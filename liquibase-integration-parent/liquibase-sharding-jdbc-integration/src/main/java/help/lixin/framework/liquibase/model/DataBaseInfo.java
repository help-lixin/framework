package help.lixin.framework.liquibase.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataBaseInfo {
    private static Logger logger = LoggerFactory.getLogger(DataBaseInfo.class);

    private Map<String, DataSource> dataSourceMap = new HashMap<>();
    private Collection<TableInfo> tableInfos = new ArrayList<>();
    // 默认的数据源
    private String defaultDataSourceName;
    // 数据源类型
    private String databaseType;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, DataSource> dataSourceMap = new HashMap<>();
        private Collection<TableInfo> tableInfos = new ArrayList<>();
        private String defaultDataSourceName = "";
        private String databaseType;

        public Builder databaseType(String databaseType) {
            this.databaseType = databaseType;
            return this;
        }

        public Builder defaultDataSourceName(String defaultDataSourceName) {
            this.defaultDataSourceName = defaultDataSourceName;
            return this;
        }

        public Builder dataSource(String dsName, DataSource ds) {
            this.dataSourceMap.putIfAbsent(dsName, ds);
            return this;
        }

        public Builder dataSources(Map<String, DataSource> dataSourceMap) {
            this.dataSourceMap.putAll(dataSourceMap);
            return this;
        }

        public Builder addTableInfo(TableInfo tableInfo) {
            if (!tableInfos.contains(tableInfo)) {
                tableInfos.add(tableInfo);
            } else {
                logger.warn("TableInfo:[{}] exists", tableInfo);
            }
            return this;
        }

        public DataBaseInfo build() {
            DataBaseInfo info = new DataBaseInfo();
            info.setDataSourceMap(dataSourceMap);
            info.setDatabaseType(databaseType);
            info.setTableInfos(tableInfos);
            info.setDefaultDataSourceName(defaultDataSourceName);
            return info;
        }
    }

    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    public Collection<TableInfo> getTableInfos() {
        return tableInfos;
    }

    public void setTableInfos(Collection<TableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }

    public String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public String toString() {
        return "DataBaseInfo{" +
                "dataSourceMap=" + dataSourceMap +
                ", tableInfos=" + tableInfos +
                ", defaultDataSourceName='" + defaultDataSourceName + '\'' +
                ", databaseType='" + databaseType + '\'' +
                '}';
    }
}
