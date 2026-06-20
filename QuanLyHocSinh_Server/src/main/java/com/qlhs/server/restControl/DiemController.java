package com.qlhs.server.restControl;

import com.qlhs.server.entity.Diem;
import com.qlhs.server.service.DiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diem")
public class DiemController {

    @Autowired
    private DiemService diemService;

    @GetMapping
    public List<Map<String, Object>> getAllDiem() {
        return diemService.getAllDiem();
    }

    @GetMapping("/filter")
    public List<Map<String, Object>> getDiemByFilter(
            @RequestParam String maLop,
            @RequestParam String maMH,
            @RequestParam int hocKy) {
        return diemService.getDiemByFilter(maLop, maMH, hocKy);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchDiem(@RequestParam String keyword) {
        return diemService.searchDiem(keyword);
    }

    @GetMapping("/hocky")
    public List<Integer> getDistinctHocKy() {
        return diemService.getDistinctHocKy();
    }

    @GetMapping("/hocsinh/{maHS}")
    public List<Map<String, Object>> getDiemByMaHS(@PathVariable String maHS) {
        return diemService.getDiemByMaHS(maHS);
    }

    @PostMapping
    public ResponseEntity<Diem> createOrUpdateDiem(@RequestBody Diem diem) {
        return ResponseEntity.ok(diemService.saveDiem(diem));
    }
}
