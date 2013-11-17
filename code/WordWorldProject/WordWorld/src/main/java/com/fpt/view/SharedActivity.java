package com.fpt.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpt.config.Config;
import com.fpt.model.Article;
import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;
import com.fpt.provider.WWDatabase;
import com.fpt.util.PopupUtils;
import com.fpt.util.StringBuilderHelper;
import com.fpt.view.fragment.SelectAllWordFragment;
import com.fpt.view.fragment.WebviewFragment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SharedActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    WebviewFragment fragment;

    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        // Delete database
//        WWDatabase.deleteDatabase(getApplicationContext());

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                                "Main Menu",
                        }),
                this);


        /** extra parameters send from browser */
        Intent intent = this.getIntent();
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (sharedText.startsWith("http")) {
            // If the shared text is a url
            fragment = new WebviewFragment();
            Bundle arguments = new Bundle();
            arguments.putString(Config.ARGUMENT_LINKWEBPAGE, sharedText);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();
        } else {
            // If the shared text is a word
            Word w = WordDAL.getWordByText(getApplicationContext(), sharedText);
            if (w == null) {
                PopupUtils.openAddWordPopup(this, sharedText);
            } else {
                PopupUtils.openRemoveWordPopup(this, sharedText);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shared, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        if (id == 0) {
            if (fragment != null) {
                fragment.callJsHighlightOn();
            }
        } else if (id == 1) {
            if (fragment != null) {
                fragment.callJsHighlightOff();
            }
        } else if (id == 2) {
            // get all strings from web page
            List<String> listWord = StringBuilderHelper.getListWord(fragment.article.content);
            CustomObject obj = new CustomObject(listWord);
            Fragment newFragment = new SelectAllWordFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(Config.ARGUMENT_LIST_WORD_STRING, obj);
            newFragment.setArguments(arguments);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
        } else if (id == 3) {
            Intent myIntent = new Intent(SharedActivity.this, MainActivity.class);
            SharedActivity.this.startActivity(myIntent);
        }
        return true;
    }

    public static class CustomObject implements Serializable {
        public List<String> words;

        public CustomObject(List<String> words) {
            this.words = words;
        }
    }

}
