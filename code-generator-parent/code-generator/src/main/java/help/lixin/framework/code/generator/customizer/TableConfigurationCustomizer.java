package help.lixin.framework.code.generator.customizer;

import help.lixin.framework.code.generator.model.CodeGeneratorInfo;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;

public interface TableConfigurationCustomizer {
    void customize(TableConfiguration tableConfiguration, CodeGeneratorInfo model);
}
