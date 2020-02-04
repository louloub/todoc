package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.model.Project;

import java.util.List;

public interface ProjectRepository {

    LiveData<List<Project>> getProjectListLiveData();

}