package com.fpt.model;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class Article {
    public int id;
    public int article_id;
    public String url;
    public String content;
    public int created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (article_id != article.article_id) return false;
        if (created != article.created) return false;
        if (!content.equals(article.content)) return false;
        if (!url.equals(article.url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = article_id;
        result = 31 * result + url.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + created;
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", article_id=" + article_id +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", created=" + created +
                '}';
    }
}
