package com.cleanup.todoc.ui.viewmodel;

import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRoomRepository;
import com.cleanup.todoc.data.repository.TaskRepository;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.model.TaskModelUi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private ProjectRoomRepository mProjectRoomRepository;
    private TaskRepository mTaskRepository;

    private MediatorLiveData<List<TaskModelUi>> mTaskModelUiMediatorLiveData = new MediatorLiveData<>();

    public LiveData<List<TaskModelUi>> getTaskModelUiMediatorLiveData() {
        return mTaskModelUiMediatorLiveData;
    }

    /**
     * List of all current taskModelUiList of the application
     */
    @NonNull
    private final ArrayList<TaskModelUi> taskModelUiList = new ArrayList<>();

    /*public void addNewTask(String message, Project project){
        // TODO
    }*/

    public void addNewTask(EditText dialogEditText, Spinner dialogSpinner, DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            ProjectModelUi taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof ProjectModelUi) {
                taskProject = (ProjectModelUi) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                int charSequence = R.string.empty_task_name;
                dialogEditText.setError(String.valueOf(charSequence));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                // TODO: Replace this by id of persisted task
                long id = (long) (Math.random() * 50000);


                TaskModelUi task = new TaskModelUi(
                        id,
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

                taskModelUiList.add(task);

                mTaskModelUiMediatorLiveData.setValue(taskModelUiList);

                // addTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    public TaskViewModel(ProjectRoomRepository projectRoomRepository, final TaskRepository taskRepository) {
        this.mProjectRoomRepository = projectRoomRepository;
        this.mTaskRepository = taskRepository;

        mTaskModelUiMediatorLiveData.addSource(mProjectRoomRepository.getProjectListLiveData(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(projects,taskRepository.getTaskListLiveData().getValue()));
            }
        });

        mTaskModelUiMediatorLiveData.addSource(mTaskRepository.getTaskListLiveData(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(mProjectRoomRepository.getProjectListLiveData().getValue(),taskList));
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
