package com.fpt.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.fpt.provider.helper.Query.URI.*;
/**
 * Created by Quang Trung on 11/15/13.
 */
public class WWDBContract {
    /**
     * All tables
     */
    public static interface Tables {
        String ARTICLE = "feq_Article";
        String WORD = "feq_Word";
    }

    interface ArticleColumns {
        String ARTICLE_ID = "article_id";
        String URL = "article_url";
        String TITLE = "article_title";
        String CONTENT = "article_content";
        String CREATED = "article_created";
    }

    interface WordColumns {
        String WORD_ID = "word_id";
        String THE_WORD = "word_word";
        String DESCRIPTION = "word_description";
        String STATUS = "word_status"; // 0: not in my dict, 1: in my dict.
        String CREATED = "word_created";
    }



    public static final String CONTENT_AUTHORITY = "com.fpt.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Article Class
     */
    public static final class Article implements ArticleColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();

    }

    /**
     * Word Class
     */
    public static final class Word implements  WordColumns, BaseColumns {

    }

    // prevent create objet
    private WWDBContract(){}
}
