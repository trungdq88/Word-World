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
import android.webkit.WebView;
import android.widget.TextView;

import com.fpt.helper.NetworkBackground;
import com.fpt.model.Article;
import com.fpt.model.ContentGetter;
import com.fpt.model.Word;
import com.fpt.model.dal.ArticleDAL;
import com.fpt.model.dal.WordDAL;
import com.fpt.provider.WWDBContract;
import com.fpt.provider.WWDatabase;
import com.fpt.util.DisplayUtils;
import com.fpt.util.ParserUtils;
import com.fpt.util.StringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseTestActivity extends ActionBarActivity implements NetworkBackground.INetworkCallback{
    public TextView textView;
    public WebView webView;
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
        WWDatabase.deleteDatabase(getApplicationContext());

        textView = (TextView) findViewById(R.id.textView);
        webView = (WebView) findViewById(R.id.webViewASD);
        String str = "";

//        Article a = new Article("http://google.com", "title1", "hello mot hai ba", (new Date()).getTime());
//        Article a2 = new Article("http://google2.com", "title2", "hello 2 mot hai ba", (new Date()).getTime());
//        Article a3 = new Article("http://google3.com", "title2", "hello 3 mot hai ba", (new Date()).getTime());
//        ArticleDAL.insertArticle(getApplicationContext(), a);
//        ArticleDAL.insertArticle(getApplicationContext(), a2);
//        ArticleDAL.insertArticle(getApplicationContext(), a3);
//
//        ArticleDAL.deleteArticleById(getApplicationContext(), 2);
//        Article resultArticle = ArticleDAL.getArticleById(getApplicationContext(), 3);
//        str += "Article: " + resultArticle;
//
//        List<Article> result1 = ArticleDAL.getAllArticles(getApplicationContext());
//        str += "All articles: " + DisplayUtils.arrayToString(result1);


        Word w1 = new Word("the", "the the", 1, 0, (new Date()).getTime());
        Word w2 = new Word("the2", "the the2", 0, 0, (new Date()).getTime());
        Word w3 = new Word("the3", "the the3", 1, 0, (new Date()).getTime());
        Word w4 = new Word("the4", "the the4", 1, 0, (new Date()).getTime());
        WordDAL.insertWord(getApplicationContext(), w1);
        WordDAL.insertWord(getApplicationContext(), w2);
        WordDAL.insertWord(getApplicationContext(), w3);
        WordDAL.insertWord(getApplicationContext(), w4);
//
//        List<Word> result = WordDAL.getAllWords(getApplicationContext(), 1, 2);
//
//        str += "wordss: " + DisplayUtils.arrayToString(result);
//
//
//        Word w = WordDAL.getWordById(getApplicationContext(), 2);
//        str += " word by id: " + w;
//
//        List<Word> result2 = WordDAL.getAllWordsWithStatus(getApplicationContext(), 1);
//        str += "status1: " + DisplayUtils.arrayToString(result2);
//
//        List<Word> result3 = WordDAL.getAllWordsWithStatus(getApplicationContext(), 1, 1, 2);
//        str += "result3: " + DisplayUtils.arrayToString(result3);
//
//        Word wResult = WordDAL.getWordByText(getApplicationContext(), "the2");
//        str += " word by text: " + wResult + "\n";
//
//        WordDAL.updateSeenCount(getApplicationContext(), wResult.id);
//        WordDAL.updateSeenCount(getApplicationContext(), wResult.id);
//        WordDAL.updateSeenCount(getApplicationContext(), wResult.id);
//
//        wResult = WordDAL.getWordByText(getApplicationContext(), "the2");
//        str += " word by text after update status: " + wResult;

        (new ContentGetter(this)).execute("http://www.howtogeek.com/175641/how-to-boot-and-install-linux-on-a-uefi-pc-with-secure-boot");

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

    @Override
    public void process(Article article) {
        List<String> words = new ArrayList<String>();
        words.add("embedded");
        words.add("boot");
        words.add("hiding");
        words.add("operating");
        words.add("add");
        String html = StringHelper.colorWord(article.content, words, "<font color='red'>", "</font>");

        webView.loadDataWithBaseURL("", html, "text/html","UTF-8","");
        textView.setText("Result: " + article.toString());
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
