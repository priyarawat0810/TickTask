package com.example.ticktask.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktask.MainActivity;
import com.example.ticktask.R;
import com.example.ticktask.TaskListener;
import com.example.ticktask.model.MyTask;
import com.example.ticktask.utils.TaskPriority;
import com.example.ticktask.utils.TaskStatus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    Context context;
    List<MyTask> myTaskList;
    Map<TaskPriority,Integer> priorityColorMap = new HashMap<>();
    TaskListener taskListener;

    public MyTaskAdapter(Context context, List<MyTask> myTaskList) {
        this.context = context;
        this.myTaskList = myTaskList;
        this.taskListener = (TaskListener) context;
        priorityColorMap.put(TaskPriority.HIGH, R.color.priorityHigh);
        priorityColorMap.put(TaskPriority.MEDIUM,R.color.priorityMedium);
        priorityColorMap.put(TaskPriority.LOW,R.color.priorityLow);
        priorityColorMap.put(TaskPriority.NONE,R.color.priorityNone);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_task,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MyTask myTask = myTaskList.get(position);
        holder.t1.setText(myTask.getTask());
        holder.t2.setText(myTask.getDsc());
        holder.status.setBackgroundColor(context.getResources().getColor(priorityColorMap.get(myTask.getTaskPriority())));
        holder.checkBox.setChecked(myTask.getTaskStatus().equals(TaskStatus.COMPLETE));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.setTaskStatus(holder.checkBox.isChecked()? TaskStatus.COMPLETE:myTask.getTaskStatus());
                taskListener.onTaskStatusUpdate(myTask);
            }
        });
        holder.t3.setText(myTask.getCreateDate()==null? Calendar.getInstance().getTime().toString():myTask.getCreateDate().toString());

        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListener.deleteTasks(myTask, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2,t3;
            View status;
            CheckBox checkBox;
            ImageView delete_img;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                t1 = itemView.findViewById(R.id.t1);
                t2 = itemView.findViewById(R.id.t2);
                t3 = itemView.findViewById(R.id.t3);
                status = itemView.findViewById(R.id.status);
                checkBox = itemView.findViewById(R.id.checkbox);
                delete_img = itemView.findViewById(R.id.delete_img);
            }


        }

}
