package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.model.Task;

import java.util.List;

public interface TaskRepository {

    LiveData<List<Task>> getTaskListLiveData();

    void addTask(Task task);

}
