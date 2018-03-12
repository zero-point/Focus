package uk.ac.gla.focuswatch.tasks;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import uk.ac.gla.focuswatch.R;
import uk.ac.gla.focuswatch.model.Task;

public class TasksFragment extends Fragment {
    private TextView mTextView;
    private ArrayList<Task> tasks;
    private TaskAdapter adapter;
    private ListView lv;
    private Context TasksContext;

    private int activatedTaskPos = -1;
    private ImageButton btnSpeak;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View cView = inflater.inflate(R.layout.tasks_fragment, container, false);

        // Fill the ListView with some mock data
        tasks = new ArrayList();

        // Dummy Tasks that should usually be fetched from the SQLite DB
        tasks.add( new Task(tasks.size() + 1, "ML deadline") );
        tasks.add( new Task(tasks.size() + 1, "MHCI deadline") );
        tasks.add( new Task(tasks.size() + 1, "Individual Project") );
        tasks.add( new Task(tasks.size() + 1, "Make video") );
        tasks.add( new Task(tasks.size() + 1, "Train presentation") );

        adapter = new TaskAdapter(getActivity(), R.layout.tasks_item_row_view, tasks);

        lv = (ListView) cView.findViewById(R.id.tasksView);
        lv.setItemsCanFocus(true);
        lv.setAdapter(adapter);

        btnSpeak = (ImageButton)cView.findViewById(R.id.spkButton);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    getActivity().startActivityForResult(intent, TaskListActivity.RESULT_SPEECH);
                } catch (ActivityNotFoundException a) {
                    Toast notFound = Toast.makeText(getActivity(),
                            "Speech to text not supported!",
                            Toast.LENGTH_SHORT);
                    notFound.show();
                }
            }

        });

        return cView;
    }

    private void addToAdapter(Task t){
        tasks.add(t);
        adapter.notifyDataSetChanged();
    }

    private void removeFromAdapter(int pos){
        tasks.remove(pos);
        adapter.notifyDataSetChanged();
        activatedTaskPos = -1;
    }

    private int findTaskPos(String label){
        label = label.toLowerCase();
        int foundPos = -1;

        for(int i = 0; i < tasks.size(); i++){
            Task currTask = tasks.get(i);
            String taskLabel = currTask.getLabel().toLowerCase();

            if( taskLabel.contains(label) ){
                foundPos = i;
                break;
            }
        }

        return foundPos;
    }

    public void createTask(String[] words) {
        // convert rest of the message to the label of the task
        String tLabel = wordsToSentence(Arrays.copyOfRange(words, 2, words.length));
        Task newT = new Task(tasks.size() + 1, tLabel);
        addToAdapter(newT);
    }

    public void focusOnTask(String[] words) {
        String tLabel = words[2];
        int foundPos;
        Task t = null;

        foundPos = findTaskPos(tLabel);

        if( foundPos < 0 ){
            t = new Task(tasks.size() + 1, tLabel);
            activatedTaskPos = tasks.size() - 1;
            addToAdapter(t);
        }
        else {
            activatedTaskPos = foundPos;
            t = tasks.get(foundPos);
        }

        t.startTask(getActivity());
    }

    public void completeTask(String[] words) {
        String tLabel = words[1];
        int foundPos = -1;
        Task t = null;

        foundPos = findTaskPos(tLabel);

        if( foundPos < 0 ){
            Toast notFound = Toast.makeText(TasksContext,
                    "Task not found!",
                    Toast.LENGTH_SHORT);
            notFound.show();
        }else {
            t = tasks.get(foundPos);
            t.finish();
            removeFromAdapter(foundPos);
        }
    }

    public void completeActivateTask(Task t){
        if( t == null )
            return;

        t.finish();
        int pos = findTaskPos(t.getLabel());

        if( pos >= 0 )
            removeFromAdapter(pos);
    }

    private String wordsToSentence(String[] words) {
        /**
         * Converts array of words to space delimited sentence
         */
        String sentence = "";
        for (int i = 0; i < words.length; i++) {
            sentence += words;
            if (i != words.length - 1) {
                sentence += " ";
            }
        }
        return sentence;

    }
}