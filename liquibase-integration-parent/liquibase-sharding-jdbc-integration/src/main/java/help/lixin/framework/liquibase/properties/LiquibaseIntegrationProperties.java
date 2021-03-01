package help.lixin.framework.liquibase.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "liquibase")
public class LiquibaseIntegrationProperties {
    private Logger logger = LoggerFactory.getLogger(LiquibaseIntegrationProperties.class);

    // 是否禁用Spring自带的:Liquibase(默认是禁用,要是不禁用,启动会报错)
    private boolean disabledSpringLiquibase = true;

    // changLog文件的路径(classpath:/db/changelog/db.changelog.xml)
    private String changeLog;

    // 可配置的上下文
    private String contexts = "";

    public boolean isDisabledSpringLiquibase() {
        return disabledSpringLiquibase;
    }

    public void setDisabledSpringLiquibase(boolean disabledSpringLiquibase) {
        this.disabledSpringLiquibase = disabledSpringLiquibase;
    }

    public String getContexts() {
        return contexts;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    @Override
    public String toString() {
        return "LiquibaseIntegrationProperties{" +
                "disabledSpringLiquibase=" + disabledSpringLiquibase +
                ", changeLog='" + changeLog + '\'' +
                ", contexts='" + contexts + '\'' +
                '}';
    }
}
