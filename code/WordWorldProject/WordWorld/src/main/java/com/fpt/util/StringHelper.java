package com.fpt.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr Dj Thuk
 */
public class StringHelper {

    private static final String TAGBEGIN = "<";
    private static final String TAGEND = ">";
    private static final String DOUBLESPACE = "00";
    private static final char SPACE = '0';
    private static final String[] SPECAILWORD = new String[]{"'s", "'re", "'ll", "'ve", "'m"};

    /**
     * This method will get all real word in the String contain html tag.
     * @param contentHTML
     * @return all real word in list
     */
    public static List<String> getListWord(String contentHTML) {
        List<String> list = new ArrayList<String>();
        try {
            // remove all tags
            int beginTagIndex = -1;
            int endTagIndex = 0;
            while (beginTagIndex < endTagIndex) {
                beginTagIndex = contentHTML.indexOf(TAGBEGIN);
                endTagIndex = contentHTML.indexOf(TAGEND);
                if (beginTagIndex < endTagIndex) {
                    // delete tag
                    contentHTML = contentHTML.substring(0, beginTagIndex)
                            + contentHTML.substring(endTagIndex + 1, contentHTML.length());
                }
            }

            // if contentHTML empty then return
            if (contentHTML.isEmpty()) {
                return list;
            }

            // remove all special characters
            int i;
            char ch;
            for (i = 0; i < contentHTML.length(); i++) {
                ch = contentHTML.charAt(i);
                if (!Character.isLetter(ch) && (ch != '\'')) {
                    contentHTML = contentHTML.replace(ch, SPACE);
                }
            }
            // if contentHTML empty then return
            if (contentHTML.isEmpty()) {
                return list;
            }
            // remove all redundant spaces
            int doubleSpaceIndex = contentHTML.indexOf(DOUBLESPACE);
            while (doubleSpaceIndex >= 0) {
                contentHTML = contentHTML.replaceAll(DOUBLESPACE, SPACE + "");
                doubleSpaceIndex = contentHTML.indexOf(DOUBLESPACE);
            }

            // if contentHTML empty then return
            if (contentHTML.isEmpty()) {
                return list;
            }

            // remove space if contentHMTL begin with space
            if (contentHTML.charAt(0) == SPACE) {
                contentHTML = contentHTML.substring(1, contentHTML.length());
            }

            // if contentHTML empty then return
            if (contentHTML.isEmpty()) {
                return list;
            }

            // remove space if contentHTML end with space;
            if (contentHTML.charAt(contentHTML.length() - 1) != SPACE) {
                contentHTML += SPACE;
            }

            // get all articles
            int beginSpaceIndex = 0;
            int endSpaceIndex = contentHTML.indexOf(SPACE, beginSpaceIndex + 1);
            String word = "";
            while ((beginSpaceIndex <= endSpaceIndex) && (endSpaceIndex >= 0)) {
                word = contentHTML.substring(beginSpaceIndex, endSpaceIndex);
                if (isRealWord(word)) {
                    if (!list.contains(word)) {
                        list.add(word);
                    }
                }
                beginSpaceIndex = endSpaceIndex + 1;
                endSpaceIndex = contentHTML.indexOf(SPACE, beginSpaceIndex + 1);
            }

        } catch (Exception e) {

        }
        return list;
    }

