package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Woman;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesMenRepository;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenServiceImpl implements MenService {

//    private final MenRepository menRepository;
//    @Autowired
//    private WomenRepository womenRepository;
//
//    @Autowired
//    public MenServiceImpl(MenRepository menRepository) {
//        this.menRepository = menRepository;
//    }

    private final MenRepository menRepository;
    private final PreferencesMenRepository preferencesMenRepository;
    private final WomenRepository womenRepository;

    @Autowired
    public MenServiceImpl(MenRepository menRepository, PreferencesMenRepository preferencesMenRepository, WomenRepository womenRepository) {
        this.menRepository = menRepository;
        this.preferencesMenRepository = preferencesMenRepository;
        this.womenRepository = womenRepository;
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

    //@Override
    public List<Woman> findMatchesByMenPreferences(int menId) {
        Men men = menRepository.findById(menId).orElseThrow(() -> new ResourceNotFoundException("Men not found"));
        PreferencesMen preferences = preferencesMenRepository.findByMenId(menId).orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));

        System.out.println("1 " +menId);
        System.out.println("2 " +preferences.getMen());
        System.out.println("2 " +preferences.getPreferredCommunity());
        System.out.println("2 " +preferences.getHandkerchiefOrWig());
        System.out.println("3 " + men.getPreferences());
        List<Woman> allWomen = womenRepository.findAll();
        System.out.println("4 " +allWomen);

        return allWomen.stream()
                .filter(woman -> matchesPreferences(preferences, woman))
                .collect(Collectors.toList());
    }

    private boolean matchesPreferences(PreferencesMen preferences, Woman woman) {


        boolean regionMatch = preferences.getPreferredRegion().isEmpty() || woman.getLocation().isEmpty() || isRegionMatch(preferences, woman);

        return (regionMatch || preferences.getPreferredRegion().isEmpty() || woman.getLocation().isEmpty()) &&
                (preferences.getPreferredCommunity().isEmpty() || preferences.getPreferredCommunity().contains(woman.getCommunity())) &&
                (preferences.getPreferredStatus().isEmpty() || preferences.getPreferredStatus().equals(woman.getStatus())) &&
                (preferences.getPreferredAgeFrom() == 0 || preferences.getPreferredAgeTo() == 0 ||
                        (woman.getAge() >= preferences.getPreferredAgeFrom() && woman.getAge() <= preferences.getPreferredAgeTo())) &&
                (preferences.getPreferredHeightFrom() == 0 || preferences.getPreferredHeightTo() == 0 ||
                        (woman.getHeight() >= preferences.getPreferredHeightFrom() && woman.getHeight() <= preferences.getPreferredHeightTo())) &&
                (preferences.getKosherOrNonKosherDevice().isEmpty() || preferences.getKosherOrNonKosherDevice().equals(woman.getDevice())) &&
                (preferences.getPreferredStyle() == null || preferences.getPreferredStyle().isEmpty() || preferences.getPreferredStyle().equals(woman.getStyle())) &&
                (preferences.getHandkerchiefOrWig().isEmpty() || preferences.getHandkerchiefOrWig().equals(woman.getHeadCovering()));
    }

    public boolean isRegionMatch(PreferencesMen preferences, Woman woman){

            // פיצול העיר והאזור לשני חלקים בטבלת MEN
            String[] womanLocationParts = woman.getLocation().split(",");
            String womanCity = womanLocationParts[0].trim();
            String womanRegion = womanLocationParts.length > 1 ? womanLocationParts[1].trim() : "";

            // פיצול העיר והאזור בטבלת העדפות לשני חלקים
            String[] preferredRegionParts = preferences.getPreferredRegion().split(",");
            String preferredCity = preferredRegionParts[0].trim();
            String preferredRegion = preferredRegionParts.length > 1 ? preferredRegionParts[1].trim() : "";

        return preferredCity.equals(womanCity) || preferredCity.equals(womanRegion) ||
                preferredRegion.equals(womanCity) || preferredRegion.equals(womanRegion);
    }
}
