package com.chscodecamp.android.bettertodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements TaskRecyclerAdapter.Callback {

    private RecyclerView recyclerView;
    private EditText addItemEditText;
    private ImageButton addItem;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        addItemEditText = (EditText) findViewById(R.id.add_item_edit_text);
        addItem = (ImageButton) findViewById(R.id.add_item);

        tasks = TaskManager.getTasks();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addItemEditText.getText().toString().trim();
                if (tasks.indexOf(new Task(title)) >= 0) {
                    addItemEditText.setError(decorateError(getString(R.string.error_msg_items_must_be_unique), 0xFFFFFFFF));
                } else if (!TextUtils.isEmpty(title)) {
                    Task task = new Task(title);
                    TaskManager.addTask(task);
                    taskRecyclerAdapter.notifyItemInserted(taskRecyclerAdapter.getItemCount() - 1);
                    addItemEditText.setText(null);
                } else {
                    addItemEditText.setError(decorateError(getString(R.string.error_msg_todo_must_not_be_empty), 0xFFFFFFFF));
                }
            }
        });
        addItemEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addItem.performClick();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tasks.isEmpty()) {
            Task task = new Task("Start Learning Android!");
            task.setCompleted(true);
            TaskManager.addTask(task);
            task = new Task("Keep Learning Android!");
            TaskManager.addTask(task);
        }

        if (taskRecyclerAdapter == null) {
            taskRecyclerAdapter = new TaskRecyclerAdapter(tasks, this);
            recyclerView.setAdapter(taskRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onTaskUpdated(@NonNull Task task) {
        TaskManager.updateTask(task);
    }

    private CharSequence decorateError(String errorMessage, int color) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorMessage);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorMessage.length(), 0);
        return spannableStringBuilder;
    }
}
