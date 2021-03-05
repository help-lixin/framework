package help.lixin.framework.code.generator.core.customizer;

import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;

public interface JavaClientGeneratorConfigurationCustomizer {
    void customize(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration, CodeGeneratorInfo model);
}
