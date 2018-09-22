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
