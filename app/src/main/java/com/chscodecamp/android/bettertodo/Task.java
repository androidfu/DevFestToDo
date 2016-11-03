package com.chscodecamp.android.bettertodo;

class Task {
    private String title;
    private boolean completed;

    Task(String title) {
        this.title = title;
    }

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

    String getTitle() {
        return title;
    }

    boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
