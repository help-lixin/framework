package help.lixin.framework.code.generator.core.customizer;

import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

public interface JavaModelGeneratorConfigurationCustomizer {
    void customize(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration, CodeGeneratorInfo model);
}
