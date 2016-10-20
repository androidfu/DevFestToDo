package com.chscodecamp.android.bettertodo;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {

    private List<Task> tasks;

    TaskRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_todo_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {

        final Task task = tasks.get(position);

        holder.itemName.setText(task.getTitle());
        setStrikethrough(task.isCompleted(), holder.itemName);

        holder.checkBox.setChecked(task.isCompleted());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompleted(isChecked);
                TaskManager.updateTasks();
                setStrikethrough(task.isCompleted(), holder.itemName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void setStrikethrough(boolean checked, TextView textView) {
        if (checked) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(0);
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private CheckBox checkBox;

        TaskViewHolder(View v) {
            super(v);
            itemName = (TextView) v.findViewById(R.id.item_name);
            checkBox = (CheckBox) v.findViewById(R.id.item_checkbox);
        }
    }
}