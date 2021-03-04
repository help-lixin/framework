package help.lixin.framework.code.generator.customizer;

import help.lixin.framework.code.generator.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

public interface JavaModelGeneratorConfigurationCustomizer {
    void customize(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration, CodeGeneratorInfo model);
}
