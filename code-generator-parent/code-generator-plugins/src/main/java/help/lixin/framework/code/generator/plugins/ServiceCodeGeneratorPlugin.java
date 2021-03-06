package help.lixin.framework.code.generator.plugins;

import help.lixin.framework.code.generator.core.annotation.Property;
import help.lixin.framework.code.generator.core.annotation.Propertys;
import help.lixin.framework.code.generator.strategy.service.GeneratedServiceInterfaceImplJavaFile;
import help.lixin.framework.code.generator.strategy.service.GeneratedServiceInterfaceJavaFile;
import help.lixin.framework.code.generator.core.util.ParseContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Propertys(propertys = {
        @Property(name = "targetProject", value = "${service.targetProject}"),
        @Property(name = "targetPackage", value = "${service.targetPackage}")
})
public class ServiceCodeGeneratorPlugin extends PluginAdapter {
    private final Logger logger = LoggerFactory.getLogger(ServiceCodeGeneratorPlugin.class);

    @Override
    public boolean validate(List<String> warnings) {
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
        if (null == targetProject || null == targetPackage) {
            logger.warn("ignore Plugin ServiceCodeGeneratorPlugin");
            return generatedJavaFiles;
        }
        // 生成Service文件
        GeneratedJavaFile serviceImpl = GeneratedServiceInterfaceJavaFile.getInstance().build(introspectedTable,
                targetProject, targetPackage);
        // 生成ServiceImpl文件
        GeneratedJavaFile service = GeneratedServiceInterfaceImplJavaFile.getInstance().build(introspectedTable,
                targetProject, targetPackage);
        generatedJavaFiles.add(service);
        generatedJavaFiles.add(serviceImpl);
        return generatedJavaFiles;
    }
}
