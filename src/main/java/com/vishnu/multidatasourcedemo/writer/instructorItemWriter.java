package com.vishnu.multidatasourcedemo.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishnu.multidatasourcedemo.dao.commondao;
import com.vishnu.multidatasourcedemo.entity.InstructorDetail;
import com.vishnu.multidatasourcedemo.model.instructorCSV;

@Component
public class instructorItemWriter implements ItemWriter<instructorCSV>{

    private commondao commondao;

    @Autowired
    public instructorItemWriter(com.vishnu.multidatasourcedemo.dao.commondao commondao) {
        this.commondao = commondao;
    }

    @Override
    public void write(Chunk< ? extends instructorCSV> instructorDetails) throws Exception {
        for (instructorCSV instructor : instructorDetails) {
            System.out.println(instructor.toString());
            InstructorDetail instructorDetail = new InstructorDetail(instructor.getHobby(),instructor.getChannel());
            try {
                commondao.save(instructorDetail);
            } catch (Exception e) {
                System.out.println("Issue in saving: " + e.getMessage());
            }
        
        }
    }

}
