package com.fpt.view.test;

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
import android.widget.TextView;

import com.fpt.provider.WWDatabase;
import com.fpt.unittest.DatabaseTest;
import com.fpt.view.R;

public class UnitTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unittestactivity_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        // delete database
        WWDatabase.deleteDatabase(getApplicationContext());
        TextView textview = (TextView) findViewById(R.id.textView);

        String str = "";
        str += DatabaseTest.test_insertArticle(getApplicationContext()) + "\n";
        WWDatabase.deleteDatabase(getApplicationContext());
        str += DatabaseTest.test_getArticleById(getApplicationContext()) + "\n";
        WWDatabase.deleteDatabase(getApplicationContext());
        str += DatabaseTest.test_deleteArticleById(getApplicationContext()) + "\n";
        WWDatabase.deleteDatabase(getApplicationContext());
        str += DatabaseTest.test_getAllArticles(getApplicationContext()) + "\n";
        WWDatabase.deleteDatabase(getApplicationContext());
        textview.setText(str);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.unit_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.unittestactivity_fragment_main, container, false);
            return rootView;
        }
    }

}
