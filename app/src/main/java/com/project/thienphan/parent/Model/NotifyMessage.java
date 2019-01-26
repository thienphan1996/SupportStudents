package com.project.thienphan.parent.Model;

public class NotifyMessage {
    private String message;
    private boolean isParent;

    public NotifyMessage(String message, boolean isParent) {
        this.message = message;
        this.isParent = isParent;
    }

    public NotifyMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }
}
