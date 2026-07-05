package com.qlhs.server.repository.Dai;

import com.qlhs.server.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {

    /**
     * Đăng nhập
     * Tương đương:
     * SELECT * FROM TaiKhoan
     * WHERE TenDangNhap = ? AND MatKhau = ?
     */
    Optional<TaiKhoan> findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);

    /**
     * Tìm kiếm
     * Tương đương:
     * SELECT *
     * FROM TaiKhoan
     * WHERE TenDangNhap LIKE ?
     *    OR Quyen LIKE ?
     *    OR MaNguoiDung LIKE ?
     */
    List<TaiKhoan> findByTenDangNhapContainingOrQuyenContainingOrMaNguoiDungContaining(
            String tenDangNhap,
            String quyen,
            String maNguoiDung
    );
}