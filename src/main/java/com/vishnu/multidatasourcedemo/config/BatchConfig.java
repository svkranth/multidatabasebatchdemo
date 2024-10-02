package com.vishnu.multidatasourcedemo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.DefaultJobKeyGenerator;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.DefaultExecutionContextSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.H2SequenceMaxValueIncrementer;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    @Autowired
    private @Qualifier("BatchDataSource")DataSource BatchDataSource;
    @Autowired
    private @Qualifier("BatchTransactionManager")PlatformTransactionManager BatchTransactionManager;

    @Override
    protected DataSource getDataSource() {
        return BatchDataSource;
    }

    @Override
    protected PlatformTransactionManager getTransactionManager() {
        return BatchTransactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(BatchDataSource);
    }

    @Bean
    public ConfigurableConversionService conversionService(){
        return new DefaultConversionService();
    }

    @Bean
    public ExecutionContextSerializer executionContextSerializer(){
        return new DefaultExecutionContextSerializer();
    }

    @Bean
    @Override
    public JobRepository jobRepository() {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(BatchDataSource);
        factory.setTransactionManager(BatchTransactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setValidateTransactionState(false);
        factory.setIncrementerFactory(new DataFieldMaxValueIncrementerFactory() {

            @Override
            public DataFieldMaxValueIncrementer getIncrementer(String incrementerName, String tableName) {
                // Return the appropriate incrementer for your database
                return new H2SequenceMaxValueIncrementer(BatchDataSource,tableName); // Use appropriate incrementer class
            }

            @Override
            public String[] getSupportedIncrementerTypes() {
                String[] supportedTypes = {"H2"};
                return supportedTypes;
            }

            @Override
            public boolean isSupportedIncrementerType(String databaseType) {
                return "H2".equalsIgnoreCase(databaseType);
            }
        });
        factory.setJobKeyGenerator(new DefaultJobKeyGenerator());
        factory.setJdbcOperations(jdbcTemplate());
        factory.setDatabaseType("H2");
        factory.setConversionService(conversionService());
        factory.setSerializer(executionContextSerializer());
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create JobRepository", e);
        }        
    }
    
    
}
