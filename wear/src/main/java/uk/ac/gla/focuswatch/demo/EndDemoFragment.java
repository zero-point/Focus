package uk.ac.gla.focuswatch.demo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.tasks.TaskListActivity;

public class EndDemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout fragmentLayout = (FrameLayout) inflater.inflate(R.layout.end_demo_fragment, container, false);
        final ImageButton button = (ImageButton) fragmentLayout.findViewById(R.id.got_it_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskListActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        return fragmentLayout;
    }
}