package help.lixin.framework.code.generator.strategy.controller;

import help.lixin.framework.code.generator.strategy.AbstractGeneratedJavaFile;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

public class GeneratedControllerJavaFile extends AbstractGeneratedJavaFile {

    private GeneratedControllerJavaFile() {
    }

    public static class Holder {
        private static final GeneratedControllerJavaFile INSTANCE = new GeneratedControllerJavaFile();
    }

    public static GeneratedControllerJavaFile getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public GeneratedJavaFile build(IntrospectedTable introspectedTable, String... args) {
        String targetProject = args[0];
        String targetPackage = args[1];
        String serviceTargetPackage = args[2];

        // help.lixin.entity.Order
        String entityFullName = getEntityFullName(introspectedTable);
        // Order(首字母大写)
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        // order(首字母小写)
        String lowEntityShortName = getEntityShortName(introspectedTable);

        // help.lixin.service.IOrderService
        String serviceInterface = getServiceFullName(serviceTargetPackage, entityName);
        // orderService
        String serviceInterfaceShortName = getServiceShortName(introspectedTable);

        // lixin.lixin.controller.OrderController
        String controllerFullName = getControllerFullName(targetPackage, entityName);
        // OrderController
        String controllerUpperName = getControllerUpperName(introspectedTable);

        // controller
        TopLevelClass controllerClass = new TopLevelClass(new FullyQualifiedJavaType(controllerFullName));
        controllerClass.setVisibility(JavaVisibility.PUBLIC);
        controllerClass.addImportedType("org.slf4j.Logger");
        controllerClass.addImportedType("org.slf4j.LoggerFactory");
        controllerClass.addImportedType("javax.annotation.Resource");

        controllerClass.addImportedType("org.springframework.web.bind.annotation.PathVariable");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.RequestBody");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.RequestHeader");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.RequestParam");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.GetMapping");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.PutMapping");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.DeleteMapping");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.PostMapping");
        controllerClass.addImportedType("org.springframework.web.bind.annotation.RestController");

        StringBuilder restBuilder = new StringBuilder();
        restBuilder.append("@RestController(")
                .append("\"")
                .append("/")
                .append(lowEntityShortName)
                .append("\"")
                .append(")");
        controllerClass.addAnnotation(restBuilder.toString());

        // private IOrderService orderService;
        Field field = new Field(serviceInterfaceShortName, new FullyQualifiedJavaType(serviceInterface));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Resource");
        controllerClass.addField(field);

        // private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
        Field logger = new Field("logger", new FullyQualifiedJavaType("org.slf4j.Logger"));
        logger.setVisibility(JavaVisibility.PRIVATE);
        StringBuilder initializationString = new StringBuilder();
        initializationString.append("LoggerFactory.getLogger(")
                .append(controllerUpperName).append(".class")
                .append(")");
        logger.setInitializationString(initializationString.toString());
        controllerClass.addField(logger);


        controllerClass.addImportedType(entityFullName);
        controllerClass.addImportedType(serviceInterface);
        importDefault(controllerClass);


        Method insert = buildInsert(entityFullName, lowEntityShortName);
        StringBuilder insertLine = new StringBuilder();
        insertLine.append("return ").append(serviceInterfaceShortName).append(SPOT);
        insertLine.append("insert").append("(");
        insertLine.append(lowEntityShortName).append(")").append(";");
        insert.addBodyLine(insertLine.toString());
        controllerClass.addMethod(insert);


        Method selectAll = buildSelectAll(entityFullName);
        StringBuilder selectAllBuilder = new StringBuilder();
        selectAllBuilder.append("return ").append(serviceInterfaceShortName).append(SPOT);
        selectAllBuilder.append("selectAll").append("()").append(";");
        selectAll.addBodyLine(selectAllBuilder.toString());
        controllerClass.addMethod(selectAll);


        Method updateByPrimaryKey = buildUpdateByPrimaryKey(entityFullName, lowEntityShortName);
        StringBuilder updateByPrimaryKeyBuilder = new StringBuilder();
        updateByPrimaryKeyBuilder.append("return ").append(serviceInterfaceShortName).append(SPOT);
        updateByPrimaryKeyBuilder.append("updateByPrimaryKey").append("(");
        updateByPrimaryKeyBuilder.append(lowEntityShortName).append(")").append(";");
        updateByPrimaryKey.addBodyLine(updateByPrimaryKeyBuilder.toString());
        controllerClass.addMethod(updateByPrimaryKey);

        IntrospectedColumn pkColumn = pkColumn(introspectedTable);
        if (null != pkColumn) {
            Method deleteByPrimaryKey = buildDeleteByPrimaryKey(pkColumn);
            StringBuilder deleteByPrimaryKeyBuilder = new StringBuilder();
            deleteByPrimaryKeyBuilder.append("return ").append(serviceInterfaceShortName).append(SPOT);
            deleteByPrimaryKeyBuilder.append("deleteByPrimaryKey").append("(");
            deleteByPrimaryKeyBuilder.append(pkColumn.getJavaProperty()).append(")").append(";");
            deleteByPrimaryKey.addBodyLine(deleteByPrimaryKeyBuilder.toString());
            controllerClass.addMethod(deleteByPrimaryKey);
        }

        JavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(controllerClass, targetProject, javaFormatter);
        return generatedJavaFile;
    }

    // *****************************************************************************
    // method 处理
    // *****************************************************************************
    // int updateByPrimaryKey(Order order);
    // @PutMapping
    // public Result<Integer> updateByPrimaryKey(@RequestBody Order order){  orderService.updateByPrimaryKey(order); };
    protected Method buildUpdateByPrimaryKey(String entityFullName, String entityShortName) {
        Method updateByPrimaryKey = new Method("updateByPrimaryKey");
        updateByPrimaryKey.addAnnotation("@PutMapping");
        updateByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter = new Parameter(new FullyQualifiedJavaType(entityFullName), entityShortName);
        parameter.addAnnotation("@RequestBody");
        updateByPrimaryKey.addParameter(parameter);
        updateByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return updateByPrimaryKey;
    }

    // int deleteByPrimaryKey(Long orderId);
    // @GetMapping("/{id}")
    // public Result<Integer> deleteByPrimaryKey(@PathVariable("id") Long id){  orderService.deleteByPrimaryKey(id); };
    protected Method buildDeleteByPrimaryKey(IntrospectedColumn pkColumn) {
        Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
        StringBuilder annotation = new StringBuilder();
        annotation.append("@GetMapping(")
                .append("\"")
                .append("/{")
                .append(pkColumn.getJavaProperty())
                .append("}").append("\"").append(")");
        deleteByPrimaryKey.addAnnotation(annotation.toString());
        deleteByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(pkColumn.getFullyQualifiedJavaType().getFullyQualifiedName()),
                pkColumn.getJavaProperty());

        StringBuilder pathVar = new StringBuilder();
        pathVar.append("@PathVariable(")
                .append("\"")
                .append(pkColumn.getJavaProperty())
                .append("\"")
                .append(")");

        parameter.addAnnotation(pathVar.toString());
        deleteByPrimaryKey.addParameter(parameter);
        deleteByPrimaryKey.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return deleteByPrimaryKey;
    }

    // List<Order> queryAll();
    // @GetMapping
    // public Result<List<Order>> queryAll(){  orderService.selectAll(); };
    protected Method buildSelectAll(String entityFullName) {
        Method selectAll = new Method("queryAll");
        selectAll.addAnnotation("@GetMapping");
        selectAll.setVisibility(JavaVisibility.PUBLIC);
        selectAll.setReturnType(new FullyQualifiedJavaType("java.util.List<" + entityFullName + ">"));
        return selectAll;
    }

    // int save(Order order);
    // @PostMapping
    // public Result<Integer> save(@RequestBody Order order){  orderService.insert(order); };
    protected Method buildInsert(String entityFullName, String entityShortName) {
        Method insert = new Method("save");
        insert.setVisibility(JavaVisibility.PUBLIC);
        insert.addAnnotation("@PostMapping");
        Parameter parameter = new Parameter(new FullyQualifiedJavaType(entityFullName), entityShortName);
        parameter.addAnnotation("@RequestBody");
        insert.addParameter(parameter);
        insert.setReturnType(FullyQualifiedJavaType.getIntInstance());
        return insert;
    }
}
