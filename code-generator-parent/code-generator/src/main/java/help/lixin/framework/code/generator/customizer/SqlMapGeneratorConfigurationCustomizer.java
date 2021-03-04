package help.lixin.framework.code.generator.customizer;

import help.lixin.framework.code.generator.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

public interface SqlMapGeneratorConfigurationCustomizer {
    void customize(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration, CodeGeneratorInfo model);
}
