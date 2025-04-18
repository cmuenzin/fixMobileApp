package com.example.fixapp.models;

/**
 * Datenmodell für eine FIX-Projekt-Session.
 */
public class Project {
    private long id;
    private String title;
    private long date;
    private String thumbnailPath;

    public Project(long id, String title, long date, String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.thumbnailPath = thumbnailPath;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }
}
