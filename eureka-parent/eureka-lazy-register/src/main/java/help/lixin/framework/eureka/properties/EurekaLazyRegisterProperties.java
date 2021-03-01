package help.lixin.framework.eureka.properties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eureka.lazy")
public class EurekaLazyRegisterProperties {
	private Logger logger = LoggerFactory.getLogger(EurekaLazyRegisterProperties.class);

	// 是否启用,延迟向注册中心注册.
	// 目的:建表的过程可能比较长,可以等待所有的表创建完了之后,再向注册中心注册该服务.
	private boolean enabled = true;

	// 期待接受完哪些事件,才触发向注册中心进行注册.
	// 只有当所有的事件都触发完了之后,才会向注册中心进行注册.
	private Set<String> expectedEventTriggerRegister = new HashSet<>();

	// 间隔多久,检查EurekaAutoServiceRegistration的状态.
	// 每隔30秒,检查EurekaAutoServiceRegistrationOverride是否完成了向注册中心注册,如果未注册,打印警告日志.
	private long checkSeconds = 30;

	public long getCheckSeconds() {
		return checkSeconds;
	}

	public void setCheckSeconds(long checkSeconds) {
		if (checkSeconds < 10) {
			logger.warn("set checkSeconds FAIL, expected checkSeconds > 10");
			this.checkSeconds = 10;
			return;
		}
		this.checkSeconds = checkSeconds;
	}

	public Set<String> getExpectedEventTriggerRegister() {
		return expectedEventTriggerRegister;
	}

	public void expectedEventTriggerRegister(String expectedEventTriggerRegisterString) {
		if (null != expectedEventTriggerRegisterString) {
			String[] array = expectedEventTriggerRegisterString.split(",");
			if (null != array && array.length >= 0) {
				expectedEventTriggerRegister.addAll(Arrays.asList(array));
			}
		}
	}

	public void setExpectedEventTriggerRegister(Set<String> expectedEventTriggerRegister) {
		if (null != expectedEventTriggerRegister) {
			this.expectedEventTriggerRegister = expectedEventTriggerRegister;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
