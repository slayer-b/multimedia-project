package com.multimedia.config.context;

import net.sf.ehcache.CacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.multimedia.cache.ehcache.EhcacheManagerFactoryBean;

@Configuration
public class CacheConfig {
    
    @Bean
    public EhcacheManagerFactoryBean cacheInit() {
        EhcacheManagerFactoryBean factory = new EhcacheManagerFactoryBean();
        factory.setShared(true);
        factory.setConfigLocation(new ClassPathResource("/gallery/cache/ehcache-single.xml"));
        return factory;
    }

    @Bean
    public CacheManager cacheManager() {
        return cacheInit().getObject();
    }

}
