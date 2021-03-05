package help.lixin.framework.code.generator.strategy;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractGeneratedJavaFile {
    private final Logger logger = LoggerFactory.getLogger(AbstractGeneratedJavaFile.class);

    protected static final String CONTROLLER = "Controller";
    protected static final String SERVICE = "Service";
    protected static final String ISERVICE = "I";
    protected static final String IMPL = "impl";
    protected static final String CONTROLLER_PACKAGE = "controller";
    protected static final String SPOT = ".";

    public abstract GeneratedJavaFile build(IntrospectedTable introspectedTable, String... args);

    // 首字母转小写
    protected String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    // 获得主键
    protected IntrospectedColumn pkColumn(IntrospectedTable introspectedTable) {
        IntrospectedColumn pkColumn = null;
        List<IntrospectedColumn> pkCloumns = introspectedTable.getPrimaryKeyColumns();
        if (null != pkCloumns && !pkCloumns.isEmpty()) {
            pkColumn = pkCloumns.get(0);
        }
        return pkColumn;
    }

    // *****************************************************************************
    // method 处理
    // *****************************************************************************
    // int updateByPrimaryKey(Order record);
    protected Method buildUpdateByPrimaryKey(String entityFullName, String entityShortName) {
        Method updateByPrimaryKey = new Method("updateByPrimaryKey");
        updateByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
        updateByPrimaryKey.addParameter(new Parameter(new FullyQualifiedJavaType(entityFullName), entityShortName));
        updateByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return updateByPrimaryKey;
    }

    // int deleteByPrimaryKey(Long orderId);
    protected Method buildDeleteByPrimaryKey(IntrospectedColumn pkColumn) {
        Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
        deleteByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
        deleteByPrimaryKey.addParameter(
                new Parameter(new FullyQualifiedJavaType(pkColumn.getFullyQualifiedJavaType().getFullyQualifiedName()),
                        pkColumn.getJavaProperty()));
        deleteByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return deleteByPrimaryKey;
    }

    // List<Order> selectAll();
    protected Method buildSelectAll(String entityFullName) {
        Method selectAll = new Method("selectAll");
        selectAll.setVisibility(JavaVisibility.PUBLIC);
        selectAll.setReturnType(new FullyQualifiedJavaType("java.util.List<" + entityFullName + ">"));
        return selectAll;
    }

    // int insert(Order record);
    protected Method buildInsert(String entityFullName, String entityShortName) {
        Method insert = new Method("insert");
        insert.setVisibility(JavaVisibility.PUBLIC);
        insert.addParameter(new Parameter(new FullyQualifiedJavaType(entityFullName), entityShortName));
        insert.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return insert;
    }

    // *****************************************************************************
    // 通用方法
    // *****************************************************************************

    // help.lixin.controller.OrderController
    protected String getControllerFullName(String targetPackage, String entityName) {
        return targetPackage + SPOT + entityName + CONTROLLER;
    }

    // OrderController
    protected String getControllerUpperName(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return entityName + CONTROLLER;
    }

    // orderController
    protected String getControllerShortName(IntrospectedTable introspectedTable) {
        String entityName = getEntityShortName(introspectedTable);
        return entityName + CONTROLLER;
    }

    // help.lixin.service.IOrderService
    protected String getServiceFullName(String targetPackage, String entityName) {
        // JVM编译时就已经是StringBuilder了,我就不管了
        return targetPackage + SPOT + ISERVICE + entityName + SERVICE;
    }

    // help.lixin.service.impl.OrderService
    protected String getServiceImplFullName(String targetPackage, String entityName) {
        return targetPackage + SPOT + IMPL + SPOT + entityName + SERVICE;
    }

    // orderService
    protected String getServiceShortName(IntrospectedTable introspectedTable) {
        String entityName = getEntityShortName(introspectedTable);
        return entityName + SERVICE;
    }

    // help.lixin.mapper.OrderMapper
    protected String getMapperFullName(IntrospectedTable introspectedTable) {
        String mapperPackage = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        String mapperName = introspectedTable.getTableConfiguration().getMapperName();
        String mapper = mapperPackage + SPOT + mapperName;
        return mapper;
    }

    // orderMapper
    protected String getMapperShortName(IntrospectedTable introspectedTable) {
        String mapperName = introspectedTable.getTableConfiguration().getMapperName();
        return lowerFirst(mapperName);
    }

    // help.lixin.entity.Order
    protected String getEntityFullName(IntrospectedTable introspectedTable) {
        String pkg = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return pkg + SPOT + entityName;
    }

    // order
    protected String getEntityShortName(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return lowerFirst(entityName);
    }

    protected void importDefault(CompilationUnit compilationUnit) {
        compilationUnit.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    }
}
