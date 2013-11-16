package com.fpt.provider.helper;

import android.provider.BaseColumns;

import com.fpt.provider.WWDBContract.*;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class Query {

    /**
     * this constant string is used for building URI
     * constants that append to Database URI
     * for example : content://com.fpt.provider/article
     */
    public static interface URI {
        final String PATH_ARTICLE = "article";
        final String PATH_WORD = "word";
    }

    /**
     * Use this interface to get Projection columns
     * when to use query method to query a table
     * <p/>
     * Trick : When programmer wants to debug sql query
     * can add a dummy column.
     * Locat will throws exception, and print out that query.
     * we can copy that query and query directly into console to show result
     */
    public interface Projections {
        String[] ARTICLE_PROJECTION = {
                BaseColumns._ID,
                Article.TITLE,
                Article.URL,
                Article.CONTENT,
                Article.CREATED
                // "Dummy colum"     :
                // Add this temporary column for debug purpose
                // logcat will print out query and we can easily trace it.
        };
        String[] WORD_PROJECTION = {
                BaseColumns._ID,
                Word.THE_WORD,
                Word.DESCRIPTION,
                Word.STATUS,
                Word.COUNT,
                Word.CREATED
        };
    }
}
