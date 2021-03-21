package help.lixin.framework.module.service.impl;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleConfig;
import help.lixin.framework.module.service.ModuleLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Iterables.toArray;

/**
 * 模块加载器的实现
 */
public class ModuleLoaderImpl implements ModuleLoader, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleLoaderImpl.class);

    /**
     * 模块版本属性
     */
    private static final String MODULE_PROPERTY_VERSION = "module_version";

    /**
     * 模块名属性
     */
    private static final String MODULE_PROPERTY_NAME = "module_name";

    /**
     * 不加载的Spring配置文件
     */
    private static final String MODULE_EXCLUSION_CONFIGE_NAME = "exclusion_confige_name";

    /**
     * 注入父applicationContext
     */
    private ApplicationContext applicationContext;

    @Override
    public Module load(ModuleConfig moduleConfig) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Loading module: {}", moduleConfig);
        }
        List<String> tempFileJarURLs = moduleConfig.getModuleUrlPath();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Local jars: {}", tempFileJarURLs);
        }

        ConfigurableApplicationContext moduleApplicationContext = loadModuleApplication(moduleConfig, tempFileJarURLs);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Loading module  complete：{}", moduleConfig);
        }
        return new SpringModule(moduleConfig, moduleConfig.getVersion(), moduleConfig.getName(),
                moduleApplicationContext);
    }

    @Override
    public void unload(Module module) {
        if (module != null) {
            module.destroy();
        }
    }

    /**
     * 根据本地临时文件Jar，初始化模块自己的ClassLoader，初始化Spring Application Context，同时要设置当前线程上下文的ClassLoader问模块的ClassLoader
     *
     * @param moduleConfig
     * @param tempFileJarURLs
     * @return
     */
    private ConfigurableApplicationContext loadModuleApplication(ModuleConfig moduleConfig, List<String>
            tempFileJarURLs) {

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader parentClassLoader = currentClassLoader;
        // 1.获取公共的ClassLoader
        ClassLoader commonClassLoader = CommonModuleLoader.getInstance().getCommonClassLoader();
        if (null != commonClassLoader) {
            parentClassLoader = commonClassLoader;
        }

        // 2. 业务模块的ClassLoader
        ClassLoader moduleClassLoader = new ModuleClassLoader(moduleConfig.getModuleUrl(), parentClassLoader, getOverridePackages(moduleConfig));

        try {
            // 3. 把当前线程的ClassLoader切换成模块的
            Thread.currentThread().setContextClassLoader(moduleClassLoader);

            // 参数列表
            List<String> argsList = new ArrayList<>(0);
            List<Object> contextsList = new ArrayList<>(0);
            contextsList.add(applicationContext);

            // 4. 构建Properties
            String propertiesFileString = moduleConfig.getPropertiesFile();
            File propertiesFile = new File(propertiesFileString);
            if (propertiesFile.exists()) {
                argsList.add("--spring.config.location=" + propertiesFileString);
            }

            // 参数
            String[] args = argsList.toArray(new String[argsList.size()]);
            // 上下文
            Object[] contexts = contextsList.toArray();
            // 5. 反射并传递参数
            ConfigurableApplicationContext context = loadApplicationContext(moduleClassLoader, moduleConfig.getMainClass(), args, contexts);
            return context;
        } catch (Throwable e) {
            CachedIntrospectionResults.clearClassLoader(moduleClassLoader);
            throw Throwables.propagate(e);
        } finally {
            //6. 还原当前线程的ClassLoader
            Thread.currentThread().setContextClassLoader(currentClassLoader);
        }
    }

    private ConfigurableApplicationContext loadApplicationContext(ClassLoader classLoader, String clazz, String[] args, Object[] contexts) throws Exception {
        Class<?> mainClass = classLoader.loadClass(clazz);
        Object application = mainClass.newInstance();
        Method start = mainClass.getDeclaredMethod("start", new Class<?>[]{String[].class, Object[].class});
        Object result = start.invoke(application, (Object) args, contexts);
        if (result instanceof ConfigurableApplicationContext) {
            return (ConfigurableApplicationContext) result;
        } else {
            throw new Exception("transform " + result + " to ConfigurableApplicationContext fail");
        }
    }

    /**
     * 获取不加载的spring配置文件名称
     *
     * @param properties
     * @return
     */
    private List<String> getExclusionConfigeNameList(Properties properties) {
        String property = properties.getProperty(MODULE_EXCLUSION_CONFIGE_NAME);
        if (property != null) {
            return Lists.newArrayList(property.split(","));
        }
        return Lists.newArrayList();

    }

    /**
     * 过滤查找到的spring配置文件资源，只查找tempFileJarURLs中的spring配置文件
     *
     * @param tempFileJarURLs
     * @param resources
     * @param exclusionConfigeNameList
     * @return
     * @throws IOException
     */
    private String[] filterURLsIncludedResources(List<String> tempFileJarURLs, Resource[] resources, List<String>
            exclusionConfigeNameList) throws IOException {
        List<String> configLocations = Lists.newArrayList();
        for (Resource resource : resources) {
            String configLocation = resource.getURL().toString();
            for (String url : tempFileJarURLs) {
                if (isExclusionConfig(configLocation, exclusionConfigeNameList)) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("exclusion url: {}", configLocation);
                    }
                    continue;
                }
                if (configLocation.contains(url)) {
                    configLocations.add(configLocation);
                }
            }
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Config locations: {}", configLocations);
        }
        return toArray(configLocations, String.class);
    }

    /**
     * 是否是需要不载入的spring配置
     *
     * @param url
     * @param exclusionConfigeNameList
     * @return
     */
    private boolean isExclusionConfig(String url, List<String> exclusionConfigeNameList) {
        for (String tmp : exclusionConfigeNameList) {
            if (url.contains(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去除list中的空白元素，string.startWith("")==true
     *
     * @param moduleConfig
     * @return
     */
    private List<String> getOverridePackages(ModuleConfig moduleConfig) {
        List<String> list = Lists.newArrayList();
        for (String s : moduleConfig.getOverridePackages()) {
            if (!StringUtils.isBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
