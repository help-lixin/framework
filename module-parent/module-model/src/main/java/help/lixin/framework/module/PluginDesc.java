package help.lixin.framework.module;

import java.io.Serializable;
import java.util.Objects;

/**
 * 插件信息
 */
public class PluginDesc implements Serializable {
    // 模块名称
    private String module;
    // 版本
    private String version = "1.0.0";
    // 描述
    private String desc;
    // 是否启用
    private Boolean enabled = true;
    // 插件的入口函数类
    private String pluginClass;
    // 参数配置文件
    private String propertiesFile;

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

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }

    @Override
    public String toString() {
        return "PluginInfo{" +
                "module='" + module + '\'' +
                ", version='" + version + '\'' +
                ", desc='" + desc + '\'' +
                ", enabled=" + enabled +
                ", pluginClass='" + pluginClass + '\'' +
                ", propertiesFile='" + propertiesFile + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginDesc that = (PluginDesc) o;
        return module.equals(that.module) && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, version);
    }
}
