package com.project.thienphan.parent.Model;

public class NotifyMessage {
    private String message;
    private boolean parent;
    private boolean hasReply;
    private Long id;

    public NotifyMessage(String message, boolean isParent, boolean hasReply, Long id) {
        this.message = message;
        this.parent = isParent;
        this.hasReply = hasReply;
        this.id = id;
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
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isHasReply() {
        return hasReply;
    }

    public void setHasReply(boolean hasReply) {
        this.hasReply = hasReply;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
