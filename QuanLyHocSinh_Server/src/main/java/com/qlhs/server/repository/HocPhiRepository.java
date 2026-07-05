package com.qlhs.server.repository;

import com.qlhs.server.entity.HocPhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface HocPhiRepository extends JpaRepository<HocPhi, Integer> {
    List<HocPhi> findByMaHS(String maHS);
    @Query("SELECT t FROM HocPhi t WHERE " +
            "LOWER(t.maLop) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<HocPhi> searchByKeyword(@Param("keyword") String keyword);
}