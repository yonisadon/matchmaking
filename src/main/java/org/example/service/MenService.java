package org.example.service;

import org.example.model.Men;
import org.example.repository.MenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenService {

    private final MenRepository menRepository;

    @Autowired
    public MenService(MenRepository menRepository) {
        this.menRepository = menRepository;
    }

    public List<Men> getAllMen() {
        return menRepository.findAll();
    }

    public Men getMenById(int id) {
        return menRepository.findById(id).orElse(null);
    }

    public Men addMen(Men men) {
        return menRepository.save(men);
    }
}
