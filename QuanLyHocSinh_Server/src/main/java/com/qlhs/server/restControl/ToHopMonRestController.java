package com.qlhs.server.restControl;

import com.qlhs.server.entity.ToHopMon;
import com.qlhs.server.service.ToHopMonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tohopmon")
public class ToHopMonRestController {

    @Autowired
    private ToHopMonService toHopMonService;

    // Lấy tất cả
    @GetMapping
    public List<Map<String, Object>> getAllToHopMon() {
        return toHopMonService.getAllToHopMon();
    }

    // Tìm kiếm
    @GetMapping("/search")
    public List<Map<String, Object>> searchToHopMon(
            @RequestParam String keyword) {

        return toHopMonService.searchToHopMon(keyword);
    }

    // Lấy theo mã
    @GetMapping("/{maToHop}")
    public List<Map<String, Object>> getToHopMonById(
            @PathVariable String maToHop) {

        return toHopMonService.getToHopMonById(maToHop);
    }

    // Thêm
    @PostMapping
    public ResponseEntity<ToHopMon> create(
            @RequestBody ToHopMon toHopMon) {

        return ResponseEntity.ok(
                toHopMonService.saveToHopMon(toHopMon));
    }

    // Cập nhật
    @PutMapping("/{maToHop}")
    public ResponseEntity<ToHopMon> update(
            @PathVariable String maToHop,
            @RequestBody ToHopMon toHopMon) {

        toHopMon.setMaToHop(maToHop);

        return ResponseEntity.ok(
                toHopMonService.saveToHopMon(toHopMon));
    }

    // Xóa
    @DeleteMapping("/{maToHop}")
    public ResponseEntity<Void> delete(
            @PathVariable String maToHop) {

        toHopMonService.deleteToHopMon(maToHop);

        return ResponseEntity.noContent().build();
    }
}