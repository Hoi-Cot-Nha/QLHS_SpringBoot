package com.qlhs.server.service.Dat;

import com.qlhs.server.entity.Lop;
import com.qlhs.server.repository.Dat.LopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LopService {

    @Autowired
    private LopRepository lopRepository;

    // Lấy toàn bộ lớp
    public List<Map<String, Object>> getAllLop() {
        return lopRepository.findAllLopWithGVCN();
    }

    // Tìm kiếm lớp
    public List<Map<String, Object>> searchLop(String keyword) {
        return lopRepository.searchLop(keyword);
    }

    // Lấy theo mã lớp
    public Map<String, Object> getLopById(String maLop) {
        return lopRepository.findByMaLopWithGVCN(maLop);
    }

    // Thêm hoặc cập nhật
    public Lop saveLop(Lop lop) {
        return lopRepository.save(lop);
    }

    // Xóa
    public void deleteLop(String maLop) {
        lopRepository.deleteById(maLop);
    }
}