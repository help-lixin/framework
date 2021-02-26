package help.lixin.framework.sleuth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.sleuth.sampler")
public class CustomSamplerProperties {

    private float rate = 0.05f;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
