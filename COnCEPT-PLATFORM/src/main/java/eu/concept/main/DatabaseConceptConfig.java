package eu.concept.main;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Christos Paraskeva
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "eu.concept.repository.concept.dao",
        entityManagerFactoryRef = "conceptEntityManagerFactory",
        transactionManagerRef = "conceptTransactionManager")
public class DatabaseConceptConfig {

    public static final String PersistentUnit = "concept";

    @Bean(name = "conceptDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.conceptdb")

    public DataSource conceptDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "conceptEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean conceptEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                
                .dataSource(conceptDataSource())
                .packages("eu.concept.repository.concept.domain")
                .persistenceUnit(PersistentUnit)
                .build();
    }

    @Bean(name = "conceptTransactionManager")
    @Primary
    public PlatformTransactionManager conceptTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(conceptDataSource());
        jpaTransactionManager.setPersistenceUnitName(PersistentUnit);
        return jpaTransactionManager;
    }

    @Bean(name = "conceptJdbc")
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }

}
