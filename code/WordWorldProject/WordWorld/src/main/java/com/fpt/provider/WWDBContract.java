package com.fpt.provider;

import android.provider.BaseColumns;

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

    /**
     * Article Class
     */
    public static final class Article implements ArticleColumns, BaseColumns {

    }

    /**
     * Word Class
     */
    public static final class Word implements  WordColumns, BaseColumns {

    }

    // prevent create objet
    private WWDBContract(){}
}
