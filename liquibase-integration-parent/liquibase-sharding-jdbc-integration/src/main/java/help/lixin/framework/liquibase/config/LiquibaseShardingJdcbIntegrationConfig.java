package help.lixin.framework.liquibase.config;

import help.lixin.framework.liquibase.accessor.LiquibaseResourceLoader;
import help.lixin.framework.liquibase.accessor.SpringResourceAccessor;
import help.lixin.framework.liquibase.model.DataBaseInfo;
import help.lixin.framework.liquibase.model.TableInfo;
import help.lixin.framework.liquibase.model.TableType;
import help.lixin.framework.liquibase.properties.LiquibaseIntegrationProperties;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ResourceAccessor;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

@Configuration
public class LiquibaseShardingJdcbIntegrationConfig {
    private Logger logger = LoggerFactory.getLogger(LiquibaseShardingJdcbIntegrationConfig.class);


    /**
     * 把Sharding-jdbc中的业务模型,进行解析成自己的业务模型(逻辑表名称->真实表名称[以及数据源名称])
     * 在这里仅针对:逻辑表和默认数据源进行建表,至于广播表,在实际生产环境压根是不会用的.也懒得去做.
     *
     * @param shardingDataSource
     * @return
     */
    @Bean
    @Conditional(ShardingJdbcDataSourceCondition.class)
    @ConditionalOnClass(DataSource.class)
    public DataBaseInfo dataBaseInfo(DataSource shardingDataSource) {
        if (null != shardingDataSource && shardingDataSource instanceof ShardingDataSource) {
            ShardingDataSource dataSource = (ShardingDataSource) shardingDataSource;
            // 所有的数据源集合.
            Map<String, DataSource> dataSourceMap = dataSource.getDataSourceMap();

            // 业务模型对象
            DataBaseInfo.Builder builder = DataBaseInfo.newBuilder();
            builder.dataSources(dataSourceMap);

            // 获得Sharding-jdbc配置信息.
            ShardingRuntimeContext shardingRuntimeContext = dataSource.getRuntimeContext();

            // 默认的数据源
            ShardingRuleConfiguration shardingRuleConfiguration = dataSource.getRuntimeContext().getRule().getRuleConfiguration();
            builder.defaultDataSourceName(shardingRuleConfiguration.getDefaultDataSourceName());

            // 数据源的类型(MySQL/Oracle...)
            String databaseType = shardingRuntimeContext.getDatabaseType().getName();
            builder.databaseType(databaseType);

            // 获得规则信息集
            ShardingRule shardingRule = shardingRuntimeContext.getRule();
            Collection<TableRule> tableRules = shardingRule.getTableRules();
            for (TableRule tableRule : tableRules) {
                String logicTable = tableRule.getLogicTable();
                List<DataNode> dataNodes = tableRule.getActualDataNodes();
                dataNodes.forEach(dataNode -> {
                    String tableName = dataNode.getTableName();
                    String dataSourceName = dataNode.getDataSourceName();
                    TableInfo tableInfo = TableInfo.newBuilder().logicTable(logicTable)
                            .dataSourceName(dataSourceName)
                            .tableName(tableName).build();
                    builder.addTableInfo(tableInfo);
                    if (logger.isDebugEnabled()) {
                        logger.debug("logicTable:[{}],tableNmae:[{}],dataSource:[{}]", logicTable,
                                tableName, dataSourceName);
                    }
                });
                return builder.build();
            }
        }
        return DataBaseInfo.newBuilder().build();
    }


