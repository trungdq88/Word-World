package com.fpt.util;

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

}
