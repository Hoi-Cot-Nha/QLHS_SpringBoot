package com.qlhs.server.restControl;


import com.qlhs.server.entity.PhongHoc;
import com.qlhs.server.service.PhongHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phonghoc")
public class PhongHocRestController {
    @Autowired
    private PhongHocService phongHocService;

    @GetMapping
    public List<PhongHoc> getAllPH(){
        return phongHocService.getAllPH();
    }

    @GetMapping("/{MaPH}")
    public ResponseEntity<PhongHoc> getByIdPH(@PathVariable String maPH){
        return phongHocService.getByIdPH(maPH)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PhongHoc> create(@RequestBody PhongHoc phongHoc){
        if (phongHocService.existsPH(phongHoc.getMaPH())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(phongHocService.save(phongHoc));
    }

    @PutMapping("/{MaPH}")
    public ResponseEntity<PhongHoc> update(@PathVariable String maPH, @RequestBody PhongHoc phongHoc){
        if (!phongHocService.existsPH(maPH)){
            return ResponseEntity.notFound().build();
        }
        phongHoc.setMaPH(maPH);
        return ResponseEntity.ok(phongHocService.save(phongHoc));
    }

    @DeleteMapping("/{MaPH}")
    public ResponseEntity<PhongHoc> delete(@PathVariable String maPH){
        if (!phongHocService.existsPH(maPH)){
            return ResponseEntity.notFound().build();
        }
        phongHocService.delete(maPH);
        return ResponseEntity.ok().build();
    }
}
