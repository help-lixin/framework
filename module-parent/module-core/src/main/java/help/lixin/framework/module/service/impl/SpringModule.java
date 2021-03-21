/*
 *
 *  * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package help.lixin.framework.module.service.impl;

import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ConfigurableApplicationContext;

import java.beans.Introspector;
import java.util.Date;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 集成Spring上下文的模块
 *
 * @author tengfei.fangtf
 * @version $Id: SpringModule.java, v 0.1 2017年05月30日 2:55 PM tengfei.fangtf Exp $
 */
public class SpringModule implements Module {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringModule.class);

    /**  模块的配置信息 */
    ModuleConfig moduleConfig;

    /**  模块的名称 */
    private final String name;

    /**  模块的版本 */
    private final String version;

    /**  模块启动的时间 */
    private final Date creation;

    private final ConfigurableApplicationContext applicationContext;

    public SpringModule(ModuleConfig moduleConfig, String version, String name,
                        ConfigurableApplicationContext applicationContext) {
        this.moduleConfig = moduleConfig;
        this.applicationContext = applicationContext;
        this.version = version;
        this.name = name;
        this.creation = new Date();
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Date getCreation() {
        return creation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void destroy() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Close application context: {}", applicationContext);
        }
        //close spring context
        closeQuietly(applicationContext);
        //clean classloader
        clear(applicationContext.getClassLoader());
    }

    /**
     * 清除类加载器
     *
     * @param classLoader
     */
    public static void clear(ClassLoader classLoader) {
        checkNotNull(classLoader, "classLoader is null");
        Introspector.flushCaches();
        //从已经使用给定类加载器加载的缓存中移除所有资源包
        ResourceBundle.clearCache(classLoader);
        //Clear the introspection cache for the given ClassLoader
        CachedIntrospectionResults.clearClassLoader(classLoader);
        // TODO lixin
//        LogFactory.release(classLoader);
    }

    /**
     * 关闭Spring上下文
     * @param applicationContext
     */
    private static void closeQuietly(ConfigurableApplicationContext applicationContext) {
        checkNotNull(applicationContext, "applicationContext is null");
        try {
            applicationContext.close();
        } catch (Exception e) {
            LOGGER.error("Failed to close application context", e);
        }
    }

    @Override
    public ModuleConfig getModuleConfig() {
        return moduleConfig;
    }

    @Override
    public ClassLoader getChildClassLoader() {
        return this.applicationContext.getClassLoader();
    }

}
