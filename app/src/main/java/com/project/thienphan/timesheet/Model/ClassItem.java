package com.project.thienphan.timesheet.Model;

import java.util.ArrayList;
import java.util.Objects;

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

    public static ArrayList<ClassItem> SortTeacherClass(ArrayList<ClassItem> list){
        ArrayList<ClassItem> temp = new ArrayList<>();
        for (int i = 0 ; i < list.size()*2; i++){
            temp.add(new ClassItem("", (long)0, null, (long) 0, (long) 0, "", "", "", "", "", ""));
        }
        for (ClassItem item : list){
            if (item.getDayofWeek() == 2){
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(0,item);
                    }
                    else {
                        temp.set(6,item);
                    }
                }
            }
            else if (item.getDayofWeek() == 3){
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(1,item);
                    }
                    else {
                        temp.set(7,item);
                    }
                }
            }
            else if (item.getDayofWeek() == 4){
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(2,item);
                    }
                    else {
                        temp.set(8,item);
                    }
                }
            }
            else if (item.getDayofWeek() == 5){
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(3,item);
                    }
                    else {
                        temp.set(9,item);
                    }
                }
            }
            else if (item.getDayofWeek() == 6){
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(4,item);
                    }
                    else {
                        temp.set(10,item);
                    }
                }
            }
            else {
                if (item.getSubjectTime() != null && item.getSubjectTime().length() > 1){
                    int time = Integer.parseInt(item.getSubjectTime().substring(0,1));
                    if (time < 6){
                        temp.set(5,item);
                    }
                    else {
                        temp.set(11,item);
                    }
                }
            }
        }
        return temp;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassItem)) return false;
        ClassItem classItem = (ClassItem) o;
        return Objects.equals(getSubjectName(), classItem.getSubjectName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubjectCode(), getDayofWeek(), getListStudent(), getStudentNumber(), getSubjectGroup(), getSubjectLocation(), getSubjectName(), getSubjectTeacher(), getSubjectTime(), getTeacherCode(), getStudents());
    }
}
