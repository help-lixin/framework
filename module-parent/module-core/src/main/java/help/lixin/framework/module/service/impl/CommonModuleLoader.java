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

import com.google.common.collect.Lists;
import help.lixin.framework.module.service.ModuleConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 公共模块加载器实现
 */
public class CommonModuleLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonModuleLoader.class);

    private ClassLoader classLoader;

    private AtomicBoolean isLoadding = new AtomicBoolean(false);

    public static class Holder {
        private static final CommonModuleLoader INSTANCE = new CommonModuleLoader();
    }

    public static CommonModuleLoader getInstance() {
        return Holder.INSTANCE;
    }

    public ClassLoader getCommonClassLoader() {
        return classLoader;
    }

    public void destroy() {
        isLoadding.compareAndSet(true, false);
        SpringModule.clear(classLoader);
        classLoader = null;
    }

    public void initCommonModuleClassLoader(ModuleConfig moduleConfig) {
        // 确保CommonModule只加载一次
        if (isLoadding.compareAndSet(false, true)) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Loading module: {}", moduleConfig);
            }
            List<String> tempFileJarURLs = moduleConfig.getModuleUrlPath();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Local jars: {}", tempFileJarURLs);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Loading module  complete：{}", moduleConfig);
            }

            ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                ClassLoader classLoader = new ModuleClassLoader(moduleConfig.getModuleUrl(), currentClassLoader, getOverridePackages(moduleConfig));
                this.classLoader = classLoader;
            } finally {
                Thread.currentThread().setContextClassLoader(currentClassLoader);
            }
        }
    }

    private List<String> getOverridePackages(ModuleConfig moduleConfig) {
        List<String> list = Lists.newArrayList();
        for (String s : moduleConfig.getOverridePackages()) {
            if (!StringUtils.isBlank(s)) {
                list.add(s);
            }
        }
        return list;
    }

}
