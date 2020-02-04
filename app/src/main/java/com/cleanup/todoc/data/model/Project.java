package com.cleanup.todoc.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String type;

    public Project(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}