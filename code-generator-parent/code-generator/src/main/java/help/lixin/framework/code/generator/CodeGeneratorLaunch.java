package help.lixin.framework.code.generator;

import com.esotericsoftware.yamlbeans.YamlReader;
import help.lixin.framework.code.generator.model.CodeGeneratorInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class CodeGeneratorLaunch {
    public static void main(String[] args) throws Exception {
        CodeGeneratorInfo model = yamlToCodeGeneratorInfo();
//        String workDir = System.getProperty("work.dir");
        String workDir = "/Users/lixin/FwRepository/framework/code-generator-parent/code-generator/work";
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
        String yamlConfig = System.getProperty("config", null);
        if (null == yamlConfig) {
            yamlConfig = CodeGeneratorLaunch.class.getClassLoader().getResource("application.yml").getPath();
        }
        if (null == yamlConfig) {
            throw new IllegalArgumentException("rquire application.yml");
        }
        YamlReader yamlReader = new YamlReader(new FileReader(yamlConfig));
        CodeGeneratorInfo model = yamlReader.read(CodeGeneratorInfo.class);
        return model;
    }
}
