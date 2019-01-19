package com.project.thienphan.timesheet.Model;

public class LearningResult {
    private String MSSV;
    private String subjectCode;
    private String subjectName;
    private Double giuaKy;
    private Double cuoiKy;
    private Double trungBinh;

    public LearningResult() {
    }

    public LearningResult(String MSSV, String subjectCode, String subjectName, Double giuaKy, Double cuoiKy, Double trungBinh) {
        this.MSSV = MSSV;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.giuaKy = giuaKy;
        this.cuoiKy = cuoiKy;
        this.trungBinh = trungBinh;
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

    public Double getGiuaKy() {
        return giuaKy;
    }

    public void setGiuaKy(Double giuaKy) {
        this.giuaKy = giuaKy;
    }

    public Double getCuoiKy() {
        return cuoiKy;
    }

    public void setCuoiKy(Double cuoiKy) {
        this.cuoiKy = cuoiKy;
    }

    public Double getTrungBinh() {
        return trungBinh;
    }

    public void setTrungBinh(Double trungBinh) {
        this.trungBinh = trungBinh;
    }
}
