package com.example.tugce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tugce.
 */
public class ListTasks extends Activity {
    public  ArrayList<Task>tasks;
    CustomAdapter tasksAdapter;
    ListView lvTasks;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_tasks);

            tasks =(ArrayList<Task>) getIntent().getSerializableExtra("List");
            tasksAdapter=new CustomAdapter(this,tasks);
            lvTasks=(ListView)findViewById(R.id.ListTasks);
            lvTasks.setAdapter(tasksAdapter);
            setupListViewListener();
        }

    //Changed LongClickListener
    private void setupListViewListener(){
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                tasksAdapter.notifyDataSetChanged();
                Intent intent = new Intent(ListTasks.this, ViewTaskDetails.class);
                intent.putExtra("Task", (Serializable) tasks.get(pos));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //@Override
   /* public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    //Request for adding new task
    public void onAddTask(View v){
       Intent intent=new Intent(ListTasks.this, AddNewTask.class);
        startActivity(intent);
        finish();
    }
}
