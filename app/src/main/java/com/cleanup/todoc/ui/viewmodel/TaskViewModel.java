package com.cleanup.todoc.ui.viewmodel;

import android.app.MediaRouteButton;
import android.app.Notification;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRoomRepository;
import com.cleanup.todoc.data.repository.TaskRepository;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.model.TaskModelUi;
import com.cleanup.todoc.ui.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private ProjectRoomRepository mProjectRoomRepository;
    private TaskRepository mTaskRepository;

    private MediatorLiveData<List<TaskModelUi>> mTaskModelUiMediatorLiveData = new MediatorLiveData<>();
    private TextView mLblNoTasks;
    private RecyclerView mListTasks;
    private SortMethod sortMethod = SortMethod.NONE;

    public LiveData<List<TaskModelUi>> getTaskModelUiMediatorLiveData() {
        return mTaskModelUiMediatorLiveData;
    }

    @NonNull
    private final ArrayList<TaskModelUi> taskModelUiList = new ArrayList<>();

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

    public void deleteTask(TaskModelUi task){
        taskModelUiList.remove(task);
        mTaskModelUiMediatorLiveData.setValue(taskModelUiList);
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

    /**
     * Updates the list of tasks in the UI
     */
    public void updateTaskList(
            List<TaskModelUi> tasks,
            TextView lblNoTasks,
            RecyclerView listTasks, MainActivity.SortMethod sortMethod) {
        if (tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new TaskModelUi.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new TaskModelUi.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new TaskModelUi.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new TaskModelUi.TaskOldComparator());
                    break;
            }

            mTaskModelUiMediatorLiveData.setValue(tasks);
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
