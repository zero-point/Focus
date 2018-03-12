package uk.ac.gla.focuswatch.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;

/**
 * Created by root on 3/10/15.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
    int layoutResourceId;
    ArrayList<Task> data;

    public TaskAdapter(Context context, int layoutResourceId, ArrayList<Task> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public static class TaskHolder{
        TextView label;
        ImageView progress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder tHolder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            tHolder = new TaskHolder();
            tHolder.label = (TextView)row.findViewById(R.id.textBox);
            tHolder.progress = (ImageView)row.findViewById(R.id.taskBox);
            row.setTag(tHolder);

            row.setClickable(true);
            row.setFocusable(true);

            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TaskHolder th = (TaskHolder)v.getTag();
                    String label = th.label.getText().toString();
                    label = label.toLowerCase();

                    Task t = null;

                    for(int i = 0; i < data.size(); i++){
                        Task currTask = data.get(i);
                        String taskLabel = currTask.getLabel().toLowerCase();

                        if( taskLabel.contains(label) ){
                            t = currTask;
                            break;
                        }
                    }

                    if( t != null )
                        t.startTask((Activity)context);
                }
            });
        }
        else
            tHolder = (TaskHolder)row.getTag();

        Task t = (Task)data.get(position);
        String label = t.getLabel();

        tHolder.label.setText(label.toCharArray(), 0, label.length());
//        tHolder.progress.setChecked(t.isCompleted());

        return row;
    }

    @Override
    public boolean isEnabled(int position){
        return true;
    }
}
