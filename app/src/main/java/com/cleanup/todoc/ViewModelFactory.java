package com.cleanup.todoc;

import androidx.annotation.NonNull;
    import androidx.lifecycle.ViewModel;
    import androidx.lifecycle.ViewModelProvider;

    import com.cleanup.todoc.data.repository.ProjectRepositoryImpl;
    import com.cleanup.todoc.data.repository.ProjectRoomRepository;
    import com.cleanup.todoc.data.repository.TaskRepositoryImpl;
    import com.cleanup.todoc.data.repository.TaskRoomRepository;
    import com.cleanup.todoc.ui.viewmodel.TaskViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private ViewModelFactory(

    ) {

    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(

                    );
                }
            }
        }

        return sFactory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(
                    new ProjectRoomRepository(),
                    new TaskRoomRepository()
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}