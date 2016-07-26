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
public class EditTask extends Activity {
    private EditText taskName;
    private DatePicker datePicker;
    private EditText taskNotes;
    private Spinner priorityLevel;
    private Spinner status;
    private ArrayAdapter<CharSequence> adapter;
    private Task task=new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent intent=getIntent();
        task = (Task) intent.getSerializableExtra("Task");

        //Task Name
        taskName=(EditText)findViewById(R.id.eTaskName);
        taskName.setText(task.getTaskName());

        //DatePicker
        datePicker = (DatePicker) findViewById(R.id.dpDueDate);
        String[] dateParse= task.getDueDate().split("/");
        datePicker.init(Integer.parseInt(dateParse[2]),Integer.parseInt(dateParse[0])-1,Integer.parseInt(dateParse[1]),null);

        //Task Notes
        taskNotes=(EditText)findViewById(R.id.eTaskNotes);
        taskNotes.setText(task.getTaskDetails());

        //Priority Level
        priorityLevel = (Spinner) findViewById(R.id.sPriorityLevel);
        adapter = ArrayAdapter.createFromResource(this, R.array.PriorityLevel, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        priorityLevel.setAdapter(adapter);
        priorityLevel.setSelection(adapter.getPosition(task.getPriorityLevel()));

        //Status
        status = (Spinner) findViewById(R.id.sStatus);
        adapter = ArrayAdapter.createFromResource(this,R.array.Status,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        status.setAdapter(adapter);
        status.setSelection(adapter.getPosition(task.getStatus()));
    }

    //Update Task
    public void onUpdate(View v){
        Task updatedTask=new Task();

        //Id
        updatedTask.setId(task.getId());

        //Task Name
        updatedTask.setTaskName(taskName.getText().toString());

        //DatePicher
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth() + 1;
        int   year = datePicker.getYear();
        updatedTask.setDueDate(GeneralFunctions.checkDigit(month) + "/" + GeneralFunctions.checkDigit(day) + "/" + String.valueOf(year));

        //Task Notes
        updatedTask.setTaskDetails(taskNotes.getText().toString());

        //Rating Bar
        updatedTask.setPriorityLevel((priorityLevel.getSelectedItem().toString()));

        //Spinner
        updatedTask.setStatus(status.getSelectedItem().toString());

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

        // -- Update Task --
        db.open();
        db.updateTask(updatedTask);
        db.close();

        AlertDialog alertDialog = new AlertDialog.Builder(
                EditTask.this).create();

        // Setting Dialog Message
        alertDialog.setMessage("Updated successfully!");

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    //Delete Task
    public void onDelete(View v){
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

        // -- Update Task --
        db.open();
        db.deleteTask(task.getId());
        db.close();

        AlertDialog alertDialog = new AlertDialog.Builder(
                EditTask.this).create();

        // Setting Dialog Message
        alertDialog.setMessage("Deleted successfully!");

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
}
