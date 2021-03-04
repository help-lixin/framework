package help.lixin.framework.code.generator.plugins;

import help.lixin.framework.code.generator.annotation.Property;
import help.lixin.framework.code.generator.annotation.Propertys;
import help.lixin.framework.code.generator.strategy.controller.GeneratedControllerJavaFile;
import help.lixin.framework.code.generator.util.ParseContext;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.TableConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Propertys(propertys = {
        @Property(name = "targetProject", value = "${controller.targetProject}"),
        @Property(name = "targetPackage", value = "${controller.targetPackage}")
})
public class ControllerCodeGeneratorPlugin extends PluginAdapter {
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
            return generatedJavaFiles;
        }
        // 生成controller文件
        GeneratedJavaFile controller = GeneratedControllerJavaFile.getInstance().build(introspectedTable, targetProject,
                targetPackage);
        generatedJavaFiles.add(controller);
        return generatedJavaFiles;
    }
}
