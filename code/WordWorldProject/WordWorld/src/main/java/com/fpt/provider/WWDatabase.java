package com.fpt.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import static com.fpt.provider.WWDBContract.*;
import static com.fpt.util.LogUtils.LOGD;
import static com.fpt.util.LogUtils.makeLogTag;

/**
 * helper for managing {@link android.database.sqlite.SQLiteDatabase}
 */
public class WWDatabase extends SQLiteOpenHelper {
    private static final String TAG = makeLogTag(WWDatabase.class);

    private final Context mContext;

    private static final String DATABASE_NAME = "fptww.db";
    private static final int DATABASE_VERSION = 1;

    // NOTE: carefully update onUpgrade() when bumping database versions
    // to make sure user data is saved

    public WWDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public WWDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * Called when the database connection is being configured
     * to enable features such as write-ahead logging or foreign key support.
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
    }

    /**
     * Called when the database has been opened. The implementation should check isReadOnly() before updating the database.
     This method is called after the database connection has been configured
     and after the database schema has been created, upgraded or downgraded as necessary.
     If the database connection must be configured in some way before the schema is created, upgraded, or downgraded
     do it in onConfigure(SQLiteDatabase) instead.
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }

    /**
     * Called when database is created for the first time
     * create all suitable tables here
     */
    @Override
    public synchronized void  onCreate(SQLiteDatabase db) {

        /**
         * Article table
         */
        db.execSQL("CREATE TABLE " + Tables.ARTICLE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ArticleColumns.CONTENT + " TEXT NOT NULL,"
                + ArticleColumns.TITLE + " TEXT NOT NULL,"
                + ArticleColumns.URL + " TEXT NOT NULL,"
                + ArticleColumns.CREATED + " INTEGER NOT NULL,"
                + "UNIQUE (" + BaseColumns._ID + ") ON CONFLICT REPLACE)");

        /**
         * Word table
         */
        db.execSQL("CREATE TABLE " + Tables.WORD + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WordColumns.THE_WORD + " TEXT NOT NULL,"
                + WordColumns.DESCRIPTION + " TEXT,"
                + WordColumns.STATUS + " INTEGER NOT NULL,"
                + WordColumns.COUNT + " INTEGER NOT NULL DEFAULT 0,"
                + WordColumns.CREATED + " INTEGER NOT NULL,"
                + "UNIQUE (" + BaseColumns._ID + ") ON CONFLICT REPLACE)");

        // Full-text search index. Update using updateSessionSearchIndex method.
        // Use the porter tokenizer for simple stemming, so that "frustration" matches "frustrated."

    }

    /**
     * Work when upgrade database version (update database schema)
     * often : save old data
     * drop tables.
     * create tables
     * return old data has been saved
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LOGD(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

        // drop all tables

        // create new tables
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
