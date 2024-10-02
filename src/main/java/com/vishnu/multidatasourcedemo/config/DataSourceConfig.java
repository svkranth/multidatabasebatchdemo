package com.vishnu.multidatasourcedemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource AppDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("AppDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.vishnu.multidatasourcedemo.entity") // Replace with your entity package
                .persistenceUnit("appDb")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager AppTransactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.batch.datasource")
    public DataSource BatchDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "batchEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("BatchDataSource") DataSource batchDataSource) {
        return builder
                .dataSource(batchDataSource)
                .packages("org.springframework.batch.core") // Spring Batch core entities
                .persistenceUnit("batchDb")
                .build();
    }

    @Bean
    public PlatformTransactionManager BatchTransactionManager(@Qualifier("batchEntityManagerFactory") LocalContainerEntityManagerFactoryBean batchEntityManagerFactory) {
        return new DataSourceTransactionManager(batchEntityManagerFactory.getDataSource());  
    }

}
