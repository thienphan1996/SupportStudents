package com.project.thienphan.timesheet.Model;

import java.util.ArrayList;
import java.util.Objects;

public class TagContent {

    public static final int INFO = 0;
    public static final int NOTIFY = 1;
    public static final int PLAN = 2;
    public static final int CALENDAR = 3;
    public static final int REGISTER = 4;
    public static final int STUDENT = 5;
    public static final int WORK = 6;
    public static final int ADJUST = 7;
    public static final int RESULT = 8;
    public static final int WELCOME = 9;

    private String link;
    private String body;
    private int bodyType;

    public TagContent() {
    }

    public TagContent(String link, String body) {
        this.link = link;
        if (body != null && body.split(" ").length > 2){
            this.CreateTypeBody(body);
            if (body.length() > 80){
                int spaceIndex = body.indexOf(" ",80);
                this.body = spaceIndex != -1 ? body.substring(0,spaceIndex) + " ..." : body;
            }
            else {
                this.body = body;
            }
        }
    }

    private void CreateTypeBody(String body){
        String[] lstWord = body.split(" ");
        String key = (lstWord[0]).toLowerCase();
        switch (key){
            case "thông":
                if (lstWord[1].toLowerCase().equals("tin")){
                    this.bodyType = INFO;
                    break;
                }
                else {
                    this.bodyType = NOTIFY;
                    break;
                }
            case "kế":
                this.bodyType = PLAN;
                break;
            case "lịch":
                this.bodyType = CALENDAR;
                break;
            case "đăng":
                if (lstWord[1].toLowerCase().equals("kí") || lstWord[1].toLowerCase().equals("ký")){
                    this.bodyType = REGISTER;
                    break;
                }
            case "v/v":
                this.bodyType = WORK;
                break;
            case "về":
                this.bodyType = WORK;
                break;
            case "điều":
                this.bodyType = ADJUST;
                break;
            case "chuyển":
                this.bodyType = ADJUST;
                break;
            case "xoá":
                this.bodyType = ADJUST;
                break;
            case "quyết":
                this.bodyType = RESULT;
                break;
            case "chào":
                this.bodyType = WELCOME;
                break;
            default:
                this.bodyType = STUDENT;
                break;
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagContent)) return false;
        TagContent content = (TagContent) o;
        return getBodyType() == content.getBodyType() &&
                Objects.equals(getLink(), content.getLink()) &&
                Objects.equals(getBody(), content.getBody());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLink(), getBody(), getBodyType());
    }
}
