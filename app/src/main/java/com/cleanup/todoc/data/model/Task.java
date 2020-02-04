package com.cleanup.todoc.data.model;

import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE
        )
)

public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private int taskId;

    private String message;

    public Task(int taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @VisibleForTesting
    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}