package com.qlhs.server.restControl;

import com.qlhs.server.entity.TKB;
import com.qlhs.server.service.TKBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tkb")
public class TKBRestController {
    @Autowired
    private TKBService tkbService;

    @GetMapping
    public List<TKB>  getAllTKB() {
        return tkbService.getAllTKB();
    }

    @GetMapping("/{maKB}")
    public ResponseEntity<TKB> getByIdTKB(@PathVariable String maTKB) {
        return tkbService.getByIdTKB(maTKB)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lop/{maLop}")
    public List<TKB> getByMaLop(@PathVariable String maLop) {
        return tkbService.getByMaLop(maLop);
    }

    @GetMapping("/monhoc/{maMH}")
    public List<TKB> getByMaMH(@PathVariable String maMH) {
        return tkbService.getByMaMH(maMH);
    }

    @GetMapping("/phong/{maPH}")
    public List<TKB> getByMaPH(@PathVariable String maPH) {
        return tkbService.getByMaPH(maPH);
    }

    @GetMapping("/giaovien/{MaGV}")
    public List<TKB> getByMaGV(@PathVariable String maGV) {
        return tkbService.getByMaGV(maGV);
    }

    @PostMapping
    public ResponseEntity<TKB> create(@RequestBody TKB tkb) {
        if (tkbService.existsByIdTKB(tkb.getMaTKB())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tkbService.save(tkb));
    }

    @PutMapping("/{maTKB}")
    public ResponseEntity<TKB>  update(@PathVariable String maTKB, @RequestBody TKB tkb) {
        if (!tkbService.existsByIdTKB(maTKB)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tkbService.save(tkb));
    }

    @DeleteMapping("/{maTKB}")
    public ResponseEntity<TKB> delete(@PathVariable String maTKB) {
        if (!tkbService.existsByIdTKB(maTKB)) {
            return ResponseEntity.notFound().build();
        }
        tkbService.delete(maTKB);
        return ResponseEntity.ok().build();
    }
}
