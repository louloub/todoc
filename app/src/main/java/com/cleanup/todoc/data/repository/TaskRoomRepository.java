package com.cleanup.todoc.data.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dao.TaskDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRoomRepository implements TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mTaskListLiveData = new MutableLiveData<>();

    public TaskRoomRepository() {
        AppDatabase db = AppDatabase.getInstance();
        mTaskDao = db.taskDao();
        mTaskListLiveData = mTaskDao.getListTaskLiveData();
    }

    @Override
    public LiveData<List<Task>> getTaskListLiveData() {
        return mTaskListLiveData;
    }

    @Override
    public void addTask(Task task) {

        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insertTask(params[0]);
            return null;
        }
    }
}
