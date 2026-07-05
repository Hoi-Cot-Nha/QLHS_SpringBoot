package com.qlhs.server.service.Dat;

import com.qlhs.server.entity.ToHopMon;
import com.qlhs.server.repository.Dat.ToHopMonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToHopMonService {

    @Autowired
    private ToHopMonRepository toHopMonRepository;

    // Lấy danh sách tất cả tổ hợp môn
    public List<ToHopMon> getAllToHopMon() {
        return toHopMonRepository.findAll();
    }

    // Lấy tổ hợp môn theo mã
    public Optional<ToHopMon> getByIdToHopMon(String maToHop) {
        return toHopMonRepository.findById(maToHop);
    }

    // Tìm kiếm theo mã hoặc tên
    public List<ToHopMon> search(String keyword) {
        return toHopMonRepository.findAll().stream()
                .filter(t -> t.getMaToHop().contains(keyword)
                        || t.getTenToHop().contains(keyword))
                .collect(java.util.stream.Collectors.toList());
    }

    // Thêm hoặc cập nhật
    public ToHopMon saveToHopMon(ToHopMon toHopMon) {
        return toHopMonRepository.save(toHopMon);
    }

    // Xóa theo mã
    public void deleteToHopMon(String maToHop) {
        toHopMonRepository.deleteById(maToHop);
    }

    // Kiểm tra mã đã tồn tại chưa
    public boolean existsToHopMon(String maToHop) {
        return toHopMonRepository.existsById(maToHop);
    }

    // Kiểm tra tên tổ hợp đã tồn tại chưa
    public boolean existsByTenToHop(String tenToHop) {
        return toHopMonRepository.findAll().stream()
                .anyMatch(t -> t.getTenToHop().equalsIgnoreCase(tenToHop));
    }
}