package com.chscodecamp.android.bettertodo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.UUID;

public class DevFestToDoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TaskManager.init(new SharedPreferenceStateManager(this));
        TaskManager.init(new FirebaseStateManager(this));
    }
}
