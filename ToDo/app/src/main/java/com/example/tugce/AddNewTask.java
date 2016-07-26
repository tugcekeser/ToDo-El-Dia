package com.example.tugce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tugce.
 */
public class AddNewTask extends Activity {
    private EditText taskName;
    private DatePicker datePicker;
    private EditText taskNotes;
    private Spinner priorityLevel;
    private Spinner status;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Task Name
        taskName=(EditText)findViewById(R.id.eTaskName);

        //DatePicker
        datePicker = (DatePicker) findViewById(R.id.dpDueDate);

        //Task Notes
        taskNotes=(EditText)findViewById(R.id.eTaskNotes);

        //Priority Level
        priorityLevel = (Spinner) findViewById(R.id.sPriorityLevel);
        adapter = ArrayAdapter.createFromResource(this,R.array.PriorityLevel,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        priorityLevel.setAdapter(adapter);

        //Status
        status = (Spinner) findViewById(R.id.sStatus);
        adapter = ArrayAdapter.createFromResource(this,R.array.Status,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        status.setAdapter(adapter);
    }

    //Add New Task
    public void onSave(View v){
        Task task=new Task();

        //Task Name
        task.setTaskName(taskName.getText().toString());

        //DatePicher
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth() + 1;
        int   year = datePicker.getYear();
        task.setDueDate(GeneralFunctions.checkDigit(month) + "/" + GeneralFunctions.checkDigit(day) + "/" + String.valueOf(year));

        //Task Notes
        task.setTaskDetails(taskNotes.getText().toString());

        //Rating Bar
        task.setPriorityLevel((priorityLevel.getSelectedItem().toString()));

        //Spinner
        task.setStatus(status.getSelectedItem().toString());

        //---- Open DB ---
        try {
            String destPath = "/data/data/" + getPackageName()
                    + "/databases/todo";
            File f = new File(destPath);
            if (!f.exists()) {
                GeneralFunctions.CopyDB(getBaseContext().getAssets().open("todo"),
                        new FileOutputStream(destPath));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBTask db = new DBTask(this);

        // -- Add Task --
        db.open();
        db.insertTask(task);
        db.close();

        AlertDialog alertDialog = new AlertDialog.Builder(
                AddNewTask.this).create();

        // Setting Dialog Message
        alertDialog.setMessage("Added successfully!");

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(AddNewTask.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
