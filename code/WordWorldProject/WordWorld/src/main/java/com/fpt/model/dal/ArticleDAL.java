package com.fpt.model.dal;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.fpt.model.Article;
import com.fpt.provider.WWDBContract;
import com.fpt.provider.WWDBContract.*;
import com.fpt.provider.WWDatabase;
import com.fpt.provider.helper.Query;

import java.util.ArrayList;
import java.util.List;

import static com.fpt.util.LogUtils.LOGD;
import static com.fpt.util.LogUtils.makeLogTag;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class ArticleDAL {
    private static final String TAG = makeLogTag(ArticleDAL.class);


    /**
     * Tested
     */
    public static boolean insertArticle(Context context, Article article) {
        LOGD(TAG, "Adding an article");

        ContentValues cv = new ContentValues();
        cv.put(WWDBContract.Article.ARTICLE_ID, article.article_id);
        cv.put(WWDBContract.Article.CONTENT, article.content);
        cv.put(WWDBContract.Article.URL, article.url);
        cv.put(WWDBContract.Article.CREATED, article.created);

        WWDatabase db = new WWDatabase(context);
        return db.getWritableDatabase().insert(Tables.ARTICLE, "", cv) > 0;
    }


    /**
     * Tested
     */
    public static List<Article> getAllArticles(Context context) {
        WWDatabase ww = new WWDatabase(context);
        Cursor c = ww.getWritableDatabase().query(Tables.ARTICLE,
                Query.Projections.ARTICLE_PROJECTION,
                null, null, null, null, null);
        List<Article> articles = new ArrayList<Article>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Article._ID));
            int article_id = c.getInt(c.getColumnIndex(WWDBContract.Article.ARTICLE_ID));
            String article_url = c.getString(c.getColumnIndex(WWDBContract.Article.URL));
            String article_content = c.getString(c.getColumnIndex(WWDBContract.Article.CONTENT));
            long created = c.getLong(c.getColumnIndex(WWDBContract.Article.CREATED));
            articles.add(new Article(_id, article_id, article_url, article_content, created));
        }
        if (c != null) {
            c.close();
        }
        return articles;
    }


    /**
     * Tested
     */
    public static Article getArticleById(Context context, int id) {
        WWDatabase db = new WWDatabase(context);
        Cursor c = db.getWritableDatabase().query(Tables.ARTICLE,
                Query.Projections.ARTICLE_PROJECTION,
                WWDBContract.Article.ARTICLE_ID + "=?", // Selection strings
                new String[]{String.valueOf(id)}, // Select args
                null, // groyp by
                null, // having
                null); // orderby

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Article._ID));
            int article_id = c.getInt(c.getColumnIndex(WWDBContract.Article.ARTICLE_ID));
            String article_url = c.getString(c.getColumnIndex(WWDBContract.Article.URL));
            String article_content = c.getString(c.getColumnIndex(WWDBContract.Article.CONTENT));
            long created = c.getLong(c.getColumnIndex(WWDBContract.Article.CREATED));
            return (new Article(_id, article_id, article_url, article_content, created));
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    /**
     * Tested
     */
    public static boolean deleteArticleById(Context context, int id) {
        WWDatabase db = new WWDatabase(context);
        int result = db.getWritableDatabase().delete(Tables.ARTICLE,
                WWDBContract.Article.ARTICLE_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }
}
