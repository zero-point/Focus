package uk.ac.gla.focuswatch.tasks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;
import uk.ac.gla.focuswatch.util.TextTimer;

public class TaskCompleteFragment extends Fragment {
    /**
     * Used just to mark task as complete and return to task list
     */

    private TaskActiveActivity theActivity;
    private Task t;
    private TextTimer textTimer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mInflater = inflater.inflate(R.layout.task_complete_fragment, container, false);
        theActivity = (TaskActiveActivity) getActivity();
        t = theActivity.getTask();
        textTimer = theActivity.getTextTimer();
        return mInflater;
    }
}
