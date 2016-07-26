package com.example.tugce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Tugce.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<Task> result;
    private static LayoutInflater inflater=null;

    public CustomAdapter(ListTasks listTasks, ArrayList<Task> tasks) {
        result=tasks;
        inflater = ( LayoutInflater )listTasks.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        RatingBar ratingBar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_list_tasks_row, null);

        //Task Name
        holder.tv=(TextView) rowView.findViewById(R.id.txtNameInList);
        holder.tv.setText(result.get(position).getTaskName());

        //Priority Level
        holder.tv=(TextView) rowView.findViewById(R.id.txtPLinList);
        holder.tv.setText(result.get(position).getPriorityLevel());

        return rowView;
    }
}
