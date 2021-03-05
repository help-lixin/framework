package help.lixin.framework.code.generator.strategy.service;

import help.lixin.framework.code.generator.strategy.AbstractGeneratedJavaFile;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

public class GeneratedServiceInterfaceImplJavaFile extends AbstractGeneratedJavaFile {

    private GeneratedServiceInterfaceImplJavaFile() {
    }

    public static class Holder {
        static final GeneratedServiceInterfaceImplJavaFile INSTANCE = new GeneratedServiceInterfaceImplJavaFile();
    }

    public static GeneratedServiceInterfaceImplJavaFile getInstance() {
        return Holder.INSTANCE;
    }


    //    public GeneratedJavaFile build(IntrospectedTable introspectedTable, String targetProject, String targetPackage) {
    public GeneratedJavaFile build(IntrospectedTable introspectedTable, String... args) {
        String targetProject = args[0];
        String targetPackage = args[1];

        // help.lixin.entity.Order
        String entityFullName = getEntityFullName(introspectedTable);
        // Order(首字母大写)
        String entityName = introspectedTable.getTableConfiguration().getDomainObjectName();
        // order(首字母小写)
        String lowEntityShortName = getEntityShortName(introspectedTable);

        // help.lixin.service.IOrderService
        String serviceInterface = getServiceFullName(targetPackage, entityName);
        // help.lixin.service.impl.OrderService
        String serviceInterfaceImpl = getServiceImplFullName(targetPackage, entityName);

        // orderMapper
        String lowMapperName = getMapperShortName(introspectedTable);
        // lixin.lixin.mapper.OrderMapper
        String mapperFullName = getMapperFullName(introspectedTable);

        Field field = new Field(lowMapperName, new FullyQualifiedJavaType(mapperFullName));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Resource");

        TopLevelClass compilationUnit = new TopLevelClass(new FullyQualifiedJavaType(serviceInterfaceImpl));
        compilationUnit.addImportedType("org.springframework.stereotype.Service");
        compilationUnit.addAnnotation("@Service");

        compilationUnit.addField(field);
        compilationUnit.addImportedType("javax.annotation.Resource");
        compilationUnit.addImportedType(entityFullName);
        compilationUnit.addImportedType(mapperFullName);
        compilationUnit.addImportedType(serviceInterface);
        compilationUnit.addSuperInterface(new FullyQualifiedJavaType(serviceInterface));
        importDefault(compilationUnit);

        Method insert = buildInsert(entityFullName, lowEntityShortName);
        StringBuilder insertLine = new StringBuilder();
        insertLine.append("return ").append(lowMapperName).append(SPOT);
        insertLine.append("insert").append("(");
        insertLine.append(lowEntityShortName).append(")").append(";");
        insert.addBodyLine(insertLine.toString());
        compilationUnit.addMethod(insert);

        Method selectAll = buildSelectAll(entityFullName);
        StringBuilder selectAllBuilder = new StringBuilder();
        selectAllBuilder.append("return ").append(lowMapperName).append(SPOT);
        selectAllBuilder.append("selectAll").append("()").append(";");
        selectAll.addBodyLine(selectAllBuilder.toString());
        compilationUnit.addMethod(selectAll);

        Method updateByPrimaryKey = buildUpdateByPrimaryKey(entityFullName, lowEntityShortName);
        StringBuilder updateByPrimaryKeyBuilder = new StringBuilder();
        updateByPrimaryKeyBuilder.append("return ").append(lowMapperName).append(SPOT);
        updateByPrimaryKeyBuilder.append("updateByPrimaryKey").append("(");
        updateByPrimaryKeyBuilder.append(lowEntityShortName).append(")").append(";");
        updateByPrimaryKey.addBodyLine(updateByPrimaryKeyBuilder.toString());
        compilationUnit.addMethod(updateByPrimaryKey);

        IntrospectedColumn pkColumn = pkColumn(introspectedTable);
        if (null != pkColumn) {
            Method deleteByPrimaryKey = buildDeleteByPrimaryKey(pkColumn);
            StringBuilder deleteByPrimaryKeyBuilder = new StringBuilder();
            deleteByPrimaryKeyBuilder.append("return ").append(lowMapperName).append(SPOT);
            deleteByPrimaryKeyBuilder.append("deleteByPrimaryKey").append("(");
            deleteByPrimaryKeyBuilder.append(pkColumn.getJavaProperty()).append(")").append(";");
            deleteByPrimaryKey.addBodyLine(deleteByPrimaryKeyBuilder.toString());
            compilationUnit.addMethod(deleteByPrimaryKey);
        }

        JavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(compilationUnit, targetProject, javaFormatter);
        return generatedJavaFile;
    }
}
