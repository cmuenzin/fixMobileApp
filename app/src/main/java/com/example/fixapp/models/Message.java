package com.example.fixapp.models;

public class Message {
    private long id;
    private long projectId;
    private String sender;
    private String text;
    private long timestamp;

    public Message(long id, long projectId, String sender, String text, long timestamp) {
        this.id = id;
        this.projectId = projectId;
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public long getProjectId() { return projectId; }
    public String getSender() { return sender; }
    public String getText() { return text; }
    public long getTimestamp() { return timestamp; }
}
