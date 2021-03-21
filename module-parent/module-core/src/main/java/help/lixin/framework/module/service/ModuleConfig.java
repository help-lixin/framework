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
package help.lixin.framework.module.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class ModuleConfig {
    // 模块所在的路径
    private String path;

    /**
     * 模块名,建议用英文命名,忽略大小写
     */
    private String name;

    /**
     * 模块描述
     */
    private String desc;

    /**
     * 是否启用模块,默认启用
     */
    private Boolean enabled = true;

    /**
     * 模块的版本，如1.0.0.20120609 版本变化会触发模块重新部署
     */
    private String version;

    /**
     * 模块指定需要覆盖的Class的包名,不遵循双亲委派, 模块的类加载器加载这些包
     * <p>
     * 如果子模块中加载不到那么仍然会到父容器中加载
     */
    private List<String> overridePackages = Lists.newArrayList();

    // 参数配置文件
    private String propertiesFile;

    /**
     * JAR 包资源地址,模块存放的地方
     */
    private List<URL> moduleUrl = Lists.newArrayList();

    private boolean isNeedUnloadOldVersion = true;

    private String mainClass;

    public boolean isNeedUnloadOldVersion() {
        return isNeedUnloadOldVersion;
    }

    public void setNeedUnloadOldVersion(boolean needUnloadOldVersion) {
        isNeedUnloadOldVersion = needUnloadOldVersion;
    }

    public List<URL> getModuleUrl() {
        return moduleUrl;
    }

    public List<String> getModuleUrlPath() {
        List<String> moduleUrls = Lists.newArrayList();
        moduleUrls.addAll(moduleUrl.stream().map(URL::toString).collect(Collectors.toList()));
        return moduleUrls;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setModuleUrl(List<URL> moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModuleConfig withEnabled(Boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    public ModuleConfig withVersion(String version) {
        setVersion(version);
        return this;
    }

    public ModuleConfig withOverridePackages(List<String> overridePackages) {
        setOverridePackages(overridePackages);
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getOverridePackages() {
        return overridePackages;
    }

    public void setOverridePackages(List<String> overridePackages) {
        this.overridePackages = overridePackages;
    }

    public ModuleConfig withName(String name) {
        setName(name);
        return this;
    }

    public ModuleConfig addModuleUrl(URL url) {
        moduleUrl.add(url);
        return this;
    }

    public ModuleConfig withNeedUnloadOldVersion(boolean needUnloadOldVersion) {
        setNeedUnloadOldVersion(needUnloadOldVersion);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ModuleConfig{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", enabled=" + enabled +
                ", version='" + version + '\'' +
                ", overridePackages=" + overridePackages +
                ", propertiesFile='" + propertiesFile + '\'' +
                ", moduleUrl=" + moduleUrl +
                ", isNeedUnloadOldVersion=" + isNeedUnloadOldVersion +
                ", mainClass='" + mainClass + '\'' +
                '}';
    }
}