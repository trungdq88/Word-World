package com.fpt.view.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.fpt.config.Config;
import com.fpt.helper.adapter.AllVocabularyAdapter;
import com.fpt.helper.adapter.SelectMultiWordAdapter;
import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;
import com.fpt.util.SortUtils;
import com.fpt.view.MainActivity;
import com.fpt.view.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.fpt.util.LogUtils.makeLogTag;

public class SelectAllWordFragment extends Fragment {

   private static String TAG = makeLogTag(SelectAllWordFragment.class);


    /** Main Activity for reference */
    Activity activity;

    /** spinner of this fragment
     */
    Spinner spinner;

    /**
     * ListView contains all words should to be added
     */
    ListView mListView;

    /** adapter for word listview */
    SelectMultiWordAdapter adapter;

   /** List all String should to be added to database */
   List<String> words;

    /** Checkbox use for check all */
    CheckBox checkallCheckbox;

    public SelectAllWordFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if (getArguments().getSerializable(Config.ARGUMENT_LIST_WORD_STRING) != null) {
            words = (List<String>) getArguments().getSerializable(Config.ARGUMENT_LIST_WORD_STRING);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_all_word_with_check_all, container, false);

        // inflat layout

        // ListView Configure
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        // we can use same adapter of others. because all is same !!!!
        adapter = new SelectMultiWordAdapter(getActivity().getApplicationContext(), words);
        mListView.setAdapter(adapter);

        // button
        Button btnAdd = (Button) rootView.findViewById(R.id.addBtn);
        Button btnDone = (Button) rootView.findViewById(R.id.doneBtn);

        // check box
        checkallCheckbox = (CheckBox) rootView.findViewById(R.id.check_all);

        // add action
        checkallCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean[] checked = new boolean[words.size()];
                Arrays.fill(checked, b);
                adapter.checked = checked;
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. add all words to database
                boolean[] checked = adapter.checked;
                for (int i = 0; i < checked.length; i++) {
                    if (checked[i]) {
                        Word word = WordDAL.getWordByText(getActivity().getApplicationContext(), words.get(i));
                        if (word == null) {
                            WordDAL.insertWord(getActivity().getApplicationContext(), new Word(words.get(i),"", 1, 1, new Date().getTime()));
                        }
                        else {
                            WordDAL.updateWordStatus(getActivity().getApplicationContext(), word.id, 1);
                        }
                    }
                }

                // 2. update again model
                List<String> newWordList  = new ArrayList<String>();
                for (int i = 0; i < checked.length; i++) {
                    if (checked[i]) newWordList.add(words.get(i));
                }
                // update check array
                checked = new boolean[newWordList.size()];
                Arrays.fill(checked, false);

                // update model
                words = newWordList;
                adapter.words = newWordList;
                adapter.checked = checked;

                adapter.notifyDataSetChanged();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // back to previous activity
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return rootView;
    }

}
