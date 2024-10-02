package com.vishnu.multidatasourcedemo.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.vishnu.multidatasourcedemo.model.instructorCSV;

@Configuration
public class instructorDetailItemReader {
    @Bean
    public FlatFileItemReader<instructorCSV> instructordetailitemreader(){
        return new FlatFileItemReaderBuilder<instructorCSV>()
                    .name("instructorDetailReader")
                    .resource(new FileSystemResource("C:\\Users\\Vishnu\\Desktop\\story\\instructordetails.csv"))
                    .lineMapper(new DefaultLineMapper<instructorCSV>() {
                        {
                            setLineTokenizer(new DelimitedLineTokenizer() {
                                {
                                    setNames("id","channel","hobby");
                                    //There is no need to set comma delimiter explicitly as comma is default for flat file item reader.
                                    //Incase if input file is delimited by any other character, it can be set below
                                    setDelimiter(",");
                                }
                            });
                            setFieldSetMapper(new BeanWrapperFieldSetMapper<instructorCSV>(){
                                {
                                    setTargetType(instructorCSV.class);
                                }
                            });
                        }
                    })
                    .build();
    }

}
