package help.lixin.framework.liquibase.model;

import liquibase.pro.packaged.B;

import java.io.Serializable;
import java.util.Objects;

public class TableInfo implements Serializable {
    private String logicTable;
    private String dataSourceName;
    private String tableName;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String logicTable;
        private String dataSourceName;
        private String tableName;

        public Builder logicTable(String logicTable) {
            this.logicTable = logicTable;
            return this;
        }

        public Builder dataSourceName(String dataSourceName) {
            this.dataSourceName = dataSourceName;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public TableInfo build() {
            TableInfo info = new TableInfo();
            info.setLogicTable(logicTable);
            info.setTableName(tableName);
            info.setDataSourceName(dataSourceName);
            return info;
        }

    }

    public String getLogicTable() {
        return logicTable;
    }

    public void setLogicTable(String logicTable) {
        this.logicTable = logicTable;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "logicTable='" + logicTable + '\'' +
                ", dataSourceName='" + dataSourceName + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableInfo tableInfo = (TableInfo) o;
        return Objects.equals(logicTable, tableInfo.logicTable) && Objects.equals(dataSourceName, tableInfo.dataSourceName) && Objects.equals(tableName, tableInfo.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logicTable, dataSourceName, tableName);
    }
}
