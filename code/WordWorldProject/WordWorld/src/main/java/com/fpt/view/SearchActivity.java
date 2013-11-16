package com.fpt.view;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;

import java.util.List;

import static com.fpt.util.LogUtils.LOGE;
import static com.fpt.util.LogUtils.makeLogTag;

public class SearchActivity extends ListActivity {

    public static String TAG = makeLogTag(SearchActivity.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

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
        Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_LONG);

        // search here
        List<Word> words = WordDAL.getAllWords(getApplicationContext());

        // assign to list view



    }


}
