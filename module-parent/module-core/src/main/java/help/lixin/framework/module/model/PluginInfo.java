package help.lixin.framework.module.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PluginInfo implements Serializable {
    // 插件所成的路径
    private String path;
    // 模块名称
    private String module;
    // 版本
    private String version = "1.0.0";

    // 插件类型
    private PluginType pluginType = PluginType.COMMON;

    // 描述
    private String desc;
    // 是否启用
    private Boolean enabled = true;
    // 插件的入口函数类
    private String pluginClass;
    // 参数配置文件
    private String propertiesFile;
    // 所有的组件
    private Set<String> bundles = new HashSet<>();

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public Set<String> getBundles() {
        return bundles;
    }

    public void setBundles(Set<String> bundles) {
        this.bundles = bundles;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PluginType getPluginType() {
        return pluginType;
    }

    public void setPluginType(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginInfo that = (PluginInfo) o;
        return path.equals(that.path) && module.equals(that.module) && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, module, version);
    }
}

