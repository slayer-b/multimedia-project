package com.multimedia.config.context;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("mysql")
//-Dspring.profiles.active=mysql
public class DataSourceMySQL implements DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource ds = new BoneCPDataSource();
        ds.setDriverClass(env.getProperty("jdbc.connection.driver_class"));
        ds.setJdbcUrl(env.getProperty("jdbc.connection.url"));
        ds.setUsername(env.getProperty("jdbc.connection.username"));
        ds.setPassword(env.getProperty("jdbc.connection.password"));

        ds.setIdleConnectionTestPeriod(60, TimeUnit.MINUTES);
        ds.setIdleMaxAge(240, TimeUnit.MINUTES);
        ds.setMaxConnectionsPerPartition(16);
        ds.setMinConnectionsPerPartition(8);
        ds.setPartitionCount(3);
        ds.setAcquireIncrement(5);
        ds.setStatementsCacheSize(50);
        ds.setReleaseHelperThreads(3);
        return ds;
    }

    @Bean
    //TODO: mb find an other workaround for not creating default cache by hibernate.
    //TODO: Problem is that it bootstraps default ehcache config, instead of configured in spring context.
    @DependsOn("cacheManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setPersistenceUnitName("multimedia-portal-mysql");
        entityManagerFactory.setPersistenceXmlLocation("classpath:META-INF/persistance-mysql.xml");
        return entityManagerFactory;
    }

    @Bean
    public HibernateExceptionTranslator exceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
