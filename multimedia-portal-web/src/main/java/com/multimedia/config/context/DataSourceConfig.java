package com.multimedia.config.context;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public interface DataSourceConfig {
    DataSource dataSource();

    LocalContainerEntityManagerFactoryBean entityManagerFactory();
}
