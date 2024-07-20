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

    //@Override
    public List<Men> getAllMen() {
        List<Men> menList = menRepository.findAll();
        System.out.println("Number of records found: " + menList.size());
        return menList;
    }
    public Men getMenById(int id) {
        return menRepository.findById(id).orElse(null);
    }

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

    public Men updateMen(int id, Men updateMen) {
        System.out.println("db1");
        Men existingMen = menRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Men not found"));
        System.out.println("db2");

        // עדכון השדות
        existingMen.setAge(updateMen.getAge());
        existingMen.setFirstName(updateMen.getFirstName());
        existingMen.setLastName(updateMen.getLastName());
        existingMen.setHeight(updateMen.getHeight());
        existingMen.setStatus(updateMen.getStatus());
        existingMen.setLocation(updateMen.getLocation());
        System.out.println("db3");

        return menRepository.save(existingMen);

    }

//    public Optional<Men> findById(int id) {
//        return menRepository.findById(id);
//    }
}
