package help.lixin.framework.code.generator.core;

import com.esotericsoftware.yamlbeans.YamlReader;
import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;

import java.io.FileReader;
import java.net.URL;

public class CodeGeneratorLaunch {
    public static void main(String[] args) throws Exception {
        String appHome = System.getenv().get("APP_PATH");
        System.setProperty("app.home", appHome);
        String workDir = appHome + "/" + "works";
        System.setProperty("work.dir", workDir);
        CodeGeneratorInfo model = yamlToCodeGeneratorInfo();
        if (null == model.getWorkDir() && null != workDir) {
            model.setWorkDir(workDir);
        }
        // 委派:CodeGeneratorController
        CodeGeneratorController controller = new CodeGeneratorController(model);
        controller.init();
        controller.generator();
    }

    /**
     * 转换YAML配置文件到业务模型(CodeGeneratorInfo)
     *
     * @return
     * @throws Exception
     */
    public static CodeGeneratorInfo yamlToCodeGeneratorInfo() throws Exception {
        String yamlConfig = null;
        URL resource = CodeGeneratorLaunch.class.getClassLoader().getResource("application.yml");
        if (null != resource) {
            yamlConfig = resource.getPath();
        }
        if (null == yamlConfig) {
            throw new IllegalArgumentException("rquire application.yml");
        }
        YamlReader yamlReader = new YamlReader(new FileReader(yamlConfig));
        CodeGeneratorInfo model = yamlReader.read(CodeGeneratorInfo.class);
        return model;
    }
}
