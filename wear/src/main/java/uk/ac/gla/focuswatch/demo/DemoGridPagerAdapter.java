package uk.ac.gla.focuswatch.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.util.Row;

/**
 * Constructs fragments as requested by the GridViewPager. For each row a different background is
 * provided.
 * <p>
 * Always avoid loading resources from the main thread. In this sample, the background images are
 * loaded from an background task and then updated using {@link #notifyRowBackgroundChanged(int)}
 * and {@link #notifyPageBackgroundChanged(int, int)}.
 */
public class DemoGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final int TRANSITION_DURATION_MILLIS = 100;

    private final Context mContext;
    private List<Row> mRows;
    private ColorDrawable mDefaultBg;

    private ColorDrawable mClearBg;

    public DemoGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;

        mRows = new ArrayList<Row>();

        mRows.add(new Row(new LogoFragment()));
        mRows.add(new Row(new Demo1Fragment()));
        mRows.add(new Row(new Demo2Fragment()));
        mRows.add(new Row(new Demo3Fragment()));
        mRows.add(new Row(new Demo4Fragment()));
        mRows.add(new Row(new Demo5Fragment()));
        mRows.add(new Row(new EndDemoFragment()));


        mDefaultBg = new ColorDrawable(R.color.dark_grey);
        mClearBg = new ColorDrawable(android.R.color.transparent);
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }
}