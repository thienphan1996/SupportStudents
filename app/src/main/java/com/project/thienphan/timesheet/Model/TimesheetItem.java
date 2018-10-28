package com.project.thienphan.timesheet.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class TimesheetItem implements Serializable {
    private String SubjectCode;
    private String SubjectName;
    private int SubjectGroup;
    private String SubjectTeacher;
    private String SubjectTime;
    private String SubjectLocation;
    private int DayofWeek;

    public TimesheetItem() {
    }

    public TimesheetItem(String subjectCode, String subjectName, int subjectGroup, String subjectTeacher, String subjectTime, String subjectLocation, int dayofWeek) {

        SubjectCode = subjectCode;
        SubjectName = subjectName;
        SubjectGroup = subjectGroup;
        SubjectTeacher = subjectTeacher;
        SubjectTime = subjectTime;
        SubjectLocation = subjectLocation;
        DayofWeek = dayofWeek;
    }

    public static ArrayList<TimesheetItem> getTimesheetByDayofWeek(ArrayList<TimesheetItem> list,int day){
        ArrayList<TimesheetItem> listItem = new ArrayList<>();
        for (TimesheetItem item : list){
            if (item.getDayofWeek() == day){
                listItem.add(item);
            }
        }
        if (listItem.size() > 0){
            for (int i = 0; i < listItem.size(); i++)
                for (int j = i + 1; j < listItem.size(); j++){
                    if (listItem.get(i).getSubjectTime() != null && listItem.get(j).getSubjectTime() != null
                            && listItem.get(i).getSubjectTime().length() > 1
                            && listItem.get(j).getSubjectTime().length() > 1){
                        int iItem = Integer.parseInt(listItem.get(i).getSubjectTime().substring(0,1));
                        int jItem = Integer.parseInt(listItem.get(j).getSubjectTime().substring(0,1));
                        if (iItem > jItem){
                            TimesheetItem temp = listItem.get(j);
                            listItem.set(j,listItem.get(i));
                            listItem.set(i,temp);
                        }
                    }
                }
        }
        return listItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimesheetItem)) return false;
        TimesheetItem item = (TimesheetItem) o;
        return getSubjectGroup() == item.getSubjectGroup() &&
                getDayofWeek() == item.getDayofWeek() &&
                Objects.equals(getSubjectCode(), item.getSubjectCode()) &&
                Objects.equals(getSubjectName(), item.getSubjectName()) &&
                Objects.equals(getSubjectTeacher(), item.getSubjectTeacher()) &&
                Objects.equals(getSubjectTime(), item.getSubjectTime()) &&
                Objects.equals(getSubjectLocation(), item.getSubjectLocation());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSubjectCode(), getSubjectName(), getSubjectGroup(), getSubjectTeacher(), getSubjectTime(), getSubjectLocation(), getDayofWeek());
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public int getSubjectGroup() {
        return SubjectGroup;
    }

    public void setSubjectGroup(int subjectGroup) {
        SubjectGroup = subjectGroup;
    }

    public String getSubjectTeacher() {
        return SubjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        SubjectTeacher = subjectTeacher;
    }

    public String getSubjectTime() {
        return SubjectTime;
    }

    public void setSubjectTime(String subjectTime) {
        SubjectTime = subjectTime;
    }

    public String getSubjectLocation() {
        return SubjectLocation;
    }

    public void setSubjectLocation(String subjectLocation) {
        SubjectLocation = subjectLocation;
    }

    public int getDayofWeek() {
        return DayofWeek;
    }

    public void setDayofWeek(int dayofWeek) {
        DayofWeek = dayofWeek;
    }

    @Override
    public String toString() {
        return getSubjectCode() + "---" + getSubjectName() + "---" + getSubjectGroup() + "---" + getSubjectTeacher() + "---" + getSubjectTime() + "---" + getSubjectLocation() + "---" + getDayofWeek();
    }
}
