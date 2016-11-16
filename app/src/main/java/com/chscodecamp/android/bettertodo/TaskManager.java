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
        taskList.clear();
        List<Task> storedTasks = this.taskStateManager.loadTasks();
        if (storedTasks.isEmpty()) {
            storedTasks = this.createDefaultEntries();
        }
        taskList.addAll(storedTasks);
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
        return getInstance().taskList;
    }

    static void addTask(@NonNull final Task task) {
        getInstance().taskList.add(task);
        getInstance().saveTasks(getTasks());
    }

    static void updateTask(@NonNull final Task task) {
        getInstance().taskList.set(getInstance().taskList.indexOf(task), task);
        getInstance().saveTasks(getTasks());
    }

    private void saveTasks(@NonNull final List<Task> taskList) {
        getInstance().taskStateManager.saveTasks(taskList);
    }

    @NonNull
    private List<Task> createDefaultEntries() {
        final List<Task> defaultTasks = new ArrayList<>();
        Task task = new Task("Start Learning Android!");
        task.setCompleted(true);
        defaultTasks.add(task);
        task = new Task("Keep Learning Android!");
        task.setCompleted(true);
        defaultTasks.add(task);
        task = new Task("Tasks in the Cloud!");
        defaultTasks.add(task);
        return defaultTasks;
    }
}
