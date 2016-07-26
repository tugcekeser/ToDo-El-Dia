package com.example.tugce;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tugce.
 */
public class Task implements Serializable {
    private Long id;
    private String taskName=null;
    private String dueDate=null;
    private String taskDetails=null;
    private String priorityLevel=null;
    private String status=null;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setPriorityLevel(String priorityLevel) {
           this.priorityLevel = priorityLevel;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
