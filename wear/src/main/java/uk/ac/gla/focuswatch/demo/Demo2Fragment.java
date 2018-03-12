package uk.ac.gla.focuswatch.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.gla.focuswatch.R;

public class Demo2Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo2_fragment, container, false);

    }
}