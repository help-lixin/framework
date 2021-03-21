package help.lixin.framework.module.service.impl;

import help.lixin.framework.module.service.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模块工具类
 *
 * @author joe
 * @version 2018.03.28 23:13
 */
public class ModuleUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUtil.class);

    public static final String MODULE_PROPERTY_PLACEHOLDER_CONFIGURER = "modulePropertyPlaceholderConfigurer";

    /**
     * 销毁模块，不抛出异常
     *
     * @param module
     */
    static void destroyQuietly(Module module) {
        if (module != null) {
            try {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Destroy module: {}", module.getName());
                }
                module.destroy();
            } catch (Exception e) {
                LOGGER.error("Failed to destroy module " + module, e);
            }
        }
    }
}
