package com.fpt.model.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fpt.model.Word;
import com.fpt.provider.WWDBContract;
import com.fpt.provider.WWDatabase;
import com.fpt.provider.helper.Query;

import java.util.ArrayList;
import java.util.List;

import static com.fpt.util.LogUtils.LOGD;
import static com.fpt.util.LogUtils.makeLogTag;

/**
 * Created by Quang Trung on 11/15/13.
 */
public class WordDAL {
    private static final String TAG = makeLogTag(WordDAL.class);

    public static boolean insertWord(Context context, Word word) {
        LOGD(TAG, "Adding an word");

        ContentValues cv = new ContentValues();
        cv.put(WWDBContract.Word.THE_WORD, word.the_word);
        cv.put(WWDBContract.Word.DESCRIPTION, word.description);
        cv.put(WWDBContract.Word.STATUS, word.status);
        cv.put(WWDBContract.Word.COUNT, word.count);
        cv.put(WWDBContract.Word.CREATED, word.created);

        WWDatabase db = new WWDatabase(context);
        return db.getWritableDatabase().insert(WWDBContract.Tables.WORD, "", cv) > 0;
    }

    public static boolean updateSeenCount(Context context, int id) {
        LOGD(TAG, "update count for " + id);

        WWDatabase db = new WWDatabase(context);
        ContentValues cv = new ContentValues();

//        cv.put(WWDBContract.Word.COUNT, WWDBContract.Word.COUNT + " + 1");
//
//        int result = db.getWritableDatabase().update(WWDBContract.Tables.WORD,
//                cv,
//                WWDBContract.Word._ID + "=?",
//                new String[]{String.valueOf(id)});

        db.getWritableDatabase().execSQL("UPDATE " + WWDBContract.Tables.WORD
                + " SET " + WWDBContract.Word.COUNT + " = " + WWDBContract.Word.COUNT + " + 1 "
                + "WHERE " + WWDBContract.Word._ID + " = " + id);

        return true;
    }

    /**
     * Get all articles, include status 1 and status 0 articles.
     * @param context
     * @return
     */
    public static List<Word> getAllWords(Context context) {
        LOGD(TAG, "Get All Words");
        WWDatabase ww = new WWDatabase(context);
        Cursor c = ww.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                null, null, null, null, null);

        List<Word> words = new ArrayList<Word>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return words;
    }

    /**
     * search word contain text
     * @param context
     * @param text
     * @return List of all word contain text
     */
    public static List<Word> getListWordBySearchText(Context context, String text) {
        LOGD(TAG, "get list of Word like Text");
        List<Word> list = new ArrayList<Word>();
        WWDatabase db = new WWDatabase(context);
        Cursor c = db.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                WWDBContract.Word.THE_WORD + " LIKE ?", // Selection strings
                new String[]{"%"+text+"%"}, // Select args
                null, // groyp by
                null, // having
                null); // orderby

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            Word word = new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated);
            list.add(word);
        }
        if (c != null) {
            c.close();
        }
        return list;
    }

    /**
     * Get all word with specific status.
     * @param context
     * @param status
     * @return
     */
    public static List<Word> getAllWordsWithStatus(Context context, int status) {
        LOGD(TAG, "Get All Words With Status");
        WWDatabase ww = new WWDatabase(context);
        Cursor c = ww.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                WWDBContract.Word.STATUS + "=?",
                new String[]{String.valueOf(status)},
                null, null, null);

        List<Word> words = new ArrayList<Word>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return words;
    }


    /**
     * Get all articles, include status 1 and status 0 articles. LIMIT offset, length (use for pagination)
     * @param context
     * @param offset
     * @param length
     * @return
     */
    public static List<Word> getAllWords(Context context, int offset, int length) {
        LOGD(TAG, "Get All Words");
        WWDatabase ww = new WWDatabase(context);
        Cursor c = ww.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                null,
                null,
                null,
                null,
                null,
                " " + offset + ", " + length);

        List<Word> words = new ArrayList<Word>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return words;
    }

    /**
     * Get all word with specific status, with LIMIT
     * @param context
     * @param status
     * @param offset
     * @param length
     * @return
     */
    public static List<Word> getAllWordsWithStatus(Context context, int status, int offset, int length) {
        LOGD(TAG, "get All Words With Status");
        WWDatabase ww = new WWDatabase(context);
        Cursor c = ww.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                WWDBContract.Word.STATUS + "=?",
                new String[]{String.valueOf(status)},
                null,
                null,
                null,
                " " + offset + ", " + length);

        List<Word> words = new ArrayList<Word>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return words;
    }


    public static Word getWordById(Context context, int id) {
        LOGD(TAG, "Get Word By Id");
        WWDatabase db = new WWDatabase(context);
        Cursor c = db.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                WWDBContract.Word._ID + "=?", // Selection strings
                new String[]{String.valueOf(id)}, // Select args
                null, // groyp by
                null, // having
                null); // orderby

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            return (new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public static Word getWordByText(Context context, String text) {
        LOGD(TAG, "get Word By Text");
        WWDatabase db = new WWDatabase(context);
        Cursor c = db.getWritableDatabase().query(WWDBContract.Tables.WORD,
                Query.Projections.WORD_PROJECTION,
                WWDBContract.Word.THE_WORD + "=?", // Selection strings
                new String[]{text}, // Select args
                null, // groyp by
                null, // having
                null); // orderby

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            int wordCount = c.getInt(c.getColumnIndex(WWDBContract.Word.COUNT));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            return (new Word(_id, wordTheWord, wordDescription, wordStatus, wordCount, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public static boolean updateWordStatus(Context context, int id, int status) {
        LOGD(TAG, "update Word Status");
        WWDatabase db = new WWDatabase(context);

        ContentValues cv = new ContentValues();
        cv.put(WWDBContract.Word.STATUS, status);

        int result = db.getWritableDatabase().update(WWDBContract.Tables.WORD,
                cv,
                WWDBContract.Word._ID + "=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }

    public static boolean updateWordDescription(Context context, int id, String description) {
        LOGD(TAG, "update Word Description");
        WWDatabase db = new WWDatabase(context);

        ContentValues cv = new ContentValues();
        cv.put(WWDBContract.Word.DESCRIPTION, description);

        int result = db.getWritableDatabase().update(WWDBContract.Tables.WORD,
                cv,
                WWDBContract.Word._ID + "=?",
                new String[]{String.valueOf(id)});

        return result > 0;
    }

    public static boolean deleteWordById(Context context, int id) {
        LOGD(TAG, "Delete Word By Id");
        WWDatabase db = new WWDatabase(context);
        int result = db.getWritableDatabase().delete(WWDBContract.Tables.WORD,
                WWDBContract.Word._ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }
}
