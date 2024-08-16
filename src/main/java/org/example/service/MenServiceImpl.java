package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Women;
import org.example.model.Women;
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
                if (term != null && !term.trim().isEmpty()) {
                    return menRepository.findByFirstNameContainingIgnoreCase(term);
                } else {
                   // return new ArrayList<>(); // או תוצאה ריקה אחרת במקרה שה-term ריק או null
                    return menRepository.findByFirstName(term);
                }
                //System.out.println(term);
                //return menRepository.findByFirstName(term);
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
        Men existingMen = menRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Men not found"));

//        if (updateMen.getAge() <= 0 || updateMen.getAge() > 120) {
//            throw new IllegalArgumentException("Age must be between 1 and 120");
//        }
        // עדכון השדות
        existingMen.setAge(updateMen.getAge());
        existingMen.setFirstName(updateMen.getFirstName());
        existingMen.setLastName(updateMen.getLastName());
        existingMen.setHeight(updateMen.getHeight());
        existingMen.setStatus(updateMen.getStatus());
        existingMen.setLocation(updateMen.getLocation());
        return menRepository.save(existingMen);
    }

    public List<Women> findMatchesByMenPreferences(int menId) {
        Men men = menRepository.findById(menId).orElseThrow(() -> new ResourceNotFoundException("Men not found"));
        PreferencesMen preferences = preferencesMenRepository.findByMenId(menId).orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));
        System.out.println("menId: " + menId);
        List<Women> allWomen = womenRepository.findAll();
        return allWomen.stream()
                .filter(woman -> matchesPreferences(preferences, woman))
                .collect(Collectors.toList());
    }

    private boolean matchesPreferences(PreferencesMen preferences, Women women) {
        return  isRegionMatch(preferences, women) &&
                isCommunityMatch(preferences, women) &&
                isStatusMatch(preferences, women) &&
                isAgeMatch(preferences, women) &&
                isHeightMatch(preferences, women) &&
                isDeviceMatch(preferences, women) &&
                isStyleMatch(preferences, women) &&
                isHeadCoveringMatch(preferences, women);
    }

    private boolean isRegionMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredRegion().isEmpty() || women.getLocation().isEmpty()) {
            return true; // Ignore if either is empty
        }
        String[] womanLocationParts = women.getLocation().split("-");
        String womanCity = womanLocationParts[0].trim();
        String womanRegion = womanLocationParts.length > 1 ? womanLocationParts[1].trim() : "";

        String[] preferredRegionParts = preferences.getPreferredRegion().split("-");
        String preferredCity = preferredRegionParts[0].trim();
        String preferredRegion = preferredRegionParts.length > 1 ? preferredRegionParts[1].trim() : "";

        return preferredCity.contains(womanCity) || preferredCity.contains(womanRegion) ||
                preferredRegion.contains(womanCity) || preferredRegion.contains(womanRegion);
    }

    private boolean isCommunityMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredCommunity().isEmpty() || women.getCommunity().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredCommunity().contains(women.getCommunity());
    }

    private boolean isStatusMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredStatus().isEmpty() || women.getStatus().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStatus().equals(women.getStatus());
    }

    private boolean isAgeMatch(PreferencesMen preferences, Women women) {
//        if (preferences.getPreferredAgeFrom() == 0 || preferences.getPreferredAgeTo() == 0) {
//            return true; // Ignore if either is empty
//        }
        int[] ageRange = {preferences.getPreferredAgeFrom(), preferences.getPreferredAgeTo()};
        if (ageRange[0] == 0 || ageRange[1] == 0 || ageRange[0] > ageRange[1]) {
            return true;
        }
        //return women.getAge() >= preferences.getPreferredAgeFrom() && women.getAge() <= preferences.getPreferredAgeTo();
        return women.getAge() >= ageRange[0] && women.getAge() <= ageRange[1];
    }

    private boolean isHeightMatch(PreferencesMen preferences, Women women) {
//        if (preferences.getPreferredHeightFrom() == 0 || preferences.getPreferredHeightTo() == 0) {
//            return true; // Ignore if either is empty
//        }
        int[] heightRange = {preferences.getPreferredAgeFrom(), preferences.getPreferredAgeTo()};
        if (heightRange[0] == 0 || heightRange[1] == 0 || heightRange[0] > heightRange[1]) {
            return true;
        }
        return women.getAge() >= heightRange[0] && women.getAge() <= heightRange[1];
        //return women.getHeight() >= preferences.getPreferredHeightFrom() && women.getHeight() <= preferences.getPreferredHeightTo();
    }

    private boolean isDeviceMatch(PreferencesMen preferences, Women women) {
        if (preferences.getKosherOrNonKosherDevice().isEmpty() || women.getDevice().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getKosherOrNonKosherDevice().equals(women.getDevice());
    }

    private boolean isStyleMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredStyle() == null || preferences.getPreferredStyle().isEmpty() || women.getStyle().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStyle().equals(women.getStyle());
    }

    private boolean isHeadCoveringMatch(PreferencesMen preferences, Women women) {
        if (preferences.getHandkerchiefOrWig().isEmpty() || women.getHeadCovering().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getHandkerchiefOrWig().equals(women.getHeadCovering());
    }
}

