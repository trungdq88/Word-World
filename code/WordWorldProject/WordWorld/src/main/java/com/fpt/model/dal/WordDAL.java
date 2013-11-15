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
        cv.put(WWDBContract.Word.WORD_ID, word.word_id);
        cv.put(WWDBContract.Word.THE_WORD, word.the_word);
        cv.put(WWDBContract.Word.DESCRIPTION, word.description);
        cv.put(WWDBContract.Word.STATUS, word.status);
        cv.put(WWDBContract.Word.CREATED, word.created);

        WWDatabase db = new WWDatabase(context);
        return db.getWritableDatabase().insert(WWDBContract.Tables.WORD, "", cv) > 0;
    }

    /**
     * Get all words, include status 1 and status 0 words.
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
            int wordId = c.getInt(c.getColumnIndex(WWDBContract.Word.WORD_ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordId, wordTheWord, wordDescription, wordStatus, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return words;
    }


    /**
     * Get all words, include status 1 and status 0 words. LIMIT offset, length (use for pagination)
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
            int wordId = c.getInt(c.getColumnIndex(WWDBContract.Word.WORD_ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            words.add(new Word(_id, wordId, wordTheWord, wordDescription, wordStatus, wordCreated));
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
                WWDBContract.Word.WORD_ID + "=?", // Selection strings
                new String[]{String.valueOf(id)}, // Select args
                null, // groyp by
                null, // having
                null); // orderby

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(WWDBContract.Word._ID));
            int wordId = c.getInt(c.getColumnIndex(WWDBContract.Word.WORD_ID));
            String wordTheWord = c.getString(c.getColumnIndex(WWDBContract.Word.THE_WORD));
            String wordDescription = c.getString(c.getColumnIndex(WWDBContract.Word.DESCRIPTION));
            int wordStatus = c.getInt(c.getColumnIndex(WWDBContract.Word.STATUS));
            long wordCreated = c.getLong(c.getColumnIndex(WWDBContract.Word.CREATED));
            return (new Word(_id, wordId, wordTheWord, wordDescription, wordStatus, wordCreated));
        }
        if (c != null) {
            c.close();
        }
        return null;
    }

    public static boolean deleteWordById(Context context, int id) {
        LOGD(TAG, "Delete Word By Id");
        WWDatabase db = new WWDatabase(context);
        int result = db.getWritableDatabase().delete(WWDBContract.Tables.WORD,
                WWDBContract.Word.WORD_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }
}
