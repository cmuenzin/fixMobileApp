package com.example.fixapp.models;

/**
 * Datenmodell für ein Projekt/Chat.
 */
public class Project {
    private long id;
    private String title;
    private long date;            // Zeitstempel in ms
    private String thumbnailPath; // Pfad zur Thumbnail‑Datei

    public Project(long id, String title, long date, String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.thumbnailPath = thumbnailPath;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public long getDate() { return date; }
    public String getThumbnailPath() { return thumbnailPath; }
}
