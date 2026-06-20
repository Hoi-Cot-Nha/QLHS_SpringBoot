package com.qlhs.server.service;

import com.qlhs.server.entity.Diem;
import com.qlhs.server.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiemService {

    @Autowired
    private DiemRepository diemRepository;

    public List<Map<String, Object>> getAllDiem() {
        return diemRepository.findAllDiemWithDetails();
    }

    public List<Map<String, Object>> getDiemByFilter(String maLop, String maMH, int hocKy) {
        return diemRepository.findDiemByFilter(maLop, maMH, hocKy);
    }

    public List<Map<String, Object>> searchDiem(String keyword) {
        return diemRepository.searchDiem(keyword);
    }

    public List<Map<String, Object>> getDiemByMaHS(String maHS) {
        return diemRepository.findDiemByMaHSWithDetails(maHS);
    }

    public List<Integer> getDistinctHocKy() {
        return diemRepository.findDistinctHocKy();
    }

    public Diem saveDiem(Diem diem) {
        return diemRepository.save(diem);
    }
}
