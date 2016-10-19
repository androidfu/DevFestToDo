package com.chscodecamp.android.bettertodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ListActivity extends AppCompatActivity {

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

        tasks = TaskManager.getTasks();
        if (tasks.isEmpty()) {
            Task task = new Task();
            task.setTitle("My first task");
            TaskManager.addTask(task);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        taskRecyclerAdapter = new TaskRecyclerAdapter(tasks);
        recyclerView.setAdapter(taskRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addItemEditText = (EditText) findViewById(R.id.add_item_edit_text);
        addItem = (ImageButton) findViewById(R.id.add_item);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addItemEditText.getText().toString();
                if (title.length() > 0) {
                    Task task = new Task();
                    task.setTitle(title);
                    TaskManager.addTask(task);
                    taskRecyclerAdapter.notifyItemInserted(taskRecyclerAdapter.getItemCount() - 1);
                    addItemEditText.setText("");
                }
            }
        });

    }
}
