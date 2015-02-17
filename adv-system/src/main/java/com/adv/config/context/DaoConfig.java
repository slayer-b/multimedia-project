package com.adv.config.context;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.adv.core.model.Block;
import com.adv.core.model.BlockContent;
import com.adv.core.model.BlockLocation;
import com.adv.core.model.DefaultsContent;
import com.adv.core.model.ItemAdv;
import com.adv.core.model.ItemLinkStats;
import com.adv.core.model.LocationSite;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import com.adv.order.model.OrderUnits;
import com.adv.payment.model.PricesSystem;
import com.jolbox.bonecp.BoneCPDataSource;
import common.dao.GenericDAOHibernate;

@Configuration
@PropertySource({
    "classpath:/com/adv/database/jdbc.properties"
})
@ComponentScan("com.adv.dao")
@EnableTransactionManagement
public class DaoConfig {
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
    public AnnotationSessionFactoryBean sessionFactory() {
        AnnotationSessionFactoryBean factory = new AnnotationSessionFactoryBean();
        factory.setDataSource(dataSource());
        factory.setConfigLocation(new ClassPathResource("/com/adv/database/hibernate.cfg.xml"));
        factory.setAnnotatedClasses(new Class[]{
        });
        return factory;
    }

    /** Transaction manager for a single Hibernate SessionFactory (alternative to JTA) */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory().getObject());
    }

    //--------------------------- DAOs -----------------------------
    @Bean
    public GenericDAOHibernate<User, Long> userDAO() {
        return new GenericDAOHibernate<User, Long>(User.class);
    }
    
    @Bean
    public GenericDAOHibernate<Block, Long> blockDAO() {
        return new GenericDAOHibernate<Block, Long>(Block.class);
    }
    
    @Bean
    public GenericDAOHibernate<BlockLocation, Long> blockLocationDAO() {
        return new GenericDAOHibernate<BlockLocation, Long>(BlockLocation.class);
    }
    
    @Bean
    public GenericDAOHibernate<LocationSite, Long> locationSiteDAO() {
        return new GenericDAOHibernate<LocationSite, Long>(LocationSite.class);
    }
    
    @Bean
    public GenericDAOHibernate<BlockContent, Long> blockContentDAO() {
        return new GenericDAOHibernate<BlockContent, Long>(BlockContent.class);
    }
    
    @Bean
    public GenericDAOHibernate<UserDefaults, Long> userDefaultsDAO() {
        return new GenericDAOHibernate<UserDefaults, Long>(UserDefaults.class);
    }
    
    @Bean
    public GenericDAOHibernate<DefaultsContent, Long> defaultsContentDAO() {
        return new GenericDAOHibernate<DefaultsContent, Long>(DefaultsContent.class);
    }
    
    @Bean
    public GenericDAOHibernate<ItemLinkStats, Long> itemLinkStatsDAO() {
        return new GenericDAOHibernate<ItemLinkStats, Long>(ItemLinkStats.class);
    }
    
    @Bean
    public GenericDAOHibernate<PricesSystem, Long> pricesSystemDAO() {
        return new GenericDAOHibernate<PricesSystem, Long>(PricesSystem.class);
    }
    
    @Bean
    public GenericDAOHibernate<OrderUnits, Long> orderUnitsDAO() {
        return new GenericDAOHibernate<OrderUnits, Long>(OrderUnits.class);
    }
    
    @Bean
    public GenericDAOHibernate<ItemAdv, Long> itemAdvDAO() {
        return new GenericDAOHibernate<ItemAdv, Long>(ItemAdv.class);
    }
}
