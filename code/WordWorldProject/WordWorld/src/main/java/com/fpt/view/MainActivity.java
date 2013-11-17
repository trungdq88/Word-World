package com.fpt.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fpt.helper.adapter.MergeAdapter;
import com.fpt.helper.adapter.NavigationDrawerAdapter;
import com.fpt.provider.WWDatabase;
import com.fpt.unittest.DatabaseTest;
import com.fpt.util.UIUtils;
import com.fpt.view.com.fpt.view.fragment.StatiticViewFragment;
import com.fpt.view.fragment.AllArticleFragment;
import com.fpt.view.fragment.AllVocabularyFragment;
import com.fpt.view.fragment.HeadVocabularyFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerAdapter.IItemDelegate {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /** SearchView for our app */
    SearchView mSearchView;

    /** DrawerLayout for MainActivity */
    DrawerLayout mDrawerLayout;

    /** ListView contains all items */
    ListView mDrawerListView;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * All Adapter for Navigation Drawer
     */
    MergeAdapter mergeAdapter;
    NavigationDrawerAdapter.HeaderAdapter headerAdapter;
    NavigationDrawerAdapter.ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerListView = (ListView) findViewById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Delete database;
//         WWDatabase.deleteDatabase(getApplicationContext());

        // Set up the drawer.
        setUpActionBar();

        // Set up the list view
        setUpListView();

        // Set up default is Welcome Screen
        Fragment fragment = new StatiticViewFragment();
        Bundle arguments = new Bundle();
        fragment.setArguments(arguments);
        switchFragment(fragment);

    }

    /**
     * managed code : onResume and onPause
     * 1. add and remove delegate in those method
     */
    @Override
    public void onResume() {
        super.onResume();
        itemAdapter.setDelegate(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        itemAdapter.setDelegate(null);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if Drawer is open. Call Default Method
        // Let the Drawer decide what to show in the action bar
        if (isDrawerOpen()) {
            return super.onCreateOptionsMenu(menu);
        }

        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // because SearchView just exist in HoneyComb 3.0 to above
        // we should check version of users here
        // if version is lower. We use SearchDialog instead
        // TODO: search google. Find SearchWidget library for API Lower than 11
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        if (searchItem != null && UIUtils.hasHoneycomb()) {
            // Get the SearchView and set the Search Configuration
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            // Assumes current activity is the searchable activity
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            // Do not icon the widget. expand it.
            mSearchView.setIconifiedByDefault(false);
            // enable submit button
            mSearchView.setSubmitButtonEnabled(true);
        }

        restoreActionBar();
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * when search dialog is active. activity will lost input focus
     * so. do some stuffs (such as saving state) before search
     * Unless passing search context data.
     * should end method by calling super class implementation
     */
    @Override
    public boolean onSearchRequested() {
        // doing some stuff before here
        return super.onSearchRequested();
    }

    /**
     * set up navigation drawer
     */
    public void setUpActionBar() {

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };


        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * set up adapter for list view
     * @return
     */
    public void setUpListView() {
        mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                }));


        /**
         * Using MergeAdapter for complex view
         * just create as many type of view in list as we want and Add to this Adapter
         */
        mergeAdapter = new MergeAdapter();

        /** setup adapter
         * create all adapters as we want
         */
        // create all adapters as we want
        headerAdapter = new NavigationDrawerAdapter.HeaderAdapter(getApplicationContext());
        itemAdapter = new NavigationDrawerAdapter.ItemAdapter(getApplicationContext());

        /** assign each adapter to this composite adapter */
        mergeAdapter.addAdapter(headerAdapter);
        mergeAdapter.addAdapter(itemAdapter);

        /** assign this complex adapter to navigation drawer list*/
        mDrawerListView.setAdapter(mergeAdapter);

    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawerListView);
    }


    @Override
    public void gotoCategoryPage(NavigationDrawerAdapter.ItemAdapter.TYPE type) {
        Fragment fragment = null;
        Bundle arguments = new Bundle();
        switch (type) {
            case WELCOME:
                fragment = new StatiticViewFragment();
                break;
            case HEAD_VOCABULARY:
                fragment = new HeadVocabularyFragment();
                break;
            case ALL_VOCABULARY:
                fragment = new AllVocabularyFragment();
                break;
            case SAVE_ARTICLE:
                fragment = new AllArticleFragment();
                break;
        }

        // close Navigation Drawer
        if (mDrawerListView != null) {
            // mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mDrawerListView);
        }

        // ... And open new Fragment
        if (fragment != null) fragment.setArguments(arguments);
        switchFragment(fragment);
    }

    /** others will call this method. MainActivity will replace old with new one fragment */
    public void switchFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
}
