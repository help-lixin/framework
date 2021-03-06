package help.lixin.framework.code.generator.plugins;


import help.lixin.framework.code.generator.core.annotation.Property;
import help.lixin.framework.code.generator.core.annotation.Propertys;
import help.lixin.framework.code.generator.core.formatter.FreemarkerCompilationUnit;
import help.lixin.framework.code.generator.core.formatter.FreemarkerFormatter;
import help.lixin.framework.code.generator.core.util.ParseContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Propertys(propertys = {
        @Property(name = "targetProject", value = "${service.targetProject}"),
        @Property(name = "targetPackage", value = "${service.targetPackage}"),
        @Property(name = "serviceTemplateFile", value = "${service.templateFile}", desc = "生成service的模板文件(比如:template/service.ftl)"),
        @Property(name = "serviceImplTemplateFile", value = "${service.impl.templateFile}", desc = "生成service impl的模板文件(比如:template/serviceImpl.ftl)")
})
public class ServiceCodeGeneratorPlugin extends PluginAdapter {

    private final Logger logger = LoggerFactory.getLogger(ServiceCodeGeneratorPlugin.class);

    @Override
    public boolean validate(List<String> warnings) {
        Properties properties = ParseContext.parse(context, this.getClass());
        String serviceTemplateFile = properties.getProperty("serviceTemplateFile", null);
        String serviceImplTemplateFile = properties.getProperty("serviceImplTemplateFile", null);
        URL serviceTemplateFileResource = ServiceCodeGeneratorPlugin.class.getClassLoader().getResource(serviceTemplateFile);
        URL serviceImplTemplateFileResource = ServiceCodeGeneratorPlugin.class.getClassLoader().getResource(serviceImplTemplateFile);
        if (null == serviceTemplateFileResource) {
            warnings.add("Not Found define[serviceTemplateFile] template:" + serviceTemplateFile);
            logger.warn("ignore Plugin ServiceCodeGeneratorPlugin,Not Found pom template:[{}]", serviceTemplateFileResource);
            return false;
        }
        if (null == serviceImplTemplateFileResource) {
            warnings.add("Not Found define[serviceImplTemplateFile] template:" + serviceImplTemplateFileResource);
            logger.warn("ignore Plugin ServiceCodeGeneratorPlugin,Not Found pom template:[{}]", serviceTemplateFileResource);
            return false;
        }
        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<GeneratedJavaFile>(2);
        Context context = introspectedTable.getContext();
        Properties properties = ParseContext.parse(context, this.getClass());
        // 生成的项目路径
        String targetProject = properties.getProperty("targetProject", null);
        // package
        String targetPackage = properties.getProperty("targetPackage", null);
        // service文件路径
        String serviceTemplateFile = properties.getProperty("serviceTemplateFile", null);
        // serviceImpl文件
        String serviceImplTemplateFile = properties.getProperty("serviceImplTemplateFile", null);
        if (null == targetProject || null == targetPackage) {
            logger.warn("ignore Plugin ServiceCodeGeneratorPlugin");
            return generatedJavaFiles;
        }

        String entityShortName = introspectedTable.getTableConfiguration().getDomainObjectName();

        FreemarkerFormatter formatter = new FreemarkerFormatter();
        String serviceInterface = CodeGeneratorUtil.getServiceFullName(targetPackage, entityShortName);
        FreemarkerCompilationUnit serviceTemplateFileCompilationUnit = new FreemarkerCompilationUnit(new FullyQualifiedJavaType(serviceInterface));
        serviceTemplateFileCompilationUnit.setTemplateFile(serviceTemplateFile);
        GeneratedJavaFile generatedServiceTemplateFile = new GeneratedJavaFile(serviceTemplateFileCompilationUnit, targetProject, formatter);


        String serviceImplInterface = CodeGeneratorUtil.getServiceImplFullName(targetPackage, entityShortName);
//        FreemarkerCompilationUnit serviceImplTemplateFileCompilationUnit = new FreemarkerCompilationUnit();
//        serviceTemplateFileCompilationUnit.setTemplateFile(serviceImplTemplateFile);
//        GeneratedJavaFile generatedServiceImplTemplateFile = new GeneratedJavaFile(serviceImplTemplateFileCompilationUnit, targetProject, formatter);


        generatedJavaFiles.add(generatedServiceTemplateFile);
//        generatedJavaFiles.add(generatedServiceImplTemplateFile);
        return generatedJavaFiles;
    }
}