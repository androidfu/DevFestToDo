package com.chscodecamp.android.bettertodo;

import android.support.annotation.NonNull;

import java.util.List;

interface TaskStateManager {
    void saveTasks(@NonNull final List<Task> taskList);

    List<Task> loadTasks();
}
