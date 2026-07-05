package com.qlhs.server.repository;

import com.qlhs.server.entity.LichThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LichThiRepository extends JpaRepository<LichThi, Integer> {

    @Query("SELECT l FROM LichThi l WHERE l.tenKyThi LIKE %:keyword% OR l.maMH LIKE %:keyword%")
    List<LichThi> searchLichThi(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT l.tenKyThi FROM LichThi l")
    List<String> getDistinctKyThi();

    @Query("SELECT l FROM LichThi l JOIN FETCH l.monHoc WHERE l.tenKyThi LIKE %:tenKyThi% AND l.maMH LIKE %:maMH% AND l.maPhong LIKE %:maPhong%")
    List<LichThi> filterLichThi(@Param("tenKyThi") String tenKyThi, @Param("maMH") String maMH,
            @Param("maPhong") String maPhong);

    @Query("SELECT l FROM LichThi l JOIN FETCH l.monHoc")
    List<LichThi> getAllLichThiWithTenMon();

    @Query("SELECT l FROM LichThi l JOIN FETCH l.monHoc WHERE l.tenKyThi LIKE %:keyword% OR l.monHoc.tenMH LIKE %:keyword% OR l.maMH LIKE %:keyword%")
    List<LichThi> searchLichThiNative(@Param("keyword") String keyword);
}
