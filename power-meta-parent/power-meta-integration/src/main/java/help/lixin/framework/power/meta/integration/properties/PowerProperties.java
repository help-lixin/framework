package help.lixin.framework.power.meta.integration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "power")
public class PowerProperties {
	// 是否启用
	private boolean enable = true;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "PowerProperties [enable=" + enable + "]";
	}
}
