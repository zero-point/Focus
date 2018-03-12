package uk.ac.gla.focuswatch.util;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/** A convenient container for a row of fragments. */
public class Row {
    final List<Fragment> columns = new ArrayList<Fragment>();

    public Row(Fragment... fragments) {
        for (Fragment f : fragments) {
            add(f);
        }
    }

    public void add(Fragment f) {
        columns.add(f);
    }

    public Fragment getColumn(int i) {
        return columns.get(i);
    }

    public int getColumnCount() {
        return columns.size();
    }
}
