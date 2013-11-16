package com.fpt.view.fragment;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fpt.view.MainActivity;
import com.fpt.view.R;

import static com.fpt.util.LogUtils.makeLogTag;

public class AllVocabularyFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public static String TAG = makeLogTag(AllVocabularyFragment.class);


    /** Main Activity for reference */
    MainActivity activity;

    /** spinner of this fragment
     */
    Spinner spinner;

    /**
     * ListView contains all vocabularies
     */
    ListView mListView;


   public AllVocabularyFragment() {

   }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_vocabulary, container, false);

        // inflat layout
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> searchChoices = ArrayAdapter.
                createFromResource(getActivity().getApplicationContext(),
                        R.array.search_option, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        searchChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(searchChoices);    // Apply the adapter to the spinner
        spinner.setOnItemSelectedListener(this);   // because this fragment has implemented method

        /** ListView Configure */
        mListView = (ListView) rootView.findViewById(R.id.listview);
        adapter = new FindByChordAdapter(getActivity().getApplicationContext(), this, chords);
        mListView.setAdapter(adapter);



        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
