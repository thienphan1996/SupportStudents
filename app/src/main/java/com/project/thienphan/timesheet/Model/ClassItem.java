package com.project.thienphan.timesheet.Model;

import java.util.ArrayList;

public class ClassItem {

    private String subjectCode;
    private Long DayofWeek;
    private ArrayList<StudentInfomation> listStudent;
    private Long studentNumber;
    private Long subjectGroup;
    private String subjectLocation;
    private String subjectName;
    private String subjectTeacher;
    private String subjectTime;
    private String teacherCode;
    private String students;

    public ClassItem() {
    }

    public ClassItem(String subjectCode, Long dayofWeek, ArrayList<StudentInfomation> listStudent, Long studentNumber, Long subjectGroup, String subjectLocation, String subjectName, String subjectTeacher, String subjectTime, String teacherCode, String students) {
        this.subjectCode = subjectCode;
        DayofWeek = dayofWeek;
        this.listStudent = listStudent;
        this.studentNumber = studentNumber;
        this.subjectGroup = subjectGroup;
        this.subjectLocation = subjectLocation;
        this.subjectName = subjectName;
        this.subjectTeacher = subjectTeacher;
        this.subjectTime = subjectTime;
        this.teacherCode = teacherCode;
        this.students = students;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Long getDayofWeek() {
        return DayofWeek;
    }

    public void setDayofWeek(Long dayofWeek) {
        DayofWeek = dayofWeek;
    }

    public ArrayList<StudentInfomation> getListStudent() {
        return listStudent;
    }

    public void setListStudent(ArrayList<StudentInfomation> listStudent) {
        this.listStudent = listStudent;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Long getSubjectGroup() {
        return subjectGroup;
    }

    public void setSubjectGroup(Long subjectGroup) {
        this.subjectGroup = subjectGroup;
    }

    public String getSubjectLocation() {
        return subjectLocation;
    }

    public void setSubjectLocation(String subjectLocation) {
        this.subjectLocation = subjectLocation;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public String getSubjectTime() {
        return subjectTime;
    }

    public void setSubjectTime(String subjectTime) {
        this.subjectTime = subjectTime;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
