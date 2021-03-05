package help.lixin.framework.code.generator.core.customizer;

import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

public interface SqlMapGeneratorConfigurationCustomizer {
    void customize(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration, CodeGeneratorInfo model);
}
