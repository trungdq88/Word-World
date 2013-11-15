package com.fpt.view;

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
import com.fpt.model.Word;
import com.fpt.model.dal.ArticleDAL;
import com.fpt.model.dal.WordDAL;
import com.fpt.provider.WWDBContract;
import com.fpt.provider.WWDatabase;
import com.fpt.util.DisplayUtils;

import java.util.Date;
import java.util.List;

public class DatabaseTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // delete database
        //WWDatabase.deleteDatabase(getApplicationContext());

        TextView textView = (TextView) findViewById(R.id.textView);
        String str = "";

//        Article a = new Article(2, "http://google.com", "hello mot hai ba", (new Date()).getTime());
//        ArticleDAL.insertArticle(getApplicationContext(), a);
//        List<Article> result = ArticleDAL.getAllArticles(getApplicationContext());
//        str += "All articles: " + DisplayUtils.arrayToString(result);
//
//        ArticleDAL.deleteArticleById(getApplicationContext(), 2);
//        Article resultArticle = ArticleDAL.getArticleById(getApplicationContext(), 3);
//        str += "Article: " + resultArticle;

//        List<Word> words = WordDAL.getAllWords(getApplicationContext());
//        str += "words: " + DisplayUtils.arrayToString(words);
//
//        Word w = new Word(1, "the", "the the", 1, (new Date()).getTime());
//        WordDAL.insertWord(getApplicationContext(), w);
//
//        List<Word> result = WordDAL.getAllWords(getApplicationContext());
//        str += ">> words: " + DisplayUtils.arrayToString(result);

        Word w = WordDAL.getWordById(getApplicationContext(), 1);
        str += " ++ words: " + w;
        WordDAL.deleteWordById(getApplicationContext(), 1);
        Word w2 = WordDAL.getWordById(getApplicationContext(), 1);
        str += " -- words: " + w2;
        textView.setText(str);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.database_test, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_database_test, container, false);
            return rootView;
        }
    }

}
