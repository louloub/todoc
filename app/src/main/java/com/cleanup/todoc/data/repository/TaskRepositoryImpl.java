package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    MutableLiveData<List<Task>> mListTaskLiveData = new MutableLiveData<>();

    @Override
    public LiveData<List<Task>> getTaskListLiveData() {
        return mListTaskLiveData;
    }

    @Override
    public void addTask(Task task) {
        List<Task> listTask = mListTaskLiveData.getValue();
        listTask.add(task);
        mListTaskLiveData.setValue(listTask);
    }

    public TaskRepositoryImpl() {
        Task task1 = new Task(0,"task numéro 1");
        Task task2 = new Task(0,"task numéro 2");
        List<Task> listTak = new ArrayList<>();
        listTak.add(task1);
        listTak.add(task2);
        mListTaskLiveData.setValue(listTak);
    }
}
