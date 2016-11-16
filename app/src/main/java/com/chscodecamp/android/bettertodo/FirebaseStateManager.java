package com.chscodecamp.android.bettertodo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class FirebaseStateManager implements TaskStateManager {

    private static final String TAG = FirebaseStateManager.class.getSimpleName();
    private final DatabaseReference databaseReference;

    FirebaseStateManager(@NonNull final Application application) {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference(this.getApplicationId(application));
    }

    @Override
    public void saveTasks(@NonNull final List<Task> taskList) {
        databaseReference.setValue(taskList);
    }

    @NonNull
    public List<Task> loadTasks() {
        final List<Task> taskList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot taskDbEntry : dataSnapshot.getChildren()) {
                    if (taskDbEntry.getValue(Task.class) != null) {
                        taskList.add(taskDbEntry.getValue(Task.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
        return taskList;
    }

    @NonNull
    private String getApplicationId(@NonNull final Context context) {
        final String keyPrefsApplicationId = "applicationId";
        SharedPreferences sharedPreferences = context.getSharedPreferences(DevFestToDoApplication.class.getCanonicalName(), Context.MODE_PRIVATE);
        String applicationId = sharedPreferences.getString(keyPrefsApplicationId, null);
        if (TextUtils.isEmpty(applicationId)) {
            applicationId = UUID.randomUUID().toString();
            sharedPreferences.edit().putString(keyPrefsApplicationId, applicationId).apply();
        }
        return applicationId;
    }
}
