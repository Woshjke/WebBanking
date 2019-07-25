package bank.configuration;

import bank.ApplicationProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * Hibernate configuration
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "bank.model.dao")
public class HibernateConfig {

    private ApplicationProperties applicationProperties = new ApplicationProperties();

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("bank.model.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        sessionFactory.setJpaVendorAdapter(vendorAdapter);
        sessionFactory.setJpaProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        return Flyway.configure().dataSource(dataSource()).baselineOnMigrate(true).load();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(applicationProperties.getProperty("database.driver"));
        dataSource.setUrl(applicationProperties.getProperty("database.url"));
        dataSource.setUsername(applicationProperties.getProperty("database.username"));
        dataSource.setPassword(applicationProperties.getProperty("database.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect",
                applicationProperties.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql",
                applicationProperties.getProperty("hibernate.show_sql"));
//        hibernateProperties.setProperty("hibernate.default_schema",
//                applicationProperties.getProperty("hibernate.default_schema"));
        return hibernateProperties;
    }
}
