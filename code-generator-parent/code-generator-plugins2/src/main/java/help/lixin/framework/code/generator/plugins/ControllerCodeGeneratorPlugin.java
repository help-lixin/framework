package help.lixin.framework.code.generator.plugins;

import help.lixin.framework.code.generator.core.annotation.Property;
import help.lixin.framework.code.generator.core.annotation.Propertys;
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
        @Property(name = "targetProject", value = "${controller.targetProject}"),
        @Property(name = "targetServicePackage", value = "${service.targetPackage}"),
        @Property(name = "targetPackage", value = "${controller.targetPackage}")
})
public class ControllerCodeGeneratorPlugin extends PluginAdapter {

    private final Logger logger = LoggerFactory.getLogger(ControllerCodeGeneratorPlugin.class);

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
        // Service所在的package
        String serviceTargetPackage = properties.getProperty("targetServicePackage", null);
        if (null == targetProject || null == targetPackage || null == serviceTargetPackage) {
            logger.warn("ignore Plugin ControllerCodeGeneratorPlugin");
            return generatedJavaFiles;
        }
        // 生成controller文件

        // generatedJavaFiles.add(controller);
        return generatedJavaFiles;
    }
}

