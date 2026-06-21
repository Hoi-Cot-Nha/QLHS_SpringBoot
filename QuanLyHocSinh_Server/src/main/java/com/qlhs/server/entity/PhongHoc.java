package com.qlhs.server.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PhongHoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhongHoc {

    @Id
    @Column(name = "MaPH", length = 50)
    private String maPH;

    @Column(name = "TenPH", length = 100)
    private String tenPH;

    @Column(name = "LoaiPhong", length = 100)
    private String loaiPhong;

    @Column(name = "SucChua", length = 50)
    private int sucChua;

    @Column(name = "TinhTrang", length = 100)
    private String tinhTrang;
}
