package com.fpt.util;

/**
 * Created by Dell on 11/16/13.
 */

import com.fpt.model.Article;
import com.fpt.model.Word;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * this class using for sort again array or ArrayList with parameters
 */
public class SortUtils {

    /**
     * use this class for all types of comparators that we need
     */
    public static class ComparatorObject {

        public static Comparator<Word> compareWordByABC() {
            return new Comparator<Word>() {
                @Override
                public int compare(Word word, Word word2) {
                    return word.the_word.compareTo(word2.the_word);
                }
            };
        }

        public static Comparator<Word> compareWordByTimes() {
            return new Comparator<Word>() {
                @Override
                public int compare(Word word, Word word2) {
                    return word.count - word2.count;
                }
            };
        }

        public static Comparator<Word> compareWordByRecentTime() {
            return new Comparator<Word>() {
                @Override
                public int compare(Word word, Word word2) {
                    return (int)(word.created - word2.created);
                }
            };
        }

        public static Comparator<Article> compareArticleByABC() {
            return new Comparator<Article>() {
                @Override
                public int compare(Article article, Article article2) {
                    return article.title.compareTo(article.title);
                }
            };
        }

        public static Comparator<Article> compareArticleByRecentTime() {
            return new Comparator<Article>() {
                @Override
                public int compare(Article article, Article article2) {
                    return (int)(article.created - article2.created);
                }
            };
        }


    }
    /**
     * generic method.
     * use this method for sorting object that already implements Comparable
     */
    public static<T extends Comparable> void sort(List<T> list) {
        Collections.sort(list);

    }

    /**
     * generic method
     * use this method for sorting object with Comparator function
     */
    public static<T> void sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
    }

    ////////////////////////////////////////////////////////////
    //////////////////// Sort non-generic method //////////////


    public static void sortWordByABC(List<Word> list) {
        sort(list, ComparatorObject.compareWordByABC());
    }

    public static void sortWordByTimes(List<Word> list) {
        sort(list, ComparatorObject.compareWordByTimes());
    }

    public static void sortWordByRecentTime(List<Word> list) {
        sort(list, ComparatorObject.compareWordByRecentTime());
    }

    public static void sortArticleByABC(List<Article> list) {
        sort(list, ComparatorObject.compareArticleByABC());
    }

    public static void sortArticleByRecentTime(List<Article> list) {
        sort(list, ComparatorObject.compareArticleByRecentTime());
    }


}
