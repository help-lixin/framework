package help.lixin.framework.liquibase.config;

import liquibase.Liquibase;

public interface LiquibaseCustomizer {
    /**
     * 自定义:Liquibase
     *
     * @param liquibase
     */
    void customize(Liquibase liquibase);
}
