package com.fpt.view.com.fpt.view.fragment;

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
import android.widget.TextView;

import com.fpt.view.MainActivity;
import com.fpt.view.R;

import java.util.ArrayList;
import java.util.List;

import static com.fpt.util.LogUtils.makeLogTag;

public class WordViewFragment extends Fragment {


    public static String TAG = makeLogTag(WordViewFragment.class);

    /** Main Activity for reference */
    MainActivity activity;

    public WordViewFragment() {

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
        View rootView = inflater.inflate(R.layout.statitic_view_fragment_main, container, false);

        // Busniess code here
//        TextView textView = (TextView) rootView.findViewById(R.id.textView);
//        textView.setText("TEST");

        return rootView;
    }




}
