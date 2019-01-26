package com.project.thienphan.timesheet.Model;

public class LearningResult {
    private String MSSV;
    private String subjectCode;
    private String subjectName;
    private String lastComment;
    private Double killNumberFive;
    private Double killNumberFour;
    private Double killNumberOne;
    private Double killNumberThree;
    private Double killNumberTwo;
    private boolean showModal;

    public LearningResult() {
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Double getKillNumberFive() {
        return killNumberFive;
    }

    public void setKillNumberFive(Double killNumberFive) {
        this.killNumberFive = killNumberFive;
    }

    public Double getKillNumberFour() {
        return killNumberFour;
    }

    public void setKillNumberFour(Double killNumberFour) {
        this.killNumberFour = killNumberFour;
    }

    public Double getKillNumberOne() {
        return killNumberOne;
    }

    public void setKillNumberOne(Double killNumberOne) {
        this.killNumberOne = killNumberOne;
    }

    public Double getKillNumberThree() {
        return killNumberThree;
    }

    public void setKillNumberThree(Double killNumberThree) {
        this.killNumberThree = killNumberThree;
    }

    public Double getKillNumberTwo() {
        return killNumberTwo;
    }

    public void setKillNumberTwo(Double killNumberTwo) {
        this.killNumberTwo = killNumberTwo;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public boolean isShowModal() {
        return showModal;
    }

    public void setShowModal(boolean showModal) {
        this.showModal = showModal;
    }
}
