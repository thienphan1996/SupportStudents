package com.project.thienphan.timesheet.Model;

import java.util.Objects;

public class LeaveStudentModel {
    private String studentCode;
    private String studentName;
    private String leaveDay;
    private int leaveDayTotal;

    public LeaveStudentModel() {
    }

    public LeaveStudentModel(String studentCode, String studentName, String leaveDay, int leaveDayTotal) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.leaveDay = leaveDay;
        this.leaveDayTotal = leaveDayTotal;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
    }

    public int getLeaveDayTotal() {
        return leaveDayTotal;
    }

    public void setLeaveDayTotal(int leaveDayTotal) {
        this.leaveDayTotal = leaveDayTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveStudentModel)) return false;
        LeaveStudentModel that = (LeaveStudentModel) o;
        return Objects.equals(getStudentCode().trim(), that.getStudentCode().trim());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStudentCode(), getStudentName(), getLeaveDay(), getLeaveDayTotal());
    }
}
