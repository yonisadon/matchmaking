package org.example.service;


import org.example.model.Woman;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    @Override
//    public List<Woman> searchWomen(String firstName, Integer age, String location, String lastName, String status, String style) {
//        return womenRepository.findByFirstNameOrAgeOrLocationOrLastNameOrStatusOrStyle(firstName, age, location, lastName, status, style);
//    }

    @Override
    public List<Woman> searchWomen(String term, String criteria) {
        switch (criteria) {
            case "firstName":
                return womenRepository.findByFirstName(term);
            case "age":
                return womenRepository.findByAge(Integer.parseInt(term));
            case "location":
                return womenRepository.findByLocation(term);
            case "lastName":
                return womenRepository.findByLastName(term);
            case "status":
                return womenRepository.findByStatus(term);
            case "style":
                return womenRepository.findByStyle(term);
            case "height":
                return womenRepository.findByHeight(term);
            default:
                return new ArrayList<>();
        }
    }
}
