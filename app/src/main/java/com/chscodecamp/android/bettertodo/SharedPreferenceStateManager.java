package com.chscodecamp.android.bettertodo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SharedPreferenceStateManager implements TaskStateManager {

    private static final String SAVED_TASKS = "savedTasks";
    private SharedPreferences sharedPreferences;

    SharedPreferenceStateManager(Application application) {
        sharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    public void saveTasks(@NonNull List<Task> taskList) {
        sharedPreferences.edit().putString(SAVED_TASKS, new Gson().toJson(taskList)).commit();
    }

    @NonNull
    public List<Task> loadTasks() {
        Task[] savedTasks = new Gson().fromJson(sharedPreferences.getString(SAVED_TASKS, null), Task[].class);
        if (savedTasks != null && savedTasks.length > 0) {
            return new ArrayList<>(Arrays.asList(savedTasks));
        } else {
            return new ArrayList<>();
        }
    }
}
