package com.qlhs.server.repository;

import com.qlhs.server.entity.HanhKiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HanhKiemRepository extends JpaRepository<HanhKiem, String> {
    
}
