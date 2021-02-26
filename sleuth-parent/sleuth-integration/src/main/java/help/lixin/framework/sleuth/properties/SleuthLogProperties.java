package help.lixin.framework.sleuth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.sleuth.log")
public class SleuthLogProperties {
    private boolean enabled = true;

    private int messageMaxBytes = 100;

    public int getMessageMaxBytes() {
        return messageMaxBytes;
    }

    public void setMessageMaxBytes(int messageMaxBytes) {
        this.messageMaxBytes = messageMaxBytes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
