package com.qlhs.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Lop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lop {

    @Id
    @Column(name = "MaLop", length = 10)
    private String maLop;

    @Column(name = "TenLop", nullable = false, length = 50)
    private String tenLop;

    @Column(name = "NienKhoa", length = 20)
    private String nienKhoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaGVCN")
    private GiaoVien giaoVienChuNhiem;


}