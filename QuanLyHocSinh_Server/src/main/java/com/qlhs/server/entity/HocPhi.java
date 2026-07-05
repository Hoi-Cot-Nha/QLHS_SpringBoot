package com.qlhs.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HocPhi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocPhi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHP")
    private int maHP;

    @Column(name = "MaHS", length = 50)
    private String maHS;

    @Column(name = "MaLop", length = 50)
    private String maLop;

    @Column(name = "HocKy")
    private int hocKy;

    @Column(name = "NamHoc", length = 20)
    private String namHoc;

    @Column(name = "TongTien")
    private long tongTien;

    @Column(name = "MienGiam")
    private long mienGiam;

    @Column(name = "PhaiDong")
    private long phaiDong;

    @Column(name = "TrangThai", length = 50)
    private String trangThai;

}