package com.example.ticktask;

import com.example.ticktask.model.MyTask;

public interface TaskListener {
    public void onTaskStatusUpdate(MyTask task);

    void deleteTasks(MyTask task, int index);

    public void onTaskDelete(MyTask task, int index);
}
