package com.vishnu.multidatasourcedemo.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecuteJob implements CommandLineRunner{

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JobLauncher jobLauncher;

    private JobParameters jobParameters;

    @Override
    public void run(String... args) throws Exception {
        
        if(args.length > 0){
            String jobName = args[0];
            try{
                Job job = (Job) applicationContext.getBean(jobName);
                jobParameters = new JobParametersBuilder()
                .addLong("startTime", System.currentTimeMillis())
                .toJobParameters();
                JobExecution jobExecution=jobLauncher.run(job,jobParameters);
                System.out.println(jobExecution.getStatus());
            }catch(Exception e){
                System.out.println("Invalid job name: " + jobName);
                e.printStackTrace();
            }
        }else{
            System.out.println("No Arguments passed");
        }
        
    }

}
