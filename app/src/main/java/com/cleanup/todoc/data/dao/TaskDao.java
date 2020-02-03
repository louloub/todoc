package com.cleanup.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.data.model.Task;

import java.util.List;

@Dao
public
interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getListTaskLiveData();

    @Insert
    long insertTask(Task task);
}