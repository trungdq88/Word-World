package com.fpt.unittest;

import android.content.Context;

import com.fpt.model.Article;
import com.fpt.model.Word;
import com.fpt.model.dal.ArticleDAL;
import com.fpt.model.dal.WordDAL;

import java.util.List;

/**
 * Created by Dell on 11/16/13.
 */
public class DatabaseTest {
    public static String test_insertArticle(Context context){
        String str = "test_insertArticle: ";

        // Tao article mau
        Article article = new Article(1, "http://www.google.com.vn", "google", "123", 11);

        // Insert article
        boolean result = ArticleDAL.insertArticle(context ,article);

        if (result){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL";
        }

        //delete
        ArticleDAL.deleteArticleById(context, 1);
        return str;
    }
    public static String test_getArticleById(Context context){
        String str = "test_getArticleById: ";

        // Tao article mau
        Article article = new Article(1, "http://www.google.com.vn", "google", "123", 11);

        // Insert article
        ArticleDAL.insertArticle(context ,article);

        // Lay article
        Article article1 = ArticleDAL.getArticleById(context, 1);

        // So sanh article moi lay va article ban dau.
        boolean result = article1.equals(article);

        if (result) {
            str += "SUCCESSFUL";
        } else {
            str += "FAIL: result: " + article1 + "| Expected: " + article;
        }
        return str;
    }

    public static String test_deleteArticleById(Context context){
        String str = "test_deleteArticleById: ";

        //Insert article
        Article article = new Article(1, "http://www.google.com.vn", "google", "123", 11);
        ArticleDAL.insertArticle(context ,article);

        //Delete Article
        boolean result = ArticleDAL.deleteArticleById(context, 1);

        if(result) {
            str += "SUCCESSFUL";
        }else {
            str += "FAIL: ";
        }

        return str;
    }

    public static String test_getAllArticles(Context context){
        String str = "test_getAllArticles: ";

        //Insert articles
        Article article1 = new Article(1, "http://www.google.com.vn", "google", "123", 11);
        Article article2 = new Article(2, "avds", "google", "123", 11);
        Article article3 = new Article(3, "dsdsds", "google", "123", 11);
        Article article4 = new Article(4, "dsvdsv", "google", "123", 11);
        ArticleDAL.insertArticle(context ,article1);
        ArticleDAL.insertArticle(context ,article2);
        ArticleDAL.insertArticle(context ,article3);
        ArticleDAL.insertArticle(context ,article4);

        //Lay articles
        List<Article> articleList = ArticleDAL.getAllArticles(context);

        //So sanh cac phan tu trong list
        if(articleList.get(0).equals(article1) && articleList.get(1).equals(article2) && articleList.get(2).equals(article3) && articleList.get(3).equals(article4)){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL: result: " + articleList.get(0).toString() + " | Expected: " + article1;
        }

        return str;
    }

    public static String test_insertWord(Context context){
        String str = "test_insertWord: ";

        //Tao word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);

        //Insert word
        boolean result = WordDAL.insertWord(context, word);

        if(result){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL";
        }

        //Delete word
        WordDAL.deleteWordById(context, 1);

        return str;
    }

    public static String test_updateSeenCount(Context context){
        String str = "test_updateSeenCount: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //Update count
        boolean result = WordDAL.updateSeenCount(context, 1);

        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }

        //Delete word
        WordDAL.deleteWordById(context, 1);

