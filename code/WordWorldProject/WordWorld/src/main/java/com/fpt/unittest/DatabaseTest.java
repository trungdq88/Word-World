package com.fpt.unittest;

import android.content.Context;

import com.fpt.model.Article;
import com.fpt.model.dal.ArticleDAL;

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
            str += "OK";
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
            str += " OK";
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
            str += "OK";
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
        if(articleList.get(0).equals(article1)){
            str += "OK";
        }else{
            str += "FAIL: result: " + articleList.get(0).toString() + " | Expected: " + article1;
        }

        return str;


    }

}
