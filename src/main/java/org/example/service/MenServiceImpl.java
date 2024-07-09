package org.example.service;

import org.example.model.Men;
import org.example.repository.MenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenServiceImpl implements MenService {

    private final MenRepository menRepository;

    @Autowired
    public MenServiceImpl(MenRepository menRepository) {
        this.menRepository = menRepository;
    }

    @Override
    public List<Men> getAllMen() {
        return menRepository.findAll();
    }

    @Override
    public Men getMenById(int id) {
        return menRepository.findById(id).orElse(null);
    }

    @Override
    public Men addMen(Men men) {
        return menRepository.save(men);
    }

    public Men deleteMen(int id) {
        Optional<Men> men = menRepository.findById(id);
        if (men.isPresent()) {
            menRepository.deleteById(id);
            return men.get();
        } else {
            throw new EntityNotFoundException("men with ID " + id + " not found.");
        }
    }

//    @Override
//    public List<Men> searchMen(String firstName, Integer age, String location, String lastName, String status, String style) {
//        return menRepository.findByFirstNameOrAgeOrLocationOrLastNameOrStatusOrStyle(firstName, age, location, lastName, status, style);
//    }
@Override
public List<Men> searchMen(String term, String criteria) {
    switch (criteria) {
        case "firstName":
            //System.out.println(term);
            return menRepository.findByFirstName(term);
        case "age":
            return menRepository.findByAge(Integer.parseInt(term));
        case "location":
            return menRepository.findByLocation(term);
        case "lastName":
            return menRepository.findByLastName(term);
        case "status":
            return menRepository.findByStatus(term);
        case "style":
            return menRepository.findByStyle(term);
        case "height":
            return menRepository.findByHeight(Float.parseFloat(term));
        default:
            return new ArrayList<>();
    }
}
}
