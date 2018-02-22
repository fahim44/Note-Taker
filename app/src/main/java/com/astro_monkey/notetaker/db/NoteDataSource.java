package com.astro_monkey.notetaker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.astro_monkey.notetaker.model_class.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahim on 03-Mar-15.
 */
public class NoteDataSource {

    Context con;

                        SQLiteOpenHelper dbhelper;
                SQLiteDatabase database;

                private static final String[] note_cols ={
                        NoteDBOpenhelper.COL_ID,
                        NoteDBOpenhelper.COL_TITLE,
                        NoteDBOpenhelper.COL_NOTE
                };

            public NoteDataSource(Context context){
                con = context;
                dbhelper = new NoteDBOpenhelper(context);
                }

            public void open(){
                database = dbhelper.getWritableDatabase();
            }

            public void close(){
                dbhelper.close();
            }

            public void insert(Notes n){
                ContentValues values = new ContentValues();
                values.put(NoteDBOpenhelper.COL_TITLE,n.getTitle());
                values.put(NoteDBOpenhelper.COL_NOTE,n.getNote());

                database.insert(NoteDBOpenhelper.TBL_NOTES,null,values);

            }

            public List<Notes> findAll(){
                List<Notes> notes = new ArrayList<Notes>();

                Cursor cursor = database.query(NoteDBOpenhelper.TBL_NOTES, note_cols,
                        null, null, null, null, null);

                if(cursor.getCount()> 0){
                    while(cursor.moveToNext()) {
                        Notes note = new Notes();
                        note.setId(cursor.getLong(cursor.getColumnIndex(NoteDBOpenhelper.COL_ID)));
                        note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDBOpenhelper.COL_TITLE)));

                notes.add(note);
            }
        }

        return notes;
    }

    public Notes find(Long id){
        Notes note = new Notes();
        Cursor cursor = database.query(NoteDBOpenhelper.TBL_NOTES, note_cols,
                null, null, null, null, null);

        if(cursor.getCount()> 0) {
            while (cursor.moveToNext()) {
                if(cursor.getLong(cursor.getColumnIndex(NoteDBOpenhelper.COL_ID))== id){
                    note.setId(id);
                    note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDBOpenhelper.COL_TITLE)));
                    note.setNote(cursor.getString(cursor.getColumnIndex(NoteDBOpenhelper.COL_NOTE)));

                    return note;
                }
            }
        }

        return null;
    }
    public void update(Notes n){
        String where = NoteDBOpenhelper.COL_ID + "=" + Long.toString(n.getId());

        ContentValues value = new ContentValues();
        value.put(NoteDBOpenhelper.COL_TITLE,n.getTitle());
        value.put(NoteDBOpenhelper.COL_NOTE,n.getNote());

        database.update(NoteDBOpenhelper.TBL_NOTES, value, where, null);
    }

    public void delete(Long id){
        String where = NoteDBOpenhelper.COL_ID + "=" + Long.toString(id);
        database.delete(NoteDBOpenhelper.TBL_NOTES, where, null);
    }

    public void deleteAll(){
        database.execSQL("DROP TABLE IF EXISTS " + NoteDBOpenhelper.TBL_NOTES);
        database.execSQL(NoteDBOpenhelper.TBL_CREATE);
    }
}
