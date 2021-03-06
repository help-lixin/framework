package help.lixin.framework.code.generator;

import help.lixin.framework.code.generator.core.CodeGeneratorLaunch;

public class CodeGeneratorTest {
    public static void main(String[] args) throws Exception {
        String appPath = "/Users/lixin/FwRepository/framework/code-generator-parent/code-generator-plugins/src/test";
//        System.getenv().put("APP_PATH",appPath);
        CodeGeneratorLaunch.main(args);
        System.out.println();
    }
}