        return  str;
    }

    public static String test_updateWordStatus(Context context){
        String str = "test_updateWordStatus: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //Update count
        boolean result = WordDAL.updateWordStatus(context, 1, 5);

        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }

        //Delete word
        WordDAL.deleteWordById(context, 1);

        return  str;
    }

    public static String test_updateWordDescription(Context context){
        String str = "test_updateWordDescription: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //Update count
        boolean result = WordDAL.updateWordDescription(context, 1, "Goodbye");

        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }

        //Delete word
        WordDAL.deleteWordById(context, 1);

        return  str;
    }

    public static String test_deleteWordById(Context context){
        String str = "test_deleteWordById: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //Update count
        boolean result = WordDAL.deleteWordById(context, 1);

        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }
        return  str;
    }

    public static String test_getWordByText(Context context){
        String str = "test_getWordByText: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //get word
        Word word1 = WordDAL.getWordByText(context, "Hello");

        //So sanh 2 word
        boolean result = word1.equals(word);
        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }
        return str;
    }

    public static String test_getWordById(Context context){
        String str = "test_getWordById: ";

        //Insert word
        Word word = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        WordDAL.insertWord(context, word);

        //get word
        Word word1 = WordDAL.getWordById(context, 1);

        //So sanh 2 word
        boolean result = word1.equals(word);
        if(result){
            str += "SUCCESSFUL";
        }else{
            str += "FAIL";
        }
        return str;
    }

    public static String test_getAllWords(Context context){
        String str = "test_getAllWords: ";

        //Insert word
        Word word1 = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        Word word2 = new Word(2, "Hi", "Xin chao", 2, 2, 3);
        Word word3 = new Word(3, "Good", "tot", 3, 2, 3);
        Word word4 = new Word(4, "Morning", "ko bik nghia", 4, 2, 3);
        Word word5 = new Word(5, "Trung", "Trung", 5, 2, 3);
        WordDAL.insertWord(context, word1);
        WordDAL.insertWord(context, word2);
        WordDAL.insertWord(context, word3);
        WordDAL.insertWord(context, word4);
        WordDAL.insertWord(context, word5);

        //get all articles
        List<Word> wordList = WordDAL.getAllWords(context);

        //so sanh
        if(wordList.get(0).equals(word1) && wordList.get(1).equals(word2) && wordList.get(4).equals(word5)){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL";
        }
        return str;
    }

    public static String test_getAllWordsWithStatus(Context context){
        String str = "test_getAllWordsWithStatus: ";


        //Insert articles
        Word word1 = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        Word word2 = new Word(2, "Hi", "Xin chao", 2, 2, 3);
        Word word3 = new Word(3, "Good", "tot", 3, 2, 3);
        Word word4 = new Word(4, "Morning", "ko bik nghia", 5, 2, 3);
        Word word5 = new Word(5, "Trung", "Trung", 5, 2, 3);
        WordDAL.insertWord(context, word1);
        WordDAL.insertWord(context, word2);
        WordDAL.insertWord(context, word3);
        WordDAL.insertWord(context, word4);
        WordDAL.insertWord(context, word5);

        //get articles with status 5
        List<Word> wordList = WordDAL.getAllWordsWithStatus(context, 5);

        //so sanh
        if(wordList.get(0).equals(word4) && wordList.get(1).equals(word5)){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL";
        }
        return str;
    }

    public static String test_getAllWords2(Context context){
        String str = "test_getAllWords2: ";


        //Insert articles
        Word word1 = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        Word word2 = new Word(2, "Hi", "Xin chao", 2, 2, 3);
        Word word3 = new Word(3, "Good", "tot", 3, 2, 3);
        Word word4 = new Word(4, "Morning", "ko bik nghia", 5, 2, 3);
        Word word5 = new Word(5, "Trung", "Trung", 5, 2, 3);
        WordDAL.insertWord(context, word1);
        WordDAL.insertWord(context, word2);
        WordDAL.insertWord(context, word3);
        WordDAL.insertWord(context, word4);
        WordDAL.insertWord(context, word5);

        //get articles with status 5
        List<Word> list = WordDAL.getAllWords(context, 2, 5);

        //so sanh
        if(list.get(0).equals(word3) && list.get(1).equals(word4) && list.get(2).equals(word5)){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL " + list.get(0);
        }
        return str;
        }

    public static String test_getAllWordsWithStatus2(Context context){
        String str = "test_getAllWordsWithStatus2: ";


        //Insert articles
        Word word1 = new Word(1, "Hello", "Xin chao", 1, 2, 3);
        Word word2 = new Word(2, "Hi", "Xin chao", 5, 2, 3);
        Word word3 = new Word(3, "Good", "tot", 5, 2, 3);
        Word word4 = new Word(4, "Morning", "ko bik nghia", 5, 2, 3);
        Word word5 = new Word(5, "Trung", "Trung", 5, 2, 3);
        WordDAL.insertWord(context, word1);
        WordDAL.insertWord(context, word2);
        WordDAL.insertWord(context, word3);
        WordDAL.insertWord(context, word4);
        WordDAL.insertWord(context, word5);

        //get articles with status 5
        List<Word> list = WordDAL.getAllWordsWithStatus(context, 5, 1, 5);

        //so sanh
        if(list.get(0).equals(word3) && list.get(1).equals(word4) && list.get(2).equals(word5)){
            str += "SUCCESSFUL";
        }else {
            str += "FAIL" + list.get(0);
        }
        return str;
    }
    }

