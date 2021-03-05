package help.lixin.framework.code.generator.core.customizer;

import help.lixin.framework.code.generator.core.model.CodeGeneratorInfo;
import org.mybatis.generator.config.TableConfiguration;

public interface TableConfigurationCustomizer {
    void customize(TableConfiguration tableConfiguration, CodeGeneratorInfo model);
}
