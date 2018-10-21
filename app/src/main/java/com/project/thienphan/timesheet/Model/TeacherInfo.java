package com.project.thienphan.timesheet.Model;

import java.util.ArrayList;

public class TeacherInfo {
    private String teacherCode;
    private String teacherName;
    private String sex;
    private String birthDay;
    private String phoneNumber;
    private ArrayList<Subject> subject;
    private String classCode;

    public TeacherInfo() {
    }

    public TeacherInfo(String teacherCode, String teacherName, String sex, String birthDay, String phoneNumber, ArrayList<Subject> subject,String classCode) {
        this.teacherCode = teacherCode;
        this.teacherName = teacherName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Subject> getSubject() {
        return subject;
    }

    public void setSubject(ArrayList<Subject> subject) {
        this.subject = subject;
    }
}