    /**
     * [Give the description for method].
     * @param word
     * @return
     */
    private static boolean isRealWord(String word) {
        for (int i = 0; i < SPECAILWORD.length; i++) {
            if (word.contains(SPECAILWORD[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * this method will add tag to all word in contentHTML which is occur in listWord.
     * @param contentHTML
     * @param listWord
     * @param beginTag
     * @param endTag
     * @return String of contentHTML with added tag
     */
    public static String colorWord(String contentHTML, List<String> listWord, String beginTag, String endTag) {
        String tempContent = contentHTML.toUpperCase();
        try {
            int wordIndex = 0;
            int beginTagIndex = 0;
            int endTagIndex = 0;
            String temp = "";
            String wordFind = "";
            for (String word : listWord) {
                do {
                    // upper case the word
                    wordFind = word.toUpperCase();
                    // get next index of word from the previous index
                    wordIndex = tempContent.indexOf(wordFind, wordIndex);
                    if (wordIndex > 0) {
                        char charAfterWord = tempContent.charAt(wordIndex + word.length());
                        char charBeforeWord = tempContent.charAt(wordIndex - 1);
                        if (!Character.isLetter(charAfterWord) && !Character.isLetter(charBeforeWord)) {
                            // get the next begin tag from the word
                            beginTagIndex = tempContent.indexOf(TAGBEGIN, wordIndex);
                            // get the next end tag from the word
                            endTagIndex = tempContent.indexOf(TAGEND, wordIndex);
                            // if the word is in the content then change word

                            if ((beginTagIndex < endTagIndex) && (beginTagIndex > 0)) {
                                // add tag to tempContent
                                temp = beginTag + word + endTag;
                                tempContent = tempContent.substring(0, wordIndex) + temp
                                        + tempContent.substring(wordIndex + word.length(), tempContent.length());

                                // add tag to contentHTML- the return result
                                temp = beginTag + contentHTML.substring(wordIndex, wordIndex + word.length()) + endTag;
                                contentHTML = contentHTML.substring(0, wordIndex) + temp
                                        + contentHTML.substring(wordIndex + word.length(), contentHTML.length());

                                // position for find next occur of the word
                                wordIndex += (beginTag.length() + endTag.length() + word.length());
                            } else {
                                wordIndex = endTagIndex;
                            }
                        } else {
                            wordIndex += word.length();
                        }
                    }

                } while (wordIndex > 0);
            }

        } catch (Exception e) {

        }
        return contentHTML;
    }

    /**
     * the rate listKnowWord occur in listWord.
     * @param listWord
     * @param listKnowWord
     * @return rate knew word / all word
     */
    public static double getKnowWordRate(List<String> listWord, List<String> listKnowWord) {
        double rate = 0;
        int noOfKnowWord = 0;
        if (listWord.size() == 0) {
            return 0;
        }
        if (listKnowWord.size() == 0) {
            return 1;
        }
        for (String word : listWord) {
            if (listKnowWord.contains(word)) {
                noOfKnowWord++;
            }
        }
        rate = (double) noOfKnowWord / listWord.size();
        return rate;
    }

    public static String colorAllWord(String contentHTML, List<String> listColorWord, List<String> listAllWord,
                                      String beginTag, String endTag,
                                      String beginNoColorTag, String endNoColorTag) {
        String string1 = colorWord(contentHTML, listColorWord, beginTag, endTag);
        string1 = colorAllWord(string1, listColorWord, listAllWord, beginNoColorTag, endNoColorTag);
        return string1;
    }

    /**
     * [Give the description for method].
     * @param contentHTML
     * @param listColorWord
     * @param listAllWord
     * @param beginNoColorTag
     * @param endNoColorTag
     * @return
     */
    public static String colorAllWord(String contentHTML, List<String> listColorWord, List<String> listAllWord,
                                      String beginNoColorTag, String endNoColorTag) {
        String tempContent = contentHTML.toUpperCase();
        try {
            int wordIndex = 0;
            int beginTagIndex = 0;
            int endTagIndex = 0;
            String temp = "";
            String wordFind = "";
            for (String word : listAllWord) {
                if (!listColorWord.contains(word)) {
                    do {
                        // upper case the word
                        wordFind = word.toUpperCase();
                        // get next index of word from the previous index
                        wordIndex = tempContent.indexOf(wordFind, wordIndex);
                        if (wordIndex > 0) {
                            char charAfterWord = tempContent.charAt(wordIndex + word.length());
                            char charBeforeWord = tempContent.charAt(wordIndex - 1);
                            if (!Character.isLetter(charAfterWord) && !Character.isLetter(charBeforeWord)) {
                                // get the next begin tag from the word
                                beginTagIndex = tempContent.indexOf(TAGBEGIN, wordIndex);
                                // get the next end tag from the word
                                endTagIndex = tempContent.indexOf(TAGEND, wordIndex);
                                // if the word is in the content then change word

                                if ((beginTagIndex < endTagIndex) && (beginTagIndex > 0)) {
                                    // add tag to tempContent
                                    temp = beginNoColorTag + word + endNoColorTag;
                                    tempContent = tempContent.substring(0, wordIndex) + temp
                                            + tempContent.substring(wordIndex + word.length(), tempContent.length());

                                    // add tag to contentHTML- the return result
                                    temp = beginNoColorTag + contentHTML.substring(wordIndex, wordIndex + word.length())
                                            + endNoColorTag;
                                    contentHTML = contentHTML.substring(0, wordIndex) + temp
                                            + contentHTML.substring(wordIndex + word.length(), contentHTML.length());

                                    // position for find next occur of the word
                                    wordIndex += (beginNoColorTag.length() + endNoColorTag.length() + word.length());
                                } else {
                                    wordIndex = endTagIndex;
                                }
                            } else {
                                wordIndex += word.length();
                            }
                        }

                    } while (wordIndex > 0);
                }
            }

        } catch (Exception e) {

        }
        return contentHTML;
    }

}