    @Bean
    @ConditionalOnBean(DataBaseInfo.class)
    public Map<Liquibase, Contexts> liquibases(
            DataBaseInfo dataBaseInfo,
            ObjectProvider<List<LiquibaseCustomizer>> customizersList,
            ObjectProvider<Map<String, Class<? extends Database>>> databasesMap,
            LiquibaseIntegrationProperties liquibaseIntegrationProperties,
            LiquibaseResourceLoader liquibaseResourceLoader) throws Exception {
        Map<String, Class<? extends Database>> databases = databasesMap.getIfAvailable();
        List<LiquibaseCustomizer> customizers = customizersList.getIfAvailable();
        Map<Liquibase, Contexts> liquibases = new HashMap<>();

        // changeLog位置
        String changeLogFile = liquibaseIntegrationProperties.getChangeLog();
        if (null == changeLogFile) {
            logger.error("liquibase.changeLogFile properties is require");
            throw new IllegalArgumentException("liquibase.changeLogFile properties is require");
        }
        // 数据库类型
        String databaseType = dataBaseInfo.getDatabaseType();
        // 数据源集合
        Map<String, DataSource> dataSourceMap = dataBaseInfo.getDataSourceMap();
        // 上下文信息
        String context = liquibaseIntegrationProperties.getContexts();
        // 默认的数据源名称
        String defaultDataSourceName = dataBaseInfo.getDefaultDataSourceName();

        // 创建资源访问授权器
        ResourceAccessor resourceAccessor = new SpringResourceAccessor(liquibaseResourceLoader.getResourceLoader());

        // 构建默认表的信息
        if (null != defaultDataSourceName) {
            StringBuilder contextBuilder = new StringBuilder(context);
            contextBuilder.append(",").append(TableType.PhysicalTable);

            // 获得数据源
            DataSource dataSource = dataSourceMap.get(defaultDataSourceName);
            // 根据数据源创建:Database
            Database database = buildDatabase(databases, databaseType, dataSource);

            // 构建:Liquibase
            Liquibase liquibase = new Liquibase(changeLogFile, resourceAccessor, database);
            // *******************************************************************
            // 构建上下文,这一步很重要:
            // 在Sharding-jdbc中默认的数据源,对上下文很重要,当上下文为:TableType.PhysicalTable时
            // changeLog中context对应得上的才会执行.
            // *******************************************************************
            Contexts contexts = new Contexts(contextBuilder.toString());
            // 允许业务对:liquibase进行自定义(实现:LiquibaseCustomizer即可)
            if (!customizers.isEmpty()) {
                customizers.forEach(customizer -> customizer.customize(liquibase));
            }
            liquibases.put(liquibase, contexts);
        }

        // 所有的逻辑表信息
        Iterator<TableInfo> iterator = dataBaseInfo.getTableInfos().iterator();
        while (iterator.hasNext()) {
            TableInfo tableInfo = iterator.next();
            String logicTable = tableInfo.getLogicTable();
            String tableName = tableInfo.getTableName();
            String dataSourceName = tableInfo.getDataSourceName();

            // 1. 首先增加用户自定义的上下文信息
            StringBuilder contextBuilder = new StringBuilder(context);
            // *********************************************************************
            // 2. TableInfo信息存在的情况下,代表着这是一张逻辑表(虚表)
            // *********************************************************************
            contextBuilder.append(",").append(TableType.LogicalTable);

            // *********************************************************************
            // 3. 如果有配置默认数据源,代表没有分片的表都路由到这个数据源上(所以,属于物理表)
            // *********************************************************************
            if (null != defaultDataSourceName && defaultDataSourceName.equalsIgnoreCase(dataSourceName)) {
                contextBuilder.append(",").append(TableType.PhysicalTable);
            }

            // 检查规则对应的数据源是否存在
            if (!dataSourceMap.containsKey(dataSourceName)) {
                String formatLine = String.format("Rule dataSourceName:[%s],logicTable:[%s],physicsTable:[%s],Not Found DataSource", defaultDataSourceName, logicTable, tableName);
                logger.error(formatLine);
                throw new IllegalArgumentException(formatLine);
            }

            // 获得数据源
            DataSource dataSource = dataSourceMap.get(dataSourceName);
            // 根据数据源创建:Database
            Database database = buildDatabase(databases, databaseType, dataSource);

            // 构建:Liquibase
            Liquibase liquibase = new Liquibase(changeLogFile, resourceAccessor, database);
            // *********************************************************************
            // 逻辑表与物理表的关系(这个是必须要存在的,开发在chagneLog时,表名称可以变量[逻辑表名称])
            // *********************************************************************
            liquibase.setChangeLogParameter(logicTable, tableName);
            // *********************************************************************
            // 物理表与数据源关系(这个是附助数据.)
            // *********************************************************************
            liquibase.setChangeLogParameter(tableName, dataSourceName);

            // 允许业务对:liquibase进行自定义(实现:LiquibaseCustomizer即可)
            if (!customizers.isEmpty()) {
                customizers.forEach(customizer -> customizer.customize(liquibase));
            }

            if (logger.isDebugEnabled()) {
                logger.debug("build Liquibase SUCCESS.[{}]", liquibase);
            }

            // 构建上下文
            Contexts contexts = new Contexts(contextBuilder.toString());
            liquibases.put(liquibase, contexts);
        }
        return liquibases;
    }


    private Database buildDatabase(Map<String, Class<? extends Database>> databases,
                                   String databaseType,
                                   DataSource dataSource) throws Exception {
        Database database = null;
        if (!databases.containsKey(databaseType)) {
            String formatLine = String.format("databases:[%s],No Match databaseType:[%s]", databases, databaseType);
            throw new IllegalArgumentException(formatLine);
        }
        // 不能共用对象,每一次都要构建出新的来
        database = databases.get(databaseType).newInstance();
        DatabaseConnection databaseConnection = buildDatabaseConnection(dataSource);
        database.setConnection(databaseConnection);
        return database;
    }

    private DatabaseConnection buildDatabaseConnection(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        DatabaseConnection databaseConnection = new JdbcConnection(connection);
        return databaseConnection;
    }
}


final class ShardingJdbcDataSourceCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(final ConditionContext conditionContext, final AnnotatedTypeMetadata annotatedTypeMetadata) {
        boolean res = conditionContext.getBeanFactory().containsBeanDefinition("shardingDataSource");
        return res ? ConditionOutcome.match("Found Sharding-jdbc DataSource") : ConditionOutcome.noMatch("Not Found Sharding-jdbc DataSource");
    }
}