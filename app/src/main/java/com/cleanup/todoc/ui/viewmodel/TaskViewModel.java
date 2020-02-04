package com.cleanup.todoc.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRepository;
import com.cleanup.todoc.data.repository.TaskRepository;
import com.cleanup.todoc.model.TaskModelUi;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;

    private MediatorLiveData<List<TaskModelUi>> mTaskModelUiMediatorLiveData = new MediatorLiveData<>();

    public LiveData<List<TaskModelUi>> getTaskModelUiMediatorLiveData() {
        return mTaskModelUiMediatorLiveData;
    }

    void addNewTask(String message, Project project){
        // TODO
    }

    public TaskViewModel(ProjectRepository projectRepository, final TaskRepository taskRepository) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;

        mTaskModelUiMediatorLiveData.addSource(mProjectRepository.getProjectListLiveData(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(projects,taskRepository.getTaskListLiveData().getValue()));
            }
        });

        mTaskModelUiMediatorLiveData.addSource(mTaskRepository.getTaskListLiveData(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(mProjectRepository.getProjectListLiveData().getValue(),taskList));
            }
        });
    }

    List<TaskModelUi> combineProjectAndTask(List<Project> projectList, List<Task> taskList) {
        List<TaskModelUi> taskModelUi = new ArrayList<>();

        if(projectList==null || taskList==null){
            return taskModelUi;
        }

        // TODO : create GETTER for creationTimesTamp
        for (Task task : taskList) {
            for (Project project : projectList) {
                if (project.getId()==task.getTaskId()){
                    taskModelUi.add(new TaskModelUi(task.getId(),project.getId(),task.getMessage(),0));
                }
            }
        }

        return taskModelUi;
    }
}
