package com.qlhs.server.repository.Dai;

import com.qlhs.server.entity.HocSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocSinhRepository extends JpaRepository<HocSinh, String> {
    // Chỉ cần kế thừa JpaRepository, Spring Boot đã tự tạo sẵn các lệnh:
    // findAll() -> Lấy tất cả
    // findById() -> Tìm theo mã
    // save() -> Thêm/Sửa
    // deleteById() -> Xóa
    List<HocSinh> findByHoTenContaining(String hoTen);

    List<HocSinh> findByMaHSContaining(String maHS);
}
