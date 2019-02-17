package com.project.thienphan.timesheet.Model;

import java.util.ArrayList;

public class NotificationItem {
    private String subject;
    private String title;
    private String message;
    private String time;
    private boolean isSeen;
    private Long createAt;

    public NotificationItem() {
    }

    public NotificationItem(String subject, String title, String message, String time, boolean isSeen, Long createAt) {
        this.subject = subject;
        this.title = title;
        this.message = message;
        this.time = time;
        this.isSeen = isSeen;
        this.createAt = createAt;
    }

    public static ArrayList<NotificationItem> SortNotifications(ArrayList<NotificationItem> data){
        if (data != null && data.size() > 1){
            for (int i = 0; i < data.size(); i++)
                for (int j = i + 1; j < data.size(); j++){
                    if (data.get(i).getCreateAt() < data.get(j).getCreateAt()){
                        NotificationItem temp = data.get(i);
                        data.set(i, data.get(j));
                        data.set(j, temp);
                    }
                }
        }
        return data;
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

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }
}
