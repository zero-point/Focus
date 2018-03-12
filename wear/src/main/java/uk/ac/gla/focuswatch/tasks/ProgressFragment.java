package uk.ac.gla.focuswatch.tasks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.gla.focuswatch.R;

public class ProgressFragment extends Fragment {
    // TODO: progress update "N/M tasks completed in past 7 days"
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_fragment, container, false);

    }
}