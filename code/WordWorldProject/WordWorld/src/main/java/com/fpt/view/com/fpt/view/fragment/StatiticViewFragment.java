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
import android.widget.TextView;

import com.fpt.model.Article;
import com.fpt.model.dal.ArticleDAL;
import com.fpt.model.dal.WordDAL;
import com.fpt.view.MainActivity;
import com.fpt.view.R;

import static com.fpt.util.LogUtils.makeLogTag;

public class StatiticViewFragment extends Fragment {


    public static String TAG = makeLogTag(StatiticViewFragment.class);

    /** Main Activity for reference */
    MainActivity activity;

    public StatiticViewFragment() {

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
        View rootView = inflater.inflate(R.layout.statitic_view_fragment_activity, container, false);

        // Busniess code here
//        TextView textView = (TextView) rootView.findViewById(R.id.textView);
//        textView.setText("TEST");

        // Update general statitics:
        TextView txtTotalWord = (TextView) rootView.findViewById(R.id.txtTotalWord);
        txtTotalWord.setText(getString(R.string.string_total_word) + " " + WordDAL.getAllWordsWithStatus(activity.getApplicationContext(), 1).size());

        TextView txtTotalArticle = (TextView) rootView.findViewById(R.id.txtTotalArticle);
        txtTotalArticle.setText(getString(R.string.total_article_string) + " " + ArticleDAL.getAllArticles(activity.getApplicationContext()).size());


        return rootView;
    }


}
