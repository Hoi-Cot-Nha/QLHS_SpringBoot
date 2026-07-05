package com.qlhs.server.restControl.Dat;

import com.qlhs.server.entity.ToHopMon;
import com.qlhs.server.service.Dat.ToHopMonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tohopmon")
public class ToHopMonRestController {

    @Autowired
    private ToHopMonService toHopMonService;

    @GetMapping
    public List<ToHopMon> getAllToHopMon() {
        return toHopMonService.getAllToHopMon();
    }

    @GetMapping("/{maToHop}")
    public ResponseEntity<ToHopMon> getByIdToHopMon(@PathVariable String maToHop) {
        return toHopMonService.getByIdToHopMon(maToHop)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<ToHopMon> search(@RequestParam(defaultValue = "") String keyword) {
        return toHopMonService.search(keyword);
    }

    @PostMapping
    public ResponseEntity<ToHopMon> createToHopMon(@RequestBody ToHopMon toHopMon) {

        // Kiểm tra mã đã tồn tại
        if (toHopMonService.existsToHopMon(toHopMon.getMaToHop())) {
            return ResponseEntity.status(409).body(null);
        }

        // Kiểm tra tên đã tồn tại
        if (toHopMonService.existsByTenToHop(toHopMon.getTenToHop())) {
            return ResponseEntity.status(422).body(null);
        }

        return ResponseEntity.ok(toHopMonService.saveToHopMon(toHopMon));
    }

    @PutMapping("/{maToHop}")
    public ResponseEntity<ToHopMon> updateToHopMon(
            @PathVariable String maToHop,
            @RequestBody ToHopMon toHopMon) {

        if (!toHopMonService.existsToHopMon(maToHop)) {
            return ResponseEntity.notFound().build();
        }

        toHopMon.setMaToHop(maToHop);

        return ResponseEntity.ok(toHopMonService.saveToHopMon(toHopMon));
    }

    @DeleteMapping("/{maToHop}")
    public ResponseEntity<Void> deleteToHopMon(@PathVariable String maToHop) {

        if (!toHopMonService.existsToHopMon(maToHop)) {
            return ResponseEntity.notFound().build();
        }

        toHopMonService.deleteToHopMon(maToHop);

        return ResponseEntity.ok().build();
    }
}