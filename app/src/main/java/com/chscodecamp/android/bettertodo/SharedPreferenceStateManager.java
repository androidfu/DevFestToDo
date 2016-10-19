package com.chscodecamp.android.bettertodo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class SharedPreferenceStateManager implements TaskStateManager {

    private static final String TAG = SharedPreferenceStateManager.class.getSimpleName();
    private final Object tasks = new Object();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;

    @SuppressLint("CommitPrefEdits")
    SharedPreferenceStateManager(Application application) {
        sharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();
    }

    @Override
    public void saveTasks(@NonNull List<Task> taskList) {
        synchronized (tasks) {
            for (int i = 0; i < taskList.size(); i++) {
                try {
                    preferenceEditor.putString(String.format(Locale.ENGLISH, "%d", i), taskList.get(i).toJsonString()).apply();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    @Override
    @NonNull
    public List<Task> loadTasks() {
        List<Task> taskList = new ArrayList<>();
        synchronized (tasks) {
            Map<String, ?> keys = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Task task;
                try {
                    task = new Task((String) entry.getValue());
                } catch (JSONException | IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage());
                    continue;
                }
                taskList.add(task);
            }
        }
        return taskList;
    }
}
