package com.fpt.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.fpt.helper.adapter.AllVocabularyAdapter;
import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;

import java.util.Date;
import java.util.List;

import static com.fpt.util.LogUtils.LOGE;
import static com.fpt.util.LogUtils.makeLogTag;

public class SearchActivity extends Activity {

    public static String TAG = makeLogTag(SearchActivity.class);

    /** List contains search data */
    ListView mListView;

    /** Adapter for ListView
     * use old ones become it's same
     */
    AllVocabularyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_activity);

        mListView = (ListView) findViewById(R.id.list_view);

        handleIntent(getIntent());
    }




    /**
     * If the currently Activity is searchable Activity, then one of two things will be happens :
     *
     * 1) <b> By default: </b>
     * the searchable activity receives the ACTION_SEARCH intent with a call to onCreate()
     * and a new instance of the activity is brought to the top of the activity stack.
     * There are now two instances of your searchable activity in the activity stack
     * (so pressing the Back button goes back to the previous instance of the searchable activity,
     * rather than exiting the searchable activity).
     *
     * 2) <b> If you set android:launchMode to "singleTop" (Set in Android Manifest): </b>
     * then the searchable activity receives the ACTION_SEARCH intent with a call to onNewIntent(Intent),
     * passing the new ACTION_SEARCH intent here.
     * <hqt>
     * This action usually ideal.
     * Because chances are goods that once a search is done, the user will performs additional searches
     * bad practice if app creates multiple instances of the searchable activity !!!
     * </hqt>
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String queryStr) {
        // get a curosr. prepare a List Adapter
        // and set it
        LOGE(TAG, "Search query " + queryStr);

        // temporary search to words
        final List<Word> words = WordDAL.getListWordBySearchText(getApplicationContext(),     queryStr);

        // assign to view
        adapter = new AllVocabularyAdapter(getApplicationContext(),words);
        mListView.setAdapter(adapter);

        /** when user click. open edit/remove dialog */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openRemoveWordPopup(words.get(i));
            }
        });
    }

    /**
     *  unique_seq: a unique sequence number to identify which word was touched...
     * @param word
     */
    public void openRemoveWordPopup(final Word word) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_remove_word);
        // Set dialog title
        dialog.setTitle("Edit word");

        /** inflat widget here */
        final Button editBtn = (Button) dialog.findViewById(R.id.btnEdit);
        final Button cancelBtn = (Button) dialog.findViewById(R.id.btnCalcel);
        final EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);
        final EditText txtDescription = (EditText) dialog.findViewById(R.id.txtDescription);
        final CheckBox chkDelete = (CheckBox) dialog.findViewById(R.id.chkDelete);
        final LinearLayout wordLayout = (LinearLayout) dialog.findViewById(R.id.linear_layout_word);
        final LinearLayout descriptionLayout = (LinearLayout) dialog.findViewById(R.id.linear_layout_description);


        // assign text into TextView
        txtWord.setText(word.the_word);
        txtDescription.setText(word.description);

        // Checkbox action
        chkDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("CheckboxDebug", String.valueOf(chkDelete.isChecked()));
                if (chkDelete.isChecked()) {
                    wordLayout.setVisibility(View.INVISIBLE);
                    descriptionLayout.setVisibility(View.INVISIBLE);
                } else {
                    wordLayout.setVisibility(View.VISIBLE);
                    descriptionLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // save action
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkDelete.isChecked()) {
                    // Delete
                    WordDAL.deleteWordById(getApplicationContext(), word.id);
                } else {
                    // Update the description
                    WordDAL.updateWordDescription(getApplicationContext(), word.id, txtDescription.getText().toString());
                }
                dialog.dismiss();
            }
        });

        // cancel
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
