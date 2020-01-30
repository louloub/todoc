package com.cleanup.todoc.data.model;

import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String path;

    public Task(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @VisibleForTesting
    public void setId(int id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }
}