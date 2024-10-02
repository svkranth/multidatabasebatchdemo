package com.vishnu.multidatasourcedemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class firstJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job JobOne(){
        return new JobBuilder("First Job",jobRepository)
                .start(jobOneStepOne())
                .build();
    }

    @Bean
    public Step jobOneStepOne(){
        return new StepBuilder("Jobonestep1",jobRepository)
                .tasklet(firstTasklet(),transactionManager)
                .build();
    }

    @Bean
    public Tasklet firstTasklet(){
        return (contribution, chunkContext) -> {
            System.out.println("First tasklet executed");
            return RepeatStatus.FINISHED;
        };

    }
}
