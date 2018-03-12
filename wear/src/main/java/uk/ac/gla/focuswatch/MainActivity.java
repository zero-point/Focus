package uk.ac.gla.focuswatch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import uk.ac.gla.focuswatch.demo.DemoGridPagerAdapter;
import uk.ac.gla.focuswatch.tasks.TaskActiveActivity;
import uk.ac.gla.focuswatch.tasks.TaskListActivity;

public class MainActivity extends Activity {
    /**
     * The Main Activity's only job is to decide whether we should run the demo first
     * or continue directly to main application
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the saved setting to decide whether we should run the Demo
        SharedPreferences settings = getPreferences(0);
        settings.edit().putBoolean("runDemo", true).apply(); // TODO: Temp
        boolean runDemo = settings.getBoolean("runDemo", true);
        if (runDemo) {
            // Update the settings so that next time we don't run the demo
            settings.edit().putBoolean("runDemo", false).apply();
            // Create the fragments of the demo
            setContentView(R.layout.activity_demo);
            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new DemoGridPagerAdapter(this, getFragmentManager()));
        } else {
            // Destroy this activity and start the tasks activity
            this.finish();
            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
        }
    }
}
