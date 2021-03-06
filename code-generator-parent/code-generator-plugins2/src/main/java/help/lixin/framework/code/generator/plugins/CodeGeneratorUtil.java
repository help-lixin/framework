package help.lixin.framework.code.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;

public class CodeGeneratorUtil {
    protected static final String CONTROLLER = "Controller";
    protected static final String SERVICE = "Service";
    protected static final String ISERVICE = "I";
    protected static final String IMPL = "impl";
    protected static final String CONTROLLER_PACKAGE = "controller";
    protected static final String SPOT = ".";

    // 首字母转小写
    protected static String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    // *****************************************************************************
    // 通用方法
    // *****************************************************************************

    // help.lixin.controller.OrderController
    public static String getControllerFullName(String targetPackage, String entityName) {
        return targetPackage + SPOT + entityName + CONTROLLER;
    }

    // OrderController
    public static String getControllerUpperName(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return entityName + CONTROLLER;
    }

    // orderController
    public static String getControllerShortName(IntrospectedTable introspectedTable) {
        String entityName = getEntityShortName(introspectedTable);
        return entityName + CONTROLLER;
    }

    // help.lixin.service.IOrderService
    public static String getServiceFullName(String targetPackage, String entityName) {
        // JVM编译时就已经是StringBuilder了,我就不管了
        return targetPackage + SPOT + ISERVICE + entityName + SERVICE;
    }

    // help.lixin.service.impl.OrderService
    public static String getServiceImplFullName(String targetPackage, String entityName) {
        return targetPackage + SPOT + IMPL + SPOT + entityName + SERVICE;
    }

    // orderService
    public static String getServiceShortName(IntrospectedTable introspectedTable) {
        String entityName = getEntityShortName(introspectedTable);
        return entityName + SERVICE;
    }

    // help.lixin.mapper.OrderMapper
    public static String getMapperFullName(IntrospectedTable introspectedTable) {
        String mapperPackage = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        String mapperName = introspectedTable.getTableConfiguration().getMapperName();
        String mapper = mapperPackage + SPOT + mapperName;
        return mapper;
    }

    // orderMapper
    public static String getMapperShortName(IntrospectedTable introspectedTable) {
        String mapperName = introspectedTable.getTableConfiguration().getMapperName();
        return lowerFirst(mapperName);
    }

    // help.lixin.entity.Order
    public static String getEntityFullName(IntrospectedTable introspectedTable) {
        String pkg = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return pkg + SPOT + entityName;
    }

    // order
    public static String getEntityShortName(IntrospectedTable introspectedTable) {
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        return lowerFirst(entityName);
    }
}
