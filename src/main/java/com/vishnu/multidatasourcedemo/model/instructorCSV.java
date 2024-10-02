package com.vishnu.multidatasourcedemo.model;

import org.springframework.stereotype.Component;

@Component
public class instructorCSV {

    private int id;
    private String Channel;
    private String hobby;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getChannel() {
        return Channel;
    }
    public void setChannel(String channel) {
        Channel = channel;
    }
    public String getHobby() {
        return hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    @Override
    public String toString() {
        return "instructorCSV [id=" + id + ", Channel=" + Channel + ", hobby=" + hobby + "]";
    }

}
