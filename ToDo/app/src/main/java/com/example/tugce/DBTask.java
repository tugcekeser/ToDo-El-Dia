package com.example.tugce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tugce.
 */
public class DBTask {
    public static final String KEY_ROWID = "Rowid";
    public static final String KEY_TASKNAME = "TaskName";
    public static final String KEY_DUEDATE = "DueDate";
    public static final String KEY_TASKDETAILS= "TaskDetails";
    public static final String KEY_PRIORITYLEVEL="PriorityLevel";
    public static final String KEY_STATUS="Status";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_CREATE = "create table if not exists Task (Rowid integer primary key autoincrement, TaskName text not null, DueDate text not null," +
            "TaskDetails text, PriorityLevel text, Status text);";


    private static final String DATABASE_NAME = "todo";
    private static final String DATABASE_TABLE = "Task";
    private static final int DATABASE_VERSION = 2;
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBTask(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Task");
            onCreate(db);
        }
    }

    public DBTask open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertTask(Task task) {
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_ROWID,task.getId());
        initialValues.put(KEY_TASKNAME, task.getTaskName());
        initialValues.put(KEY_DUEDATE, task.getDueDate());
        initialValues.put(KEY_TASKDETAILS, task.getTaskDetails());
        initialValues.put(KEY_PRIORITYLEVEL,task.getPriorityLevel());
        initialValues.put(KEY_STATUS,task.getStatus());
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTask(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllTasks() {
        return db.rawQuery("select * from " + DATABASE_TABLE,null);
    }

    public Cursor getTask(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_TASKNAME, KEY_DUEDATE,
                        KEY_TASKDETAILS, KEY_PRIORITYLEVEL, KEY_STATUS}, KEY_ROWID + "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateTask(Task task) {
        ContentValues args = new ContentValues();
        args.put(KEY_TASKNAME, task.getTaskName());
        args.put(KEY_DUEDATE, task.getDueDate());
        args.put(KEY_TASKDETAILS, task.getTaskDetails());
        args.put(KEY_PRIORITYLEVEL, task.getPriorityLevel());
        args.put(KEY_STATUS, task.getStatus());
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + task.getId(), null) > 0;
    }

}
