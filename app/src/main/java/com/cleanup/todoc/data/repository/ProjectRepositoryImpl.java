package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Project;

import java.util.Collections;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepository {

    MutableLiveData<List<Project>> mMutableLiveData = new MutableLiveData<>();

    @Override
    public LiveData<List<Project>> getProjectListLiveData() {
        return mMutableLiveData;
    }

    public ProjectRepositoryImpl() {
        mMutableLiveData.setValue(Collections.singletonList(new Project("projet 1")));
    }
}