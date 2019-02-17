package com.project.thienphan.parent.Model;

public class NotifyMessage {
    private String message;
    private boolean parent;
    private boolean hasReply;
    private Long id;
    private Long createAtMilisecons;
    private String createAt;

    public NotifyMessage(String message, boolean parent, boolean hasReply, Long id, Long createAtMilisecons, String createAt) {
        this.message = message;
        this.parent = parent;
        this.hasReply = hasReply;
        this.id = id;
        this.createAtMilisecons = createAtMilisecons;
        this.createAt = createAt;
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

    public Long getCreateAtMilisecons() {
        return createAtMilisecons;
    }

    public void setCreateAtMilisecons(Long createAtMilisecons) {
        this.createAtMilisecons = createAtMilisecons;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
