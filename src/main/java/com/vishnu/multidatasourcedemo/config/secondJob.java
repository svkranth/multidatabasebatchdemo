package com.vishnu.multidatasourcedemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.vishnu.multidatasourcedemo.model.instructorCSV;
import com.vishnu.multidatasourcedemo.writer.instructorItemWriter;

@Configuration
public class secondJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private FlatFileItemReader<instructorCSV> instructordetailitemreader;
    private instructorItemWriter instructoritemwriter;

    @Autowired
    public secondJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<instructorCSV> instructordetailitemreader, instructorItemWriter instructoritemwriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.instructordetailitemreader = instructordetailitemreader;
        this.instructoritemwriter = instructoritemwriter;
    }


    @Bean
    public Job jobTwo(){
        return new JobBuilder("second job",jobRepository)
                    .start(jobTwoStepOne())
                    .build();
    }

    @Bean
    public Step jobTwoStepOne(){
        return new StepBuilder("Second Job step 1", jobRepository)
                   .<instructorCSV,instructorCSV>chunk(3,transactionManager)
                   .reader(instructordetailitemreader)
                   .writer(instructoritemwriter)
                   .build();
    }
}
