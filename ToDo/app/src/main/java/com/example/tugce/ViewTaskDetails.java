package com.example.tugce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Tugce.
 */
public class ViewTaskDetails extends Activity  {
    private TextView taskName;
    private TextView taskDueDate;
    private TextView taskNotes;
    private TextView priorityLevel;
    private TextView status;
    private Task task=new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Intent intent=getIntent();
        task = (Task) intent.getSerializableExtra("Task");

        taskName=(TextView)findViewById(R.id.aTaskName);
        taskName.setText(task.getTaskName());

        taskDueDate=(TextView)findViewById(R.id.aDueDate);
        taskDueDate.setText(task.getDueDate());

        taskNotes=(TextView)findViewById(R.id.aTaskNotes);
        taskNotes.setText(task.getTaskDetails());

        priorityLevel=(TextView)findViewById(R.id.aPriorityLevel);
        priorityLevel.setText(task.getPriorityLevel());

        status=(TextView)findViewById(R.id.aStatus);
        status.setText(task.getStatus());
    }
    public void onEdit(View v){
        Intent intent=new Intent(ViewTaskDetails.this, EditTask.class);
        intent.putExtra("Task", (Serializable) task);
        startActivity(intent);
        finish();
    }
}
