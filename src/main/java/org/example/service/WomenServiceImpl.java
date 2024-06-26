package org.example.service;


import org.example.model.Woman;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WomenServiceImpl implements WomenService {
    private final WomenRepository womenRepository;

    @Autowired
    public WomenServiceImpl(WomenRepository womenRepository) {
        this.womenRepository = womenRepository;
    }

    public List<Woman> getAllWomen() {
        return womenRepository.findAll();
    }

    public Woman getWomanById(int id) {
        return womenRepository.findById(id).orElse(null);
    }

    public Woman addWoman(Woman woman) {
        //System.out.println(woman);
        return womenRepository.save(woman);
    }
}
