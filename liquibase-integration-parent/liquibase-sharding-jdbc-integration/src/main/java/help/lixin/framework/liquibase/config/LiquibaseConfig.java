package help.lixin.framework.liquibase.config;

import help.lixin.framework.liquibase.accessor.LiquibaseResourceLoader;
import help.lixin.framework.liquibase.lifecle.LiquibaseLifecycle;
import help.lixin.framework.liquibase.model.DatabaseType;
import help.lixin.framework.liquibase.properties.LiquibaseIntegrationProperties;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LiquibaseConfig {

    private Logger logger = LoggerFactory.getLogger(LiquibaseConfig.class);

    @Bean
    public Map<String, Class<? extends Database>> databasesMap() {
        Map<String, Class<? extends Database>> databases = new HashMap<>();
        databases.put(DatabaseType.SQLServer.name(), MSSQLDatabase.class);
        databases.put(DatabaseType.MySQL.name(), MySQLDatabase.class);
        databases.put(DatabaseType.PostgreSQL.name(), PostgresDatabase.class);
        databases.put(DatabaseType.Oracle.name(), OracleDatabase.class);
        databases.put(DatabaseType.MariaDB.name(), MariaDBDatabase.class);
        databases.put(DatabaseType.H2.name(), H2Database.class);
        return databases;
    }

    @Bean
    public LiquibaseResourceLoader liquibaseResourceLoader() {
        return new LiquibaseResourceLoader();
    }

    @Bean
    public LiquibaseLifecycle liquibaseLifecycle(ObjectProvider<Map<Liquibase, Contexts>> liquibases,
                                                 LiquibaseIntegrationProperties properties) {
        return new LiquibaseLifecycle(liquibases.getIfAvailable());
    }

    @Bean
    public LiquibaseCustomizer liquibaseCustomizerOne() {
        return new LiquibaseCustomizer() {
            @Override
            public void customize(Liquibase liquibase) {
                // todo
            }
        };
    }
}
