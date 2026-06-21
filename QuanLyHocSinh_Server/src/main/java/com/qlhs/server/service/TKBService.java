package com.qlhs.server.service;

import com.qlhs.server.entity.TKB;
import com.qlhs.server.repository.TKBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TKBService {
    @Autowired
    private TKBRepository tkbRepository;

    public List<TKB> getAllTKB() {
        return tkbRepository.findAll();
    }
    public Optional<TKB> getByIdTKB(String maTKB){
        return tkbRepository.findById(maTKB);
    }
    public List<TKB> getByMaLop(String maLop){
        return tkbRepository.findByMaLop(maLop);
    }
    public List<TKB> getByMaMH(String maMH){
        return tkbRepository.findByMaMH(maMH);
    }
    public List<TKB> getByMaPH(String maPH){
        return tkbRepository.findByMaPH(maPH);
    }
    public List<TKB> getByMaGV(String maGV){
        return tkbRepository.findByMaGV(maGV);
    }
    public TKB save(TKB tkb){
        return tkbRepository.save(tkb);
    }
    public void delete(String maTKB){
        tkbRepository.deleteById(maTKB);
    }
    public boolean existsByIdTKB(String maTKB){
        return tkbRepository.existsById(maTKB);
    }
}
