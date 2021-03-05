package help.lixin.framework.code.generator.core;

import help.lixin.framework.code.generator.core.annotation.Property;
import help.lixin.framework.code.generator.core.annotation.Propertys;
import help.lixin.framework.code.generator.core.constant.Constants;
import help.lixin.framework.code.generator.core.customizer.JavaClientGeneratorConfigurationCustomizer;
import help.lixin.framework.code.generator.core.customizer.JavaModelGeneratorConfigurationCustomizer;
import help.lixin.framework.code.generator.core.customizer.SqlMapGeneratorConfigurationCustomizer;
import help.lixin.framework.code.generator.core.customizer.TableConfigurationCustomizer;
import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;
import help.lixin.framework.code.generator.core.model.Driver;
import help.lixin.framework.code.generator.core.model.Pom;
import help.lixin.framework.code.generator.core.model.ProjectConfig;
import help.lixin.framework.code.generator.core.util.CodeGeneratorHelp;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CodeGeneratorController {

    private final Logger logger = LoggerFactory.getLogger(CodeGeneratorController.class);

    private CodeGeneratorInfo model;
    private Configuration configuration;
    private Properties extProperties;

    public CodeGeneratorController(CodeGeneratorInfo model) {
        this.model = model;
        this.configuration = new Configuration();
        // 1. 加载扩展属性
        extProperties = buildExtProperties();
    }

    // 初始化
    public void init() {
        // 2. 加载第三方的jar
        loadThirdPartyJar();
        // 3. 加载Context
        loadContext();
        // 初始化磁盘目录
        initDir();
        logger.info("generator code before model:[{}]", model);
        logger.info("generator code before extProperties:[{}]", extProperties);
    }


    // 生成代码
    public void generator() throws InvalidConfigurationException, InterruptedException, SQLException, IOException, Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        if (!warnings.isEmpty()) {
            logger.error("call mbg exception:[{}]", warnings);
            throw new Exception(warnings.get(0));
        }
    }

    // 1. 构建扩展属性
    public Properties buildExtProperties() {
        // 应用程序所在的目录
        String appHome = System.getProperty("app.home");

        // 设置第三方jar路径
        String thirdPartyJarPath = model.getThirdPartyJarPath();
        if (null != thirdPartyJarPath) {
            String tmpPath = appHome + "/" + thirdPartyJarPath;
            logger.info("load third part jar path:[{}]", tmpPath);
            model.setThirdPartyJarPath(tmpPath);
        }

        // 加载用户自定义的变量
        Properties extProperties = new Properties();
        String extPropertiesFilePath = model.getExtPropertiesFile();
        if (null != extPropertiesFilePath) {
            Properties properties = CodeGeneratorHelp.loadExtProperties(extPropertiesFilePath);
            logger.info("load ext properties:[{}]", properties);
            if (null != properties) {
                extProperties.putAll(properties);
            }
        }

        ProjectConfig projectConfig = model.getProjectConfig();
        if (null != projectConfig) {
            extProperties.put(Constants.CONTROLLER_TARGET_PROJECT, model.getControllerProject());
            extProperties.put(Constants.CONTROLLER_TARGET_PACKAGE, projectConfig.getControllerPackage());

            extProperties.put(Constants.SERVICE_TARGET_PROJECT, model.getServiceProject());
            extProperties.put(Constants.SERVICE_TARGET_PACKAGE, projectConfig.getServicePackage());
        }

        Pom pom = model.getPom();
        if (null != pom) {
            extProperties.put(Constants.POM_TARGET_PROJECT, model.getProject());
            extProperties.put(Constants.POM_XML_TEMPLATE_PATH, pom.getPomXmlTemplateFile());
            extProperties.put(Constants.POM_GROUP_ID, pom.getGroupId());

            // 获得pom配置的artifactId
            // 当用户没有配置:artifactId时,artifactId == 项目名称
            String artifactId = pom.getArtifactId();
            if (null == artifactId) {
                // 获得项目名称
                artifactId = model.getProjectName();
            }
            extProperties.put(Constants.POM_ARTIFACTED_ID, artifactId);
            extProperties.put(Constants.POM_VERSION, pom.getVersion());
        }
        return extProperties;
    }

    // 2. 加载第三方的jar
    public void loadThirdPartyJar() {
        String thirdPartyJarPath = model.getThirdPartyJarPath();
        if (null != thirdPartyJarPath) {
            List<String> jars = CodeGeneratorHelp.loadThirdPartyJar(thirdPartyJarPath);
            jars.forEach(jar -> configuration.addClasspathEntry(jar));
        }
    }

    // 3.加载上下文信息
    public void loadContext() {
        String defaultModelType = model.getDefaultModelType();
        String id = model.getId();
        ModelType mt = defaultModelType == null ? null : ModelType
                .getModelType(defaultModelType);
        // 创建上下文
        Context context = new Context(mt);
        context.setId(id);
        context.setTargetRuntime(model.getTargetRuntime());

        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "false");
        commentGeneratorConfiguration.addProperty("suppressDate", "false");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // 4. 加载详细
        loadContextDetail(context);

        configuration.addContext(context);
    }

    // 4. 加载详细
    public void loadContextDetail(Context context) {
        // 5. 构建插件
        buildPlugin(context);
        // 6. 构建:JDBCConnection
        buildJDBCConnection(context);
        // 7. 构建JavaModelGenerator
        buildJavaModelGenerator(context);
        // 8. 构建SqlMapGenerator
        buildSqlMapGenerator(context);
        // 9. 构建JavaClientGenerator
        buildJavaClientGenerator(context);
        // 10. 构建table
        buildTables(context);
    }

    // 5. 构建plugin
    public void buildPlugin(Context context) {
        // 通过SPI加载
        ServiceLoader<Plugin> plugins = ServiceLoader.load(Plugin.class);
        plugins.forEach(plugin -> {
            String type = plugin.getClass().getName();

            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType(type);

            Propertys propertys = plugin.getClass().getAnnotation(Propertys.class);
            Property[] propertyArray = propertys.propertys();
            int len = propertyArray.length;

            for (int i = 0; i < len; i++) {
                Property property = propertyArray[i];
                String name = property.name();
                String value = parsePropertyTokens(property.value());
                pluginConfiguration.addProperty(name, value);
            }
            logger.info("add Plugin:[{}] to Context", type);
            context.addPluginConfiguration(pluginConfiguration);
        });
    }


    // 6. 构建:JDBCConnection
    public void buildJDBCConnection(Context context) {
        Driver driver = model.getDriver();
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass(driver.getDriverClass());
        jdbcConnectionConfiguration.setConnectionURL(driver.getUrl());
        jdbcConnectionConfiguration.setUserId(driver.getUsername());
        jdbcConnectionConfiguration.setPassword(driver.getPassword());
        logger.info("add jdbc connection driver:[{}],url:[{}],username:[{}],password:[{}]", driver.getDriverClass(), driver.getUrl(), driver.getUsername(), driver.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
    }

    // 7. 构建JavaModelGenerator
    public void buildJavaModelGenerator(Context context) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(model.getModelProject());
        javaModelGeneratorConfiguration.setTargetPackage(model.getProjectConfig().getModelPackage());
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");

        
        // 调用扩展的SPI
        ServiceLoader<JavaModelGeneratorConfigurationCustomizer> javaModelGeneratorConfigurationCustomizers = ServiceLoader.load(JavaModelGeneratorConfigurationCustomizer.class);
        javaModelGeneratorConfigurationCustomizers.forEach(action -> action.customize(javaModelGeneratorConfiguration, model));

        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

    }

    // 8. 构建SqlMapGenerator
    public void buildSqlMapGenerator(Context context) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(model.getMapperXmlProject());
        sqlMapGeneratorConfiguration.setTargetPackage(model.getProjectConfig().getMapperXmlPackage());

        // 调用扩展的SPI
        ServiceLoader<SqlMapGeneratorConfigurationCustomizer> sqlMapGeneratorConfigurationCustomizers = ServiceLoader.load(SqlMapGeneratorConfigurationCustomizer.class);
        sqlMapGeneratorConfigurationCustomizers.forEach(action -> action.customize(sqlMapGeneratorConfiguration, model));

        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
    }


    // 9. 构建JavaClientGenerator
    public void buildJavaClientGenerator(Context context) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetProject(model.getMapperProject());
        javaClientGeneratorConfiguration.setTargetPackage(model.getProjectConfig().getMapperPackage());


        // 调用扩展的SPI
        ServiceLoader<JavaClientGeneratorConfigurationCustomizer> javaClientGeneratorConfigurationCustomizers = ServiceLoader.load(JavaClientGeneratorConfigurationCustomizer.class);
        javaClientGeneratorConfigurationCustomizers.forEach(action -> action.customize(javaClientGeneratorConfiguration, model));

        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }

    // 10. 构建table
    public void buildTables(Context context) {
        model.getTables().forEach(table -> {
            String tableName = table.getTableName();
            String entityName = table.getEntityName();
            String mapperName = table.getMapperName();

            TableConfiguration tc = new TableConfiguration(context);
            tc.setDomainObjectName(entityName);
            tc.setTableName(tableName);
            tc.setMapperName(mapperName);
            tc.setConfiguredModelType("hierarchical");

            // 调用扩展的SPI
            ServiceLoader<TableConfigurationCustomizer> tableConfigurationCustomizers = ServiceLoader.load(TableConfigurationCustomizer.class);
            tableConfigurationCustomizers.forEach(action -> action.customize(tc, model));

            context.addTableConfiguration(tc);
        });
    }

    public void initDir() {
        // 初始化磁盘位置
        Set<String> proejctDirs = new HashSet<>();
        proejctDirs.add(model.getControllerProject());
        proejctDirs.add(model.getModelProject());
        proejctDirs.add(model.getServiceProject());
        proejctDirs.add(model.getMapperProject());
        proejctDirs.add(model.getMapperXmlProject());

        proejctDirs.forEach(path -> {
            File file = new File(path);
            file.mkdirs();
        });

    }


    String parsePropertyTokens(String s) {
        final String OPEN = "${"; //$NON-NLS-1$
        final String CLOSE = "}"; //$NON-NLS-1$
        int currentIndex = 0;

        List<String> answer = new ArrayList<>();

        int markerStartIndex = s.indexOf(OPEN);
        if (markerStartIndex < 0) {
            // no parameter markers
            answer.add(s);
            currentIndex = s.length();
        }

        while (markerStartIndex > -1) {
            if (markerStartIndex > currentIndex) {
                // add the characters before the next parameter marker
                answer.add(s.substring(currentIndex, markerStartIndex));
                currentIndex = markerStartIndex;
            }

            int markerEndIndex = s.indexOf(CLOSE, currentIndex);
            int nestedStartIndex = s.indexOf(OPEN, markerStartIndex + OPEN.length());
            while (nestedStartIndex > -1 && markerEndIndex > -1 && nestedStartIndex < markerEndIndex) {
                nestedStartIndex = s.indexOf(OPEN, nestedStartIndex + OPEN.length());
                markerEndIndex = s.indexOf(CLOSE, markerEndIndex + CLOSE.length());
            }

            if (markerEndIndex < 0) {
                // no closing delimiter, just move to the end of the string
                answer.add(s.substring(markerStartIndex));
                currentIndex = s.length();
                break;
            }

            // we have a valid property marker...
            String property = s.substring(markerStartIndex + OPEN.length(), markerEndIndex);
            String propertyValue = resolveProperty(parsePropertyTokens(property));
            if (propertyValue == null) {
                // add the property marker back into the stream
                answer.add(s.substring(markerStartIndex, markerEndIndex + 1));
            } else {
                answer.add(propertyValue);
            }

            currentIndex = markerEndIndex + CLOSE.length();
            markerStartIndex = s.indexOf(OPEN, currentIndex);
        }

        if (currentIndex < s.length()) {
            answer.add(s.substring(currentIndex));
        }
        return answer.stream().collect(Collectors.joining());
    }

    private String resolveProperty(String key) {
        String property = null;
        property = System.getProperty(key);
        if (property == null) {
            property = extProperties.getProperty(key);
        }
        return property;
    }
}
