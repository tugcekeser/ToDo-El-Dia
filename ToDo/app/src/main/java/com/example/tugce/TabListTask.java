package com.example.tugce;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import java.util.ArrayList;

/**
 * Created by Tugce.
 */
public class TabListTask extends TabActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Task> todoTasks =(ArrayList<Task>) getIntent().getSerializableExtra("Todo");
        ArrayList<Task> inProgressTasks=(ArrayList<Task>) getIntent().getSerializableExtra("InProgress");
        ArrayList<Task> doneTasks =(ArrayList<Task>) getIntent().getSerializableExtra("Done");
        int position= getIntent().getIntExtra("Position", 0);

        TabHost mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("todo").setIndicator("ToDo").setContent(new Intent(this, ListTasks.class).putExtra("List", todoTasks)));
        mTabHost.addTab(mTabHost.newTabSpec("inprogress").setIndicator("InProgress").setContent(new Intent(this, ListTasks.class).putExtra("List",inProgressTasks)));
        mTabHost.addTab(mTabHost.newTabSpec("done").setIndicator("Done").setContent(new Intent(this, ListTasks.class).putExtra("List", doneTasks)));
        mTabHost.setCurrentTab(position);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
