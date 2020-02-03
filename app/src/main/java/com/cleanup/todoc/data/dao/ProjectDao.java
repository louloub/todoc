package com.cleanup.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.data.model.Project;

import java.util.List;

@Dao
public
interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getListProjectLiveData();

    @Insert
    void insertProject(Project project);

    @Update
    void updateProject(Project project);
}