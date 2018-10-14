package com.project.thienphan.timesheet.Model;

import com.project.thienphan.timesheet.View.LoginActivity;

public class StudentInfomation {
    private String GioiTinh;
    private String HeDaoTao;
    private String HoTen;
    private Long KhoaHoc;
    private String LopHoc;
    private String MaNganh;
    private String MaSV;
    private String NgaySinh;
    private boolean isCheck;

    public StudentInfomation() {
    }

    public StudentInfomation(String gioiTinh, String heDaoTao, String hoTen, Long khoaHoc, String lopHoc, String maNganh, String maSV, String ngaySinh) {
        GioiTinh = gioiTinh;
        HeDaoTao = heDaoTao;
        HoTen = hoTen;
        KhoaHoc = khoaHoc;
        LopHoc = lopHoc;
        MaNganh = maNganh;
        MaSV = maSV;
        NgaySinh = ngaySinh;
        isCheck = true;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getHeDaoTao() {
        return HeDaoTao;
    }

    public void setHeDaoTao(String heDaoTao) {
        HeDaoTao = heDaoTao;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public Long getKhoaHoc() {
        return KhoaHoc;
    }

    public void setKhoaHoc(Long khoaHoc) {
        KhoaHoc = khoaHoc;
    }

    public String getLopHoc() {
        return LopHoc;
    }

    public void setLopHoc(String lopHoc) {
        LopHoc = lopHoc;
    }

    public String getMaNganh() {
        return MaNganh;
    }

    public void setMaNganh(String maNganh) {
        MaNganh = maNganh;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }
}
