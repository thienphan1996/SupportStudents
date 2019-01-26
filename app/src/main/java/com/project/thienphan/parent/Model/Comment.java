package com.project.thienphan.parent.Model;

import java.util.ArrayList;

public class Comment {
    private String subjectName;
    private boolean isShowModal;
    ArrayList<NotifyMessage> messages;

    public Comment(String subjectName, boolean isShowModal, ArrayList<NotifyMessage> messages) {
        this.subjectName = subjectName;
        this.isShowModal = isShowModal;
        this.messages = messages;
    }

    public Comment() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean isShowModal() {
        return isShowModal;
    }

    public void setShowModal(boolean showModal) {
        isShowModal = showModal;
    }

    public ArrayList<NotifyMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<NotifyMessage> messages) {
        this.messages = messages;
    }
}
