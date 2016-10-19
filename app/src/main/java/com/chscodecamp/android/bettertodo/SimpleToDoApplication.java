package com.chscodecamp.android.bettertodo;

import android.app.Application;

public class SimpleToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TaskManager.init(new SharedPreferenceStateManager(this));
    }
}
