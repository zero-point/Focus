package uk.ac.gla.focuswatch.model;

import uk.ac.gla.focuswatch.util.Date;

/**
 * Created by pi2 on 11/03/15.
 */
public class TaskSession {
    /**
     * Our task session model.
     * Every time a user starts focusing on a task, a new entry is added.
     */
    private int id;
    private int taskId;
    private String startTime;
    private int timeSpent;

    public TaskSession(){
        id = 0;
        taskId = 0;
        startTime = Date.utcnow();
        timeSpent = 0;
    }

    public TaskSession(int id, int toTaskId){
        this.id = id;
        taskId = toTaskId;
        startTime = Date.utcnow();
        timeSpent = 0;
    }

    public int getId() {
        return id;
    }
    public int getTaskId() {
        return taskId;
    }
    public String getStartTime() {
        return startTime;
    }
    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
}
