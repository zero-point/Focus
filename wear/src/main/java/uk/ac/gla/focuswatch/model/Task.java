package uk.ac.gla.focuswatch.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

import uk.ac.gla.focuswatch.tasks.TaskActiveActivity;
import uk.ac.gla.focuswatch.tasks.TaskListActivity;
import uk.ac.gla.focuswatch.tasks.TasksFragment;
import uk.ac.gla.focuswatch.util.Date;

public class Task implements Serializable {
    /**
     * Our task model. Keeps reference to the tasks created by the user with state info.
     */

    private int id;
    private String label;
    private String created;
    private int elapsed;
    private boolean completed;

    public static final String TASK_COMPLETE = "TASK_COMPLETE";

    public Task(){
        id = 0;
        label = "";
        created = Date.utcnow();
        elapsed = 0;
        completed = false;
    }

    public Task(int id, String label){
        this.id = id;
        this.label = label;
        created = Date.utcnow();
        elapsed = 0;
        completed = false;
    }

    // ----------------------- Setters -----------------------
    public void setId(int id) { this.id = id; }
    public void setLabel(String label){ this.label = label; }
    public void setCreated(String created) { this.created = created; }
    public void setElapsed(int elapsed) { this.elapsed = elapsed; }
    public void finish(){ completed = true; }
    public void undoFinish() { completed = false; }

    // ----------------------- Getters -----------------------
    public int getId(){ return id; }
    public String getLabel(){ return label; }
    public String getCreated() { return created; }
    public int getElapsed() { return elapsed; }
    public boolean isCompleted(){ return completed; }

    public void startTask(Activity a){
        Intent i = new Intent(a, TaskActiveActivity.class);
        i.putExtra(TaskListActivity.EXTRA_MESSAGE, this);
        a.startActivityForResult(i, TaskListActivity.RESULT_COMPLETE);
    }
}
