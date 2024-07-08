package com.example.edupay.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String id;
    private String tenDangNhap;
    private String matKhau;
    private String maSinhVien;
    private String tenSinhVien;
    private String lopHoc;
    private String nganhHoc;
    private List<MonHoc> monHoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(String lopHoc) {
        this.lopHoc = lopHoc;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public List<MonHoc> getMonHoc() {
        return monHoc;
    }
    public void setMonHoc(List<MonHoc> monHoc) {
        this.monHoc = monHoc;
    }
}
