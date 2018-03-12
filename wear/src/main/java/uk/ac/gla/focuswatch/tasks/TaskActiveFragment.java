package uk.ac.gla.focuswatch.tasks;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;
import uk.ac.gla.focuswatch.util.TextTimer;

public class TaskActiveFragment extends Fragment {
    /**
     * TODO: Vibrate and change Pause button to "Continue" when count reaches 10 (or N) minutes (configurable in settings)
     * TODO: Hook Pause button - endTaskSession, get back to TaskListActivity
     * TODO: Hook Complete button - endTaskSession, display message that the task is completed AND MARK TASK AS COMPLETE
     * Displays a timer and a the label of the task that is executing (if any).
     * The user can mark a task as complete or pause working on task. On time up, it vibrates.
     * Fired when a task is active from one of the following initiators:
     *  - Selecting a task from the list view of TaskListActivity
     *  - "Start Focus " (system wide voice command)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mInflater = inflater.inflate(R.layout.task_active_fragment, container, false);
        return mInflater;
    }

    private int mInterval = 1000;
    private TextView taskTimer;
    private TextTimer textTimer;
    private Handler mHandler;
    private ImageButton pauseTask;

    private int nPauseClicks = 0;
    private TaskActiveActivity theActivity;
    private Task t;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskTimer = (TextView) getView().findViewById(R.id.task_timer);
        theActivity = (TaskActiveActivity) getActivity();
        t = theActivity.getTask();

        TextView taskLabel = (TextView) getView().findViewById(R.id.task_label);
        // obtain buttons
        pauseTask = (ImageButton) getView().findViewById(R.id.task_pause);

        pauseTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseClicked(v);
            }
        });

        Intent intent = getActivity().getIntent();
        Serializable serializable = intent.getSerializableExtra(TaskListActivity.EXTRA_MESSAGE);
        if (serializable == null) {
            // there is no task_id passed in the intent, probably we've been called from
            // system wide voice action
            taskLabel.setText("");
        } else if (serializable instanceof Task) {
            // we have a task passed from TaskListActivity
            theActivity.setTask((Task) serializable);
            t = theActivity.getTask();
            taskLabel.setText(t.getLabel());
        } else {
            assert false; // We should not reach here - Task object should be passed or nothing
        }

        // Create a text timer
        String timerValue = taskTimer.getText().toString();
        theActivity.setTextTimer(new TextTimer(timerValue));
        textTimer = theActivity.getTextTimer();

        // Create a handler to handle updating of the ui component
        mHandler = new Handler();
        startRepeatingTask();

    }

    Runnable mTimerUpdater = new Runnable() {
        @Override
        public void run() {
            updateTimer(); //this function can change value of mInterval.
            mHandler.postDelayed(mTimerUpdater, mInterval);
        }
    };

    void startRepeatingTask() {
        mTimerUpdater.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mTimerUpdater);
    }

    public Drawable getPlayButton(){
        Resources resources = getResources();
        final Drawable playPic = resources.getDrawable(R.drawable.play);
        return playPic;
    }

    public Drawable getPauseButton(){
        Resources resources = getResources();
        final Drawable playPic = resources.getDrawable(R.drawable.pause);
        return playPic;
    }

    public void pauseClicked(View v){
        if( nPauseClicks < 1 ) {
            stopRepeatingTask();
            pauseTask.setBackground(getPlayButton());

            nPauseClicks++;
        }
        else if( nPauseClicks >= 1 ) {
            startRepeatingTask();
            pauseTask.setBackground(getPauseButton());

            nPauseClicks--;
        }
    }

    void updateTimer() {
        // Increment and update UI
        taskTimer.setText(textTimer.increment());
    }
}
