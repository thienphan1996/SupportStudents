package com.project.thienphan.timesheet.Model;

public class NotificationItem {
    private String subject;
    private String title;
    private String message;
    private String time;
    private boolean isSeen;

    public NotificationItem() {
    }

    public NotificationItem(String subject, String title, String message, String time, boolean isSeen) {
        this.subject = subject;
        this.title = title;
        this.message = message;
        this.time = time;
        this.isSeen = isSeen;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
