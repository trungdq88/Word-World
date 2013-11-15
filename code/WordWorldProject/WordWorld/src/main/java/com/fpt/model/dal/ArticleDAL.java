package com.fpt.model.dal;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.fpt.model.Article;
import com.fpt.provider.WWDBContract;

import static com.fpt.util.LogUtils.LOGD;
import static com.fpt.util.LogUtils.makeLogTag;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class ArticleDAL {
    private static final String TAG = makeLogTag(ArticleDAL.class);

    public static String insertArtist(Context context, Article article) {
        LOGD(TAG, "Adding an article");

        ContentValues cv = new ContentValues();
        cv.put(WWDBContract.Article.ARTICLE_ID, article.article_id);
        cv.put(WWDBContract.Article.CONTENT, article.content);
        cv.put(WWDBContract.Article.URL, article.url);
        cv.put(WWDBContract.Article.CREATED, article.created);


        ContentResolver resolver = context.getContentResolver();
        Uri uri = WWDBContract.Article.CONTENT_URI;
        Uri insertedUri = resolver.insert(uri, cv);
        LOGD(TAG, "inserted uri: " + insertedUri);
        return insertedUri.toString();
    }

}
