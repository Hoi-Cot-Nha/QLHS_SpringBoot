package com.qlhs.server.service;

import com.qlhs.server.entity.PhucKhao;
import com.qlhs.server.repository.PhucKhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhucKhaoService {

    @Autowired
    private PhucKhaoRepository phucKhaoRepository;

    public List<PhucKhao> getAllPK(){
        return phucKhaoRepository.findAll();
    }

    public Optional<PhucKhao> getByIdPK(int maPK){
        return phucKhaoRepository.findById(maPK);
    }

    public List<PhucKhao> search(String keyword){
        return phucKhaoRepository.searchByKeyword(keyword);
    }

    public PhucKhao savePK(PhucKhao phucKhao){
        return phucKhaoRepository.save(phucKhao);
    }

    public void deletePK(int maPK){
        phucKhaoRepository.deleteById(maPK);
    }

    public boolean existsPK(int maPK){
        return phucKhaoRepository.existsById(maPK);
    }

}