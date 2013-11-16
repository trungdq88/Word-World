package com.fpt.util;

import com.fpt.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class DisplayUtils {

    public static String arrayToString(List list) {
        String res = "";
        for (Object o : list) {
            res += o.toString() + "\n";
        }
        return res;
    }

    public static List<String> listWordToListString(List<Word> words) {
        List<String> sWords = new ArrayList<String>();
        for (Word word : words) {
            sWords.add(word.the_word);
        }
        return sWords;
    }

}
