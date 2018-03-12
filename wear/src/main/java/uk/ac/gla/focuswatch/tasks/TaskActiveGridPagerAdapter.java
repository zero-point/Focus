package uk.ac.gla.focuswatch.tasks;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.ac.gla.focuswatch.util.Row;

public class TaskActiveGridPagerAdapter extends FragmentGridPagerAdapter {

    private List<Row> mRows;


    public TaskActiveGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);

        mRows = new ArrayList<>();

        mRows.add(new Row(new TaskActiveFragment(),
                  new TaskCompleteFragment())
        );
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