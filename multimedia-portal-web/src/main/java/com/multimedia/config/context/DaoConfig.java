/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multimedia.config.context;

import com.multimedia.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("com.multimedia.dao")
public class DaoConfig {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * Transaction manager alternative to JTA
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(dataSourceConfig.entityManagerFactory().getObject());
    }

    @Bean
    public ResolutionDAO resolutionDAO() {
        return new ResolutionDAO();
    }

    @Bean
    public AdvertisementPagesDAO advertisementPagesDAO() {
        return new AdvertisementPagesDAO();
    }

    @Bean
    public AdvertisementDAO advertisementDAO() {
        return new AdvertisementDAO();
    }

    @Bean
    public AutoreplaceDAO autoreplaceDAO() {
        return new AutoreplaceDAO();
    }

    @Bean
    public AutoreplaceLDAO autoreplaceLDAO() {
        return new AutoreplaceLDAO();
    }

    @Bean
    public CounterDAO counterDAO() {
        return new CounterDAO();
    }

    @Bean
    public LocaleDAO localeDAO() {
        return new LocaleDAO();
    }

    @Bean
    public WallpaperCommentDAO wallpaperCommentDAO() {
        return new WallpaperCommentDAO();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    public SiteConfigDAO siteConfigDAO() {
        return new SiteConfigDAO();
    }

    @Bean
    public PagesPseudonymDAO pagesPseudonymDAO() {
        return new PagesPseudonymDAO();
    }

    @Bean
    public GalleryPagesDAOHibernate pagesDAO() {
        return new GalleryPagesDAOHibernate();
    }
}
