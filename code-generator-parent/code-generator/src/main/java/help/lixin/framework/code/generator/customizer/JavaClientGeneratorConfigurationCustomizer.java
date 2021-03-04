package help.lixin.framework.code.generator.customizer;

import help.lixin.framework.code.generator.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

public interface JavaClientGeneratorConfigurationCustomizer {
    void customize(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration, CodeGeneratorInfo model);
}
