package com.qlhs.server.restControl;

import com.qlhs.server.entity.Lop;
import com.qlhs.server.service.LopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lop")
public class LopRestController {

    @Autowired
    private LopService lopService;

    // Lấy tất cả
    @GetMapping
    public List<Map<String, Object>> getAllLop() {
        return lopService.getAllLop();
    }

    // Tìm kiếm
    @GetMapping("/search")
    public List<Map<String, Object>> searchLop(
            @RequestParam String keyword) {
        return lopService.searchLop(keyword);
    }

    // Lấy theo mã
    @GetMapping("/{maLop}")
    public Map<String, Object> getLopById(
            @PathVariable String maLop) {
        return lopService.getLopById(maLop);
    }

    // Thêm
    @PostMapping
    public ResponseEntity<Lop> create(
            @RequestBody Lop lop) {

        return ResponseEntity.ok(
                lopService.saveLop(lop));
    }

    // Cập nhật
    @PutMapping("/{maLop}")
    public ResponseEntity<Lop> update(
            @PathVariable String maLop,
            @RequestBody Lop lop) {

        lop.setMaLop(maLop);

        return ResponseEntity.ok(
                lopService.saveLop(lop));
    }

    // Xóa
    @DeleteMapping("/{maLop}")
    public ResponseEntity<Void> delete(
            @PathVariable String maLop) {

        lopService.deleteLop(maLop);

        return ResponseEntity.noContent().build();
    }
}