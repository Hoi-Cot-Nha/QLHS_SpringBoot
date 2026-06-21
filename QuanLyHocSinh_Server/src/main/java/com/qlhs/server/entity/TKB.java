package com.qlhs.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ThoiKhoaBieu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TKB {

    @Id
    @Column(name = "MaTKB", length = 50)
    private String maTKB;

    @Column(name = "Thu", length = 50)
    private int thu;

    @Column(name = "MaLop", length = 50)
    private String maLop;

    @Column(name = "MaMH", length = 50)
    private String maMH;

    @Column(name = "MaGV", length = 50)
    private String maGV;

    @Column(name = "MaPH", length = 50)
    private String maPH;

    @Column(name = "TietBD", length = 50)
    private int tietBD;

    @Column(name = "TietKT", length = 50)
    private int tietKT;
}
