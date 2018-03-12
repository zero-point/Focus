package uk.ac.gla.focuswatch.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDataSource {
    /**
     * This class is our DAO.
     * It maintains the database connection, supports adding new tasks, fetching all tasks,
     * starting and ending task sessions.
     *
     * Use it in an Activity as:
     * datasource = new TasksDataSource(this);
     * datasource.open();
     * // do things with datasource
     * datasource.close();
     *
     */

    // Database fields
    private SQLiteDatabase database;
    private TaskOpenHelper dbHelper;
    private String[] allColumns = {  };

    public TasksDataSource(Context context) {
        dbHelper = new TaskOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Task> getAllTasks() {
        /**
         * TODO: Order by recent used (join with TaskSession, order by startTime)
         * Returns a list of all tasks
         */
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(TaskOpenHelper.TASK_TABLE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public void createTask(String label) {
        /**
         * Creates new task with label
         */
        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.FIELD_TASK_LABEL, label);
        long insertId = database.insert(TaskOpenHelper.TASK_TABLE, null,
                values);
        Cursor cursor = database.query(TaskOpenHelper.TASK_TABLE,
                allColumns, TaskOpenHelper.FIELD_TASK_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public boolean completeTask(int task_id) {

        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.FIELD_TASK_COMPLETED, "true");
        long noOfRows = database.update(TaskOpenHelper.TASK_TABLE,values,TaskOpenHelper.FIELD_TASK_ID+'='+task_id,null);
        if (noOfRows > 0)   return true;
        else    return false;
    }

    public boolean startTaskSession(int task_id) {

        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.FIELD_TASK_FK_ID, task_id);
        long id = database.insert(TaskOpenHelper.TASK_SESSION_TABLE,null,values);
        if (id != -1)   return true;
        else    return false;
    }

    public boolean endTaskSession(int task_id) {

        ContentValues values = new ContentValues();
        values.put(TaskOpenHelper.FIELD_TASK_COMPLETED, "true");
        long noOfRows = database.update(TaskOpenHelper.TASK_SESSION_TABLE,values,TaskOpenHelper.FIELD_TASK_ID+'='+task_id,null);
        if (noOfRows > 0)   return true;
        else    return false;
    }

    public Task getTaskById(int task_id) {

        Cursor cursor = database.query(TaskOpenHelper.TASK_TABLE,
                allColumns, TaskOpenHelper.FIELD_TASK_ID + " = " + task_id, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            Task task = cursorToTask(cursor);
            if (task.getId() != 0)   return task;
        }
        return null;
    }

    private Task cursorToTask(Cursor cursor) {
        /**
         * Converts a db cursor to Task object
         */
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setLabel(cursor.getString(1));
        return task;
    }
}
