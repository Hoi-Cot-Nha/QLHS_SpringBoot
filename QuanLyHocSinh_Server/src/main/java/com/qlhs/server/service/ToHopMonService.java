package com.qlhs.server.service;

import com.qlhs.server.entity.ToHopMon;
import com.qlhs.server.repository.ToHopMonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ToHopMonService {

    @Autowired
    private ToHopMonRepository toHopMonRepository;

    // Lấy tất cả tổ hợp môn
    public List<Map<String, Object>> getAllToHopMon() {
        return toHopMonRepository.findAllToHopMon();
    }

    // Tìm kiếm tổ hợp môn
    public List<Map<String, Object>> searchToHopMon(String keyword) {
        return toHopMonRepository.searchToHopMon(keyword);
    }

    // Lấy theo mã
    public List<Map<String, Object>> getToHopMonById(String maToHop) {
        return toHopMonRepository.findByMaToHop(maToHop);
    }

    // Thêm hoặc cập nhật
    public ToHopMon saveToHopMon(ToHopMon toHopMon) {
        return toHopMonRepository.save(toHopMon);
    }

    // Xóa
    public void deleteToHopMon(String maToHop) {
        toHopMonRepository.deleteById(maToHop);
    }

    // Kiểm tra mã tồn tại
    public boolean existsToHopMon(String maToHop) {
        return toHopMonRepository.existsById(maToHop);
    }

    // Lấy danh sách tên tổ hợp môn
    public List<String> getDistinctTenToHop() {
        return toHopMonRepository.findDistinctTenToHop();
    }
}