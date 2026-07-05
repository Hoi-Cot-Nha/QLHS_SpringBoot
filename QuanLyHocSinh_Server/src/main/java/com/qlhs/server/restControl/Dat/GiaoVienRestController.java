package com.qlhs.server.restControl.Dat;

import com.qlhs.server.entity.GiaoVien;
import com.qlhs.server.service.Dat.GiaoVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/giaovien")
public class GiaoVienRestController {

    @Autowired
    private GiaoVienService giaoVienService;

    // Lấy tất cả
    @GetMapping
    public List<Map<String, Object>> getAllGiaoVien() {
        return giaoVienService.getAllGiaoVien();
    }

    // Tìm kiếm
    @GetMapping("/search")
    public List<Map<String, Object>> searchGiaoVien(
            @RequestParam String keyword) {
        return giaoVienService.searchGiaoVien(keyword);
    }

    // Lấy theo mã
    @GetMapping("/{maGV}")
    public Map<String, Object> getGiaoVienById(
            @PathVariable String maGV) {
        return giaoVienService.getGiaoVienById(maGV);
    }

    // Thêm
    @PostMapping
    public ResponseEntity<GiaoVien> create(
            @RequestBody GiaoVien giaoVien) {

        return ResponseEntity.ok(
                giaoVienService.saveGiaoVien(giaoVien));
    }

    // Cập nhật
    @PutMapping("/{maGV}")
    public ResponseEntity<GiaoVien> update(
            @PathVariable String maGV,
            @RequestBody GiaoVien giaoVien) {

        giaoVien.setMaGV(maGV);

        return ResponseEntity.ok(
                giaoVienService.saveGiaoVien(giaoVien));
    }

    // Xóa
    @DeleteMapping("/{maGV}")
    public ResponseEntity<Void> delete(
            @PathVariable String maGV) {

        giaoVienService.deleteGiaoVien(maGV);

        return ResponseEntity.noContent().build();
    }
}