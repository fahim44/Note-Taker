package com.astro_monkey.notetaker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fahim on 03-Mar-15.
 */
public class NoteDBOpenhelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;

    public static final String TBL_NOTES = "notes";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_NOTE = "note";

    public static final String TBL_CREATE =
            "CREATE TABLE " + TBL_NOTES + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TITLE + " TEXT, " +
                    COL_NOTE + " TEXT " +
                    ")";


    public NoteDBOpenhelper(Context context){
          super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TBL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NOTES);
        onCreate(db);
    }
}
