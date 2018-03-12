package uk.ac.gla.focuswatch.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;
import uk.ac.gla.focuswatch.util.TextTimer;

public class TaskActiveActivity extends FragmentActivity {

    private Task t;
    private TextTimer textTimer;

    public Task getTask() {
        return t;
    }

    public void setTask(Task t) {
        this.t = t;
    }

    public TextTimer getTextTimer() {
        return textTimer;
    }

    public void setTextTimer(TextTimer textTimer) {
        this.textTimer = textTimer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_active_activity);
        final GridViewPager pager = (GridViewPager) findViewById(R.id.task_active_pager);
        final FragmentGridPagerAdapter adapter = new TaskActiveGridPagerAdapter(this, getFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new GridViewPager.OnPageChangeListener() {

            public void onPageSelected(int vertNumber, int horNumber) {
                if (horNumber == 1) {
                    t.finish();
                    t.setElapsed(t.getElapsed() + textTimer.toSeconds());

                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    intent.putExtra(Task.TASK_COMPLETE, t);

                    finish();
                }
            }

            public void onPageScrolled(int arg0, int b, float arg1, float c, int d, int arg2) {
                // TODO Auto-generated method stub

            }

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
