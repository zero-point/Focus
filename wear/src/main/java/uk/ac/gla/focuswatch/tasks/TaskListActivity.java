package uk.ac.gla.focuswatch.tasks;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;
import uk.ac.gla.focuswatch.tasks.TasksGridPagerAdapter;

public class TaskListActivity extends Activity {

    public final static String EXTRA_MESSAGE = "uk.ac.gla.focuswatch.tasks.MESSAGE";

    protected static final int RESULT_SPEECH = 1;
    public static final int RESULT_COMPLETE = 666;

    private TasksGridPagerAdapter taskGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the fragments for the tasks activity
        setContentView(R.layout.activity_demo);

        taskGridAdapter = new TasksGridPagerAdapter(this, getFragmentManager());

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(taskGridAdapter);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TasksFragment f = (TasksFragment) taskGridAdapter.getFragment(0, 0);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    // We have speech input success
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String[] words = text.get(0).split(" ");

                    if( words.length > 2 && words[0].equalsIgnoreCase("create")
                            && words[1].equalsIgnoreCase("task")) {
                        f.createTask(words);
                    }
                    else if( words.length > 2 && words[0].equalsIgnoreCase("focus")
                            && words[1].equalsIgnoreCase("on")){
                        f.focusOnTask(words);
                    }
                    else if( words.length > 1 && words[0].equalsIgnoreCase("complete")){
                        f.completeTask(words);
                    }
                    else{
                        Toast notFound = Toast.makeText(this,
                                "Sorry, didn't catch that.",
                                Toast.LENGTH_SHORT);
                        notFound.show();
                    }
                }

                break;
            }
            case RESULT_COMPLETE:{
                if ( resultCode == Activity.RESULT_OK && data != null ) {
                    Serializable s = data.getSerializableExtra(Task.TASK_COMPLETE);

                    if( s instanceof Task )
                    f.completeActivateTask((Task)s);
                }

                break;
            }

        }
    }// end of method
}
