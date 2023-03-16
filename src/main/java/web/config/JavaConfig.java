package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JavaConfig {
    private static final String BEANS_PACKAGE = "web";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "alexisakov2912";
    private static final String JDBC_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String HB_PROP_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String HB_PROP_CURRENT_SESSION_CONTEXT_CLASS = "thread";
    private static final String HB_PROP_HBM2DDL_AUTO = "none";
    private static final String HB_PROP_SHOW_SQL = "false";
    private static final String HB_PROP_FORMAT_SQL = "true";
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(BEANS_PACKAGE);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER_CLASS);
        dataSource.setUrl(MYSQL_URL);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", HB_PROP_HBM2DDL_AUTO);
        properties.setProperty("hibernate.dialect", HB_PROP_DIALECT);
        properties.setProperty("hibernate.show_sql", HB_PROP_SHOW_SQL);
        properties.setProperty("hibernate.format_sql", HB_PROP_FORMAT_SQL);
        properties.setProperty("hibernate.current_session_context_class", HB_PROP_CURRENT_SESSION_CONTEXT_CLASS);
        return properties;
    }
}