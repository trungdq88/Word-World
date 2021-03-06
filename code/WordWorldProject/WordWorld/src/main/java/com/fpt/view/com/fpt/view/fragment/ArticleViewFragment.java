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

import com.fpt.view.MainActivity;
import com.fpt.view.R;

import static com.fpt.util.LogUtils.makeLogTag;

public class ArticleViewFragment extends Fragment {

    public static String TAG = makeLogTag(ArticleViewFragment.class);

    /** Main Activity for reference */
    MainActivity activity;

    public ArticleViewFragment() {

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
        View rootView = inflater.inflate(R.layout.fragment_article_view, container, false);

        // Busniess code here
//        TextView textView = (TextView) rootView.findViewById(R.id.textView);
//        textView.setText("TEST");

        return rootView;
    }


}
