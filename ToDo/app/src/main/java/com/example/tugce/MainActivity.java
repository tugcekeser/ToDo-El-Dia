package com.example.tugce;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {
    private TextView currentDate;
    private TextView numberOfTodo;
    private TextView numberOfInProgress;
    private TextView numberOfDone;
    private ArrayList<Task> todoTasks = new ArrayList<Task>();
    private ArrayList<Task> inProgressTasks = new ArrayList<Task>();
    private ArrayList<Task> doneTasks=new ArrayList<Task>();
    protected ArrayList<Task> allTasks = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentDate= (TextView)findViewById(R.id.date);
        DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy EEEE");
        Date date=new Date();
        currentDate.setText(dateFormat.format(date));

        numberOfTodo=(TextView)findViewById(R.id.numberOfTodo);
        numberOfInProgress=(TextView)findViewById(R.id.numberOfInProgress);
        numberOfDone=(TextView) findViewById(R.id.numberOfDone);

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

        // -- Get All Tasks --
        db.open();

        Cursor c = db.getAllTasks();

        if (c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Long.valueOf(c.getString(0)));
                task.setTaskName(c.getString(1));
                task.setDueDate(c.getString(2));
                task.setTaskDetails(c.getString(3));
                task.setPriorityLevel(c.getString(4));
                task.setStatus(c.getString(5));
                allTasks.add(task);

            } while (c.moveToNext());
        }
        db.close();

        if(allTasks.size()>0){
            for(int i=0;i<allTasks.size();i++){
                if(allTasks.get(i).getStatus().equals("TODO"))
                    todoTasks.add(allTasks.get(i));
                else if(allTasks.get(i).getStatus().equals("IN PROGRESS"))
                    inProgressTasks.add(allTasks.get(i));
                else if(allTasks.get(i).getStatus().equals("DONE"))
                    doneTasks.add(allTasks.get(i));
            }
        }
        numberOfTodo.setText(Integer.toString(todoTasks.size()));
        numberOfInProgress.setText(Integer.toString(inProgressTasks.size()));
        numberOfDone.setText(Integer.toString(doneTasks.size()));
    }

    // Show Todo tasks in the tab
    public void onShowListTodo(View v){
        Intent intent=new Intent(MainActivity.this, TabListTask.class);
        intent.putExtra("Todo", todoTasks);
        intent.putExtra("InProgress", inProgressTasks);
        intent.putExtra("Done", doneTasks);
        intent.putExtra("Position", 0);
        startActivity(intent);
        finish();
    }

    //Show In Progress tasks in the tab
    public void onShowListInProgress(View v){
        Intent intent=new Intent(MainActivity.this, TabListTask.class);
        intent.putExtra("Todo", todoTasks);
        intent.putExtra("InProgress", inProgressTasks);
        intent.putExtra("Done", doneTasks);
        intent.putExtra("Position", 1);
        startActivity(intent);
        finish();
    }

    //Show Done tasks in the tab
    public void onShowListDone(View v){
        Intent intent=new Intent(MainActivity.this, TabListTask.class);
        intent.putExtra("Todo", todoTasks);
        intent.putExtra("InProgress", inProgressTasks);
        intent.putExtra("Done", doneTasks);
        intent.putExtra("Position", 2);
        startActivity(intent);
        finish();
    }

}
