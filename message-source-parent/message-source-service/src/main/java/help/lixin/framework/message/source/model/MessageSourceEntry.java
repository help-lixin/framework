package help.lixin.framework.message.source.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

public class MessageSourceEntry {
    private String name;
    private Properties properties;
    private long lastUpdateTime = System.currentTimeMillis();

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private String name;
        private Properties properties;
        private long lastUpdateTime = System.currentTimeMillis();

        public Builder name(){
            this.name = name;
            return  this;
        }

        public Builder lastUpdateTime(){
            this.name = name;
            return  this;
        }

        public MessageSourceEntry build(){
            Assert.notNull(name,"Message Source name  require");

            // 加载文件
            File file = new File(name);


            MessageSourceEntry entry = null;
            return entry;
        }
    }


    public MessageSourceEntry(String name, Properties properties, long lastUpdateTime) {
        this.name = name;
        this.properties = properties;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageSourceEntry that = (MessageSourceEntry) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
