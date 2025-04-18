package com.example.fixapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fixapp.models.Project;
import com.example.fixapp.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet SQLite-Datenbank für FIX-App mit Tabellen für Projekte und Nachrichten.
 */
public class ProjectsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fixapp.db";
    private static final int DATABASE_VERSION = 1;

    // Projekte-Tabelle
    public static final String TABLE_PROJECTS    = "projects";
    public static final String COLUMN_ID         = "id";
    public static final String COLUMN_TITLE      = "title";
    public static final String COLUMN_DATE       = "date";
    public static final String COLUMN_THUMB_PATH = "thumbnailPath";

    // Nachrichten-Tabelle
    public static final String TABLE_MESSAGES    = "messages";
    public static final String COLUMN_MSG_ID     = "id";
    public static final String COLUMN_PROJECT_ID = "projectId";
    public static final String COLUMN_SENDER     = "sender";
    public static final String COLUMN_TEXT       = "text";
    public static final String COLUMN_TIMESTAMP  = "timestamp";

    // SQL für Tabellen
    private static final String SQL_CREATE_PROJECTS =
            "CREATE TABLE " + TABLE_PROJECTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DATE + " INTEGER, " +
                    COLUMN_THUMB_PATH + " TEXT)";

    private static final String SQL_CREATE_MESSAGES =
            "CREATE TABLE " + TABLE_MESSAGES + " (" +
                    COLUMN_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PROJECT_ID + " INTEGER, " +
                    COLUMN_SENDER + " TEXT, " +
                    COLUMN_TEXT + " TEXT, " +
                    COLUMN_TIMESTAMP + " INTEGER)";

    public ProjectsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROJECTS);
        db.execSQL(SQL_CREATE_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    /** Fügt ein neues Projekt hinzu und liefert die ID. */
    public long addProject(String title, long date, String thumbnailPath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_THUMB_PATH, thumbnailPath);
        long id = db.insert(TABLE_PROJECTS, null, values);
        db.close();
        return id;
    }

    /** Liest alle Projekte, absteigend nach Datum. */
    public List<Project> getAllProjects() {
        List<Project> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_PROJECTS,
                null, null, null, null, null,
                COLUMN_DATE + " DESC"
        );
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String thumb = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMB_PATH));
                list.add(new Project(id, title, date, thumb));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /** Fügt eine Nachricht hinzu und liefert die ID. */
    public long addMessage(long projectId, String sender, String text, long timestamp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_ID, projectId);
        values.put(COLUMN_SENDER, sender);
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_TIMESTAMP, timestamp);
        long id = db.insert(TABLE_MESSAGES, null, values);
        db.close();
        return id;
    }

    /** Liest alle Nachrichten zu einem Projekt, aufsteigend nach Zeit. */
    public List<Message> getMessagesForProject(long projectId) {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_MESSAGES,
                null,
                COLUMN_PROJECT_ID + " = ?",
                new String[]{ String.valueOf(projectId) },
                null, null,
                COLUMN_TIMESTAMP + " ASC"
        );
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_MSG_ID));
                String sender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENDER));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT));
                long time = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));
                list.add(new Message(id, projectId, sender, text, time));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
