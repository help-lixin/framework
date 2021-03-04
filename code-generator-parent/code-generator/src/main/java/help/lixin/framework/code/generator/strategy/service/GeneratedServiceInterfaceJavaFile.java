package help.lixin.framework.code.generator.strategy.service;

import help.lixin.framework.code.generator.strategy.AbstractGeneratedJavaFile;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;

public class GeneratedServiceInterfaceJavaFile extends AbstractGeneratedJavaFile {

    private GeneratedServiceInterfaceJavaFile() {
    }

    public static class Holder {
        static final GeneratedServiceInterfaceJavaFile INSTANCE = new GeneratedServiceInterfaceJavaFile();
    }

    public static GeneratedServiceInterfaceJavaFile getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 构建接口
     *
     * @param introspectedTable
     * @param targetProject
     * @param targetPackage
     * @return
     */
    public GeneratedJavaFile build(IntrospectedTable introspectedTable, String targetProject,
                                   String targetPackage) {
        // help.lixin.entity.Order
        String entityFullName = getEntityFullName(introspectedTable);
        // Order
        String entityShortName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String lowEntityShortName = lowerFirst(entityShortName);

        // 把Order转换成:help.lixin.service.IOrderService
        String serviceInterface = getServiceFullName(targetPackage, entityShortName);
        Interface compilationUnit = new Interface(new FullyQualifiedJavaType(serviceInterface));
        compilationUnit.setVisibility(JavaVisibility.PUBLIC);
        importDefault(compilationUnit);
        compilationUnit.addImportedType(new FullyQualifiedJavaType(entityFullName));

        Method insert = buildInsert(entityFullName, lowEntityShortName);
        insert.setAbstract(true);
        compilationUnit.addMethod(insert);

        Method selectAll = buildSelectAll(entityFullName);
        selectAll.setAbstract(true);
        compilationUnit.addMethod(selectAll);

        Method updateByPrimaryKey = buildUpdateByPrimaryKey(entityFullName, lowEntityShortName);
        updateByPrimaryKey.setAbstract(true);
        compilationUnit.addMethod(updateByPrimaryKey);

        IntrospectedColumn pkColumn = pkColumn(introspectedTable);
        if (null != pkColumn) {
            Method deleteByPrimaryKey = buildDeleteByPrimaryKey(pkColumn);
            deleteByPrimaryKey.setAbstract(true);
            compilationUnit.addMethod(deleteByPrimaryKey);
        }

        JavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(compilationUnit, targetProject, javaFormatter);
        return generatedJavaFile;
    }
}
