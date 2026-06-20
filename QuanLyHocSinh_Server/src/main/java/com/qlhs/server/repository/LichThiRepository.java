package com.qlhs.server.repository;

import com.qlhs.server.entity.LichThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichThiRepository extends JpaRepository<LichThi, String> {

}
