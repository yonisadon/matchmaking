package org.example.service;


import org.example.exception.ResourceNotFoundException;
import org.example.model.*;
import org.example.model.Women;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesWomenRepository;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WomenServiceImpl implements WomenService {
    private final WomenRepository womenRepository;
    private final PreferencesWomenRepository preferencesWomenRepository;
    private final MenRepository menRepository;
    @Autowired
    public WomenServiceImpl(WomenRepository womenRepository,PreferencesWomenRepository preferencesWomenRepository, MenRepository menRepository ) {
        this.womenRepository = womenRepository;
        this.preferencesWomenRepository = preferencesWomenRepository;
        this.menRepository = menRepository;
    }

    public List<Women> getAllWomen() {
        return womenRepository.findAll();
    }

    public Women getWomanById(int id) {
        return womenRepository.findById(id).orElse(null);
    }

    public Women addWomen(Women women) {
        //System.out.println(woman);
        return womenRepository.save(women);
    }

    public Women deleteWomen(int id) {
        Optional<Women> women = womenRepository.findById(id);
        if (women.isPresent()) {
            womenRepository.deleteById(id);
            return women.get();
        } else {
            throw new EntityNotFoundException("Woman with ID " + id + " not found.");
        }
    }

//    @Override
//    public List<Woman> searchWomen(String firstName, Integer age, String location, String lastName, String status, String style) {
//        return womenRepository.findByFirstNameOrAgeOrLocationOrLastNameOrStatusOrStyle(firstName, age, location, lastName, status, style);
//    }

    @Override
    public List<Women> searchWomen(String term, String criteria) {
        switch (criteria) {
            case "firstName":
                if (term != null && !term.trim().isEmpty()) {
                    return womenRepository.findByFirstNameContainingIgnoreCase(term);
                } else {
                    // return new ArrayList<>(); // או תוצאה ריקה אחרת במקרה שה-term ריק או null
                    return womenRepository.findByFirstName(term);
                }
                //return womenRepository.findByFirstName(term);
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
                return womenRepository.findByHeight(Float.parseFloat(term));
            default:
                return new ArrayList<>();
        }
    }

    public Women updateWoman(int id, Women updatedWoman) {
        Women existingWoman = womenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Woman not found"));

        // עדכון השדות
        existingWoman.setDevice(updatedWoman.getDevice());
        existingWoman.setCommunity(updatedWoman.getCommunity());
        existingWoman.setHeadCovering(updatedWoman.getHeadCovering());
        existingWoman.setStyle(updatedWoman.getStyle());
        existingWoman.setDateOfBirth(updatedWoman.getDateOfBirth());
        existingWoman.setAge(updatedWoman.getAge());
        existingWoman.setFirstName(updatedWoman.getFirstName());
        existingWoman.setLastName(updatedWoman.getLastName());
        existingWoman.setHeight(updatedWoman.getHeight());
        existingWoman.setStatus(updatedWoman.getStatus());
        existingWoman.setLocation(updatedWoman.getLocation());
        existingWoman.setSeeking(updatedWoman.getSeeking());

        return womenRepository.save(existingWoman);
    }

    public List<Men> findMatchesByWomenPreferences(int womenId) {
        Women women = womenRepository.findById(womenId).orElseThrow(() -> new ResourceNotFoundException("Women not found"));
        PreferencesWomen preferences = preferencesWomenRepository.findByWomenId(womenId).orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));
        System.out.println("womenId: " + womenId);
        List<Men> allMen = menRepository.findAll();
        return allMen.stream()
                .filter(men -> matchesPreferences(preferences, men))
                .collect(Collectors.toList());
    }

    private boolean matchesPreferences(PreferencesWomen preferences, Men men) {
        //return true;
        return  isRegionMatch(preferences, men) &&
                isCommunityMatch(preferences, men) &&
                isStatusMatch(preferences, men) &&
                isAgeMatch(preferences, men) &&
                isHeightMatch(preferences, men) &&
                isDeviceMatch(preferences, men) &&
                isStyleMatch(preferences, men) &&
                isHeadCoveringMatch(preferences, men);
    }

    private boolean isRegionMatch(PreferencesWomen preferencesWomen, Men men){
        if (preferencesWomen.getPreferredRegion().isEmpty() || men.getLocation().isEmpty()){
            return true;
        }
        String[] menLocationParts = men.getLocation().split("-");
        String menCity = menLocationParts[0].trim();
        String menRegion = menLocationParts.length > 1 ? menLocationParts[1].trim() : "";

        String[] preferredRegionParts = preferencesWomen.getPreferredRegion().split("-");
        String preferredCity = preferredRegionParts[0].trim();
        String preferredRegion = preferredRegionParts.length > 1 ? preferredRegionParts[1].trim() : "";

        return preferredCity.contains(menCity) || preferredCity.contains(menRegion) ||
                preferredRegion.contains(menCity) || preferredRegion.contains(menRegion);
    }

    private boolean isCommunityMatch(PreferencesWomen preferences, Men men) {
        if (preferences.getPreferredCommunity().isEmpty() || men.getCommunity().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredCommunity().contains(men.getCommunity());
    }

    private boolean isStatusMatch(PreferencesWomen preferences, Men men) {
        if (preferences.getPreferredStatus().isEmpty() || men.getStatus().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStatus().equals(men.getStatus());
    }

    private boolean isAgeMatch(PreferencesWomen preferences, Men men) {
        int[] ageRange = {preferences.getPreferredAgeFrom(), preferences.getPreferredAgeTo()};
        if (ageRange[0] == 0 || ageRange[1] == 0 || ageRange[0] > ageRange[1]) {
            return true;
        }
        return men.getAge() >= ageRange[0] && men.getAge() <= ageRange[1];
    }

    private boolean isHeightMatch(PreferencesWomen preferences, Men men){
        float[] heightRange = {preferences.getPreferredHeightFrom(), preferences.getPreferredHeightTo()};
        if (heightRange[0] == 0 || heightRange[1] == 0 || heightRange[0] > heightRange[1]) {
            return true;
        }
        return men.getHeight() >= heightRange[0] && men.getHeight() <= heightRange[1];
    }

    private boolean isDeviceMatch(PreferencesWomen preferences, Men men) {
        if (preferences.getKosherOrNonKosherDevice().isEmpty() || men.getDevice().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getKosherOrNonKosherDevice().equals(men.getDevice());
    }

    private boolean isStyleMatch(PreferencesWomen preferences, Men men) {
        if (preferences.getPreferredStyle() == null || preferences.getPreferredStyle().isEmpty() || men.getStyle().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStyle().equals(men.getStyle());
    }

    private boolean isHeadCoveringMatch(PreferencesWomen preferences, Men men) {
        if (preferences.getHandkerchiefOrWig().isEmpty() || men.getHeadCovering().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getHandkerchiefOrWig().equals(men.getHeadCovering());
    }
}
