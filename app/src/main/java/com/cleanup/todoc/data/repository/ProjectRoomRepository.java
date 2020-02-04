package com.cleanup.todoc.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;

import java.util.List;

public class ProjectRoomRepository implements ProjectRepository {

    private ProjectDao mProject;
    private LiveData<List<Project>> mProjectListLiveData;

    public ProjectRoomRepository() {
        AppDatabase db = AppDatabase.getInstance();
        mProject = db.projectDao();
        mProjectListLiveData = mProject.getListProjectLiveData();
    }

    @Override
    public LiveData<List<Project>> getProjectListLiveData() {
        return mProjectListLiveData;
    }
}
