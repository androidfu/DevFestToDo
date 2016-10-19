package com.chscodecamp.android.bettertodo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class TaskManager {
    private static TaskManager instance;
    private final List<Task> taskList = new ArrayList<>();
    private final TaskStateManager taskStateManager;

    private TaskManager(TaskStateManager taskStateManager) {
        this.taskStateManager = taskStateManager;
        synchronized (taskList) {
            taskList.clear();
            taskList.addAll(this.taskStateManager.loadTasks());
        }
    }

    static void init(final TaskStateManager taskStateManager) {
        if (instance == null) {
            instance = new TaskManager(taskStateManager);
        }
    }

    @NonNull
    private static TaskManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("Call TaskManager.init() first.");
        }
        return instance;
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    static List<Task> getTasks() {
        synchronized (getInstance().taskList) {
            return getInstance().taskList;
        }
    }

    private static void saveTasks(@NonNull final List<Task> taskList) {
        synchronized (getInstance().taskList) {
            getInstance().taskStateManager.saveTasks(taskList);
        }
    }

    static void updateTasks() {
        saveTasks(getTasks());
    }

    static void addTask(Task task) {
        synchronized (getInstance().taskList) {
            getInstance().taskList.add(task);
        }
        saveTasks(getTasks());
    }
}
