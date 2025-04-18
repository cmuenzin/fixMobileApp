package com.example.fixapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.fixapp.models.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet die Tabelle "projects" mit Spalten:
 * id (PK), title, date (ms), thumbnailPath.
 */
public class ProjectsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fixapp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PROJECTS    = "projects";
    public static final String COLUMN_ID         = "id";
    public static final String COLUMN_TITLE      = "title";
    public static final String COLUMN_DATE       = "date";
    public static final String COLUMN_THUMB_PATH = "thumbnailPath";

    // SQL zum Anlegen der Tabelle
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_PROJECTS + " (" +
                    COLUMN_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE+ " TEXT, " +
                    COLUMN_DATE + " INTEGER, " +
                    COLUMN_THUMB_PATH + " TEXT)";

    public ProjectsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Für MVP: einfach neu anlegen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    /** Fügt ein neues Projekt ein und gibt die neue ID zurück. */
    public long addProject(String title, long date, String thumbnailPath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_THUMB_PATH, thumbnailPath);
        long id = db.insert(TABLE_PROJECTS, null, cv);
        db.close();
        return id;
    }

    /** Liest alle Projekte absteigend nach Datum. */
    public List<Project> getAllProjects() {
        List<Project> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                TABLE_PROJECTS, null, null, null, null, null,
                COLUMN_DATE + " DESC"
        );
        if (c.moveToFirst()) {
            do {
                long id   = c.getLong(c.getColumnIndexOrThrow(COLUMN_ID));
                String title = c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE));
                long date = c.getLong(c.getColumnIndexOrThrow(COLUMN_DATE));
                String thumb = c.getString(c.getColumnIndexOrThrow(COLUMN_THUMB_PATH));
                list.add(new Project(id, title, date, thumb));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }
}
