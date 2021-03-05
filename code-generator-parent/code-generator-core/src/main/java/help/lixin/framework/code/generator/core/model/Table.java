package help.lixin.framework.code.generator.core.model;

import java.io.Serializable;

public class Table implements Serializable {
    private String tableName;
    private String entityName;
    private String mapperName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getMapperName() {
        if (null == mapperName && null != entityName) {
            return entityName + "Mapper";
        }
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", entityName='" + entityName + '\'' +
                ", mapperName='" + mapperName + '\'' +
                '}';
    }
}
