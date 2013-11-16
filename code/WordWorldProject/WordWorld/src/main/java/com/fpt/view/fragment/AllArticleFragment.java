package com.fpt.view.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fpt.helper.adapter.AllArticleAdapter;
import com.fpt.model.Article;
import com.fpt.model.dal.ArticleDAL;
import com.fpt.util.SortUtils;
import com.fpt.view.MainActivity;
import com.fpt.view.R;

import java.util.List;

import static com.fpt.util.LogUtils.makeLogTag;

public class AllArticleFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static String TAG = makeLogTag(AllArticleFragment.class);

    /** Main Activity for reference */
    MainActivity activity;

    /** spinner of this fragment
     */
    Spinner spinner;

    /**
     * ListView contains all vocabularies
     */
    ListView mListView;

    /** adapter for word listview */
    AllArticleAdapter adapter;

    /** list all articles currently in database */
    List<Article> articles;

    public AllArticleFragment() {
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
                        R.array.search_article_option, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        searchChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(searchChoices);    // Apply the adapter to the spinner
        spinner.setOnItemSelectedListener(this);   // because this fragment has implemented method

        // ListView Configure
        mListView = (ListView) rootView.findViewById(R.id.listview);
        articles = ArticleDAL.getAllArticles(getActivity().getApplicationContext());
        adapter = new AllArticleAdapter(getActivity().getApplicationContext(), articles);
        mListView.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(i) {
            case 0 :
                SortUtils.sortArticleByABC(articles);
                break;
            case 1:
                SortUtils.sortArticleByRecentTime(articles);
                break;
        }
        // notify to adapter
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
