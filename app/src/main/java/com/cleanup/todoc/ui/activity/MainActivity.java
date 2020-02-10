package com.cleanup.todoc.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.model.TaskModelUi;
import com.cleanup.todoc.ui.adapter.TasksAdapter;
import com.cleanup.todoc.ui.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    TaskViewModel mTaskViewModel;

    private final ProjectModelUi[] mAllProjects = ProjectModelUi.getAllProjects();

    @NonNull
    private List<TaskModelUi> mTaskListUiModel = new ArrayList<>();

    private final TasksAdapter mTaskAdapter = new TasksAdapter(mTaskListUiModel, this);

    @NonNull
    private SortMethod mSortMethod = SortMethod.NONE;

    @Nullable
    public AlertDialog mAlertDialog = null;

    @Nullable
    private EditText mDialogEditText = null;

    @Nullable
    private Spinner mDialogSpinner = null;

    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView mRecyclerView;

    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView mTextViewNoTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.list_tasks);
        mTextViewNoTask = findViewById(R.id.lbl_no_task);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mTaskAdapter);

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

        mTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TaskViewModel.class);

        mTaskViewModel.getTaskModelUiMediatorLiveData().observe(this, new Observer<List<TaskModelUi>>() {
            @Override
            public void onChanged(List<TaskModelUi> taskModelUiList) {
                mTaskAdapter.updateTasks(taskModelUiList);
                mTaskListUiModel = taskModelUiList;
                if (taskModelUiList.isEmpty()){
                    mTextViewNoTask.setVisibility(View.VISIBLE);
                } else {
                    mTextViewNoTask.setVisibility(View.GONE);
                }
            }
        });

        mTaskViewModel.getSortMethodLiveData().observe(this, new Observer<SortMethod>() {
            @Override
            public void onChanged(SortMethod sortMethod) {
                mSortMethod = sortMethod;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            mSortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            mSortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            mSortMethod = SortMethod.RECENT_FIRST;
        }

        updateTaskList();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(TaskModelUi task) {
        mTaskViewModel.deleteTask(task);
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        mTaskViewModel.addNewTask(mDialogEditText,mDialogSpinner,dialogInterface);
        updateTaskList();
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        mDialogEditText = dialog.findViewById(R.id.txt_task_name);
        mDialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    private void updateTaskList() {
        mTaskViewModel.updateTaskList(mTaskListUiModel,mTextViewNoTask,mRecyclerView,mSortMethod);
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mDialogEditText = null;
                mDialogSpinner = null;
                mAlertDialog = null;
            }
        });

        mAlertDialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(mAlertDialog);
                    }
                });
            }
        });

        return mAlertDialog;
    }

    private void populateDialogSpinner() {
        final ArrayAdapter<ProjectModelUi> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mAllProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (mDialogSpinner != null) {
            mDialogSpinner.setAdapter(adapter);
        }
    }

    public enum SortMethod {
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
