package com.cleanup.todoc.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Task.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String type;

    private long taskId;

    public Project(String type, long taskId) {
        this.type = type;
        this.taskId = taskId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getTaskId() {
        return taskId;
    }
}