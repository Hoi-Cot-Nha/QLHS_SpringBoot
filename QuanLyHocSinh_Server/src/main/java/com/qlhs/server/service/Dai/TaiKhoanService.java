package com.qlhs.server.service.Dai;

import com.qlhs.server.entity.TaiKhoan;
import com.qlhs.server.repository.Dai.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    /**
     * Kiểm tra đăng nhập
     */
    public TaiKhoan checkLogin(String username, String password) {
        Optional<TaiKhoan> tk =
                taiKhoanRepository.findByTenDangNhapAndMatKhau(username, password);
        return tk.orElse(null); // Trả về null nếu không tìm thấy
    }

    /**
     * Lấy toàn bộ tài khoản
     */
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanRepository.findAll();
    }

    /**
     * Lấy tài khoản theo tên đăng nhập
     */
    public Optional<TaiKhoan> getTaiKhoanByTenDangNhap(String tenDangNhap) {
        return taiKhoanRepository.findById(tenDangNhap);
    }

    /**
     * Tìm kiếm tài khoản
     */
    public List<TaiKhoan> searchTaiKhoan(String keyword) {
        return taiKhoanRepository
                .findByTenDangNhapContainingOrQuyenContainingOrMaNguoiDungContaining(
                        keyword,
                        keyword,
                        keyword
                );
    }

    /**
     * Thêm hoặc cập nhật tài khoản
     */
    public TaiKhoan saveTaiKhoan(TaiKhoan taiKhoan) {

        if (taiKhoan.getTenDangNhap() == null ||
                taiKhoan.getTenDangNhap().isBlank()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống!");
        }

        if (taiKhoan.getMatKhau() == null ||
                taiKhoan.getMatKhau().isBlank()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống!");
        }

        if (taiKhoan.getQuyen() == null ||
                taiKhoan.getQuyen().isBlank()) {
            throw new IllegalArgumentException("Quyền không được để trống!");
        }

        return taiKhoanRepository.save(taiKhoan);
    }

    /**
     * Xóa tài khoản
     */
    public void deleteTaiKhoan(String tenDangNhap) {
        taiKhoanRepository.deleteById(tenDangNhap);
    }

}