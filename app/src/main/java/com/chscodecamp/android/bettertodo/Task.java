package com.chscodecamp.android.bettertodo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import org.json.JSONException;
import org.json.JSONObject;

class Task implements Parcelable {
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    private static final String TITLE = "title";
    private static final String COMPLETED = "completed";
    private String title;
    private boolean completed;

    Task() {
        // Required by Parcelable
    }

    @SuppressWarnings({"ConstantConditions", "WeakerAccess"})
    Task(@NonNull JSONObject jsonObject) throws JSONException, IllegalArgumentException {
        if (jsonObject == null) {
            throw new IllegalArgumentException("You must provide a non-Null JSONObject to hydrate this object.");
        }
        title = jsonObject.getString(TITLE);
        completed = jsonObject.getBoolean(COMPLETED);
    }

    Task(@NonNull @Size(min = 2) final String jsonString) throws JSONException, IllegalArgumentException {
        this(new JSONObject(jsonString));
    }

    private Task(Parcel in) {
        this.title = in.readString();
        this.completed = in.readByte() != 0;
    }

    @SuppressWarnings("WeakerAccess")
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("WeakerAccess")
    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("WeakerAccess")
    boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    String toJsonString() throws JSONException {
        JSONObject task = new JSONObject();
        task.put(TITLE, getTitle());
        task.put(COMPLETED, isCompleted());
        return task.toString();
    }

    @SuppressWarnings("unused")
    JSONObject toJsonObject() throws JSONException {
        return new JSONObject(toJsonString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (isCompleted() != task.isCompleted()) return false;
        return getTitle().equals(task.getTitle());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + (isCompleted() ? 1 : 0);
        return result;
    }
}
