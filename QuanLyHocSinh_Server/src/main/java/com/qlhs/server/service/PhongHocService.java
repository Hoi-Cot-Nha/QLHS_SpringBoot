package com.qlhs.server.service;

import com.qlhs.server.entity.PhongHoc;
import com.qlhs.server.repository.PhongHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhongHocService {
    @Autowired
    private PhongHocRepository phongHocRepository;

    public List<PhongHoc> getAllPH() {
        return phongHocRepository.findAll();
    }
    public Optional<PhongHoc> getByIdPH(String maPH){
        return phongHocRepository.findById(maPH);
    }
    public PhongHoc save(PhongHoc phongHoc){
        return phongHocRepository.save(phongHoc);
    }
    public void delete(String maPH){
        phongHocRepository.deleteById(maPH);
    }
    public boolean existsPH(String maPH){
        return phongHocRepository.existsById(maPH);
    }
}
