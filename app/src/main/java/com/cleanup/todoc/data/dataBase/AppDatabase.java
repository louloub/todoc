package com.cleanup.todoc.data.dataBase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dao.TaskDao;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    private static AppDatabase sInstance;

    public static AppDatabase getInstance() {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            MainApplication.getInstance(),
                            AppDatabase.class,
                            "Database.db"
                    ).build();
                }
            }
        }

        return sInstance;
    }
}