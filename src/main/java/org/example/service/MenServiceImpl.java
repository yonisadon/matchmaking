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

    private final MenRepository menRepository;
    private final PreferencesMenRepository preferencesMenRepository;
    private final WomenRepository womenRepository;

    //constructor
    @Autowired
    public MenServiceImpl(MenRepository menRepository, PreferencesMenRepository preferencesMenRepository, WomenRepository womenRepository) {
        this.menRepository = menRepository;
        this.preferencesMenRepository = preferencesMenRepository;
        this.womenRepository = womenRepository;
    }

    //service, Displays all existing rows from the men table.
    public List<Men> getAllMen() {
        List<Men> menList = menRepository.findAll();
        System.out.println("Number of records found: " + menList.size());
        return menList;
    }

    //service, Retrieving a record by ID card from the men's register, and presenting the details in an update form
    public Men getMenById(int id) {
        return menRepository.findById(id).orElse(null);
    }

    //Saving a new record in the table of men.
    public Men addMen(Men men) {
        return menRepository.save(men);
    }

    //deleting row in the table of men and table of preferencesMen by id from the men table.
    public Men deleteMen(int id) {
        Optional<Men> men = menRepository.findById(id);
        if (men.isPresent()) {
            menRepository.deleteById(id);
            return men.get();
        } else {
            throw new EntityNotFoundException("men with ID " + id + " not found.");
        }
    }

    //Search by any condition (first name or age...) from the table of men, after clicking in search button.
    @Override
    public List<Men> searchMen(String term, String criteria) {
        List<Men> results = new ArrayList<>();
        switch (criteria) {
            case "firstName":
                if (term != null && !term.trim().isEmpty()) {
                    results = menRepository.findByFirstNameContainingIgnoreCase(term);
                }
                break;
            case "age":
                results = menRepository.findByAge(Integer.parseInt(term));
                break;
            case "location":
                results = menRepository.findByLocation(term);
                break;
            case "lastName":
                results = menRepository.findByLastNameContainingIgnoreCase(term);
                break;
            case "status":
                results = menRepository.findByStatus(term);
                break;
            case "style":
                results = menRepository.findByStyle(term);
                break;
            case "height":
                results = menRepository.findByHeight(Float.parseFloat(term));
                break;
            default:
                break;
        }
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("לא נמצאה רשומה לפי החיפוש.");
        }
        return results;
    }

    //update row in the table of men, by id.
    public Men updateMen(int id, Men updateMen) {
        Men existingMen = menRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("לא נמצא"));

//        if (updateMen.getAge() <= 0 || updateMen.getAge() > 120) {
//            throw new IllegalArgumentException("Age must be between 1 and 120");
//        }
        // עדכון השדות
        existingMen.setDevice(updateMen.getDevice());
        existingMen.setCommunity(updateMen.getCommunity());
        existingMen.setHeadCovering(updateMen.getHeadCovering());
        existingMen.setStyle(updateMen.getStyle());
        existingMen.setDateOfBirth(updateMen.getDateOfBirth());
        existingMen.setAge(updateMen.getAge());
        existingMen.setFirstName(updateMen.getFirstName());
        existingMen.setLastName(updateMen.getLastName());
        existingMen.setHeight(updateMen.getHeight());
        existingMen.setStatus(updateMen.getStatus());
        existingMen.setLocation(updateMen.getLocation());
        existingMen.setSeeking(updateMen.getSeeking());
        return menRepository.save(existingMen);
    }

    //search matches by id in the table of men.
    public List<Women> findMatchesByMenPreferences(int menId) {
        Men men = menRepository.findById(menId).orElseThrow(() -> new ResourceNotFoundException("לא נמצא"));
        PreferencesMen preferences = preferencesMenRepository.findByMenId(menId).orElseThrow(() -> new ResourceNotFoundException("Preferences not found"));
        System.out.println("menId: " + menId);
        List<Women> allWomen = womenRepository.findAll();
        return allWomen.stream()
                .filter(woman -> matchesPreferences(preferences, woman))
                .collect(Collectors.toList());
    }
    //checks all the parameters, if everything is correct,
    // then the corresponding record will be displayed in the table on the front screen (table on the lower right side),
    //note: There can be several matching records.
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

    //Checks the region parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
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

    //Checks the community parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isCommunityMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredCommunity().isEmpty() || women.getCommunity().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredCommunity().contains(women.getCommunity());
    }

    //Checks the status parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isStatusMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredStatus().isEmpty() || women.getStatus().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStatus().equals(women.getStatus());
    }

    //Checks the age parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isAgeMatch(PreferencesMen preferences, Women women) {
        int[] ageRange = {preferences.getPreferredAgeFrom(), preferences.getPreferredAgeTo()};
        if (ageRange[0] == 0 || ageRange[1] == 0 || ageRange[0] > ageRange[1]) {
            return true;
        }
        //return women.getAge() >= preferences.getPreferredAgeFrom() && women.getAge() <= preferences.getPreferredAgeTo();
        return women.getAge() >= ageRange[0] && women.getAge() <= ageRange[1];
    }

    //Checks the height parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isHeightMatch(PreferencesMen preferences, Women women) {
        float[] heightRange = {preferences.getPreferredHeightFrom(), preferences.getPreferredHeightTo()};
        if (heightRange[0] == 0 || heightRange[1] == 0 || heightRange[0] > heightRange[1]) {
            return true;
        }
        return women.getHeight() >= heightRange[0] && women.getHeight() <= heightRange[1];
    }

    //Checks the device parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isDeviceMatch(PreferencesMen preferences, Women women) {
        if (preferences.getKosherOrNonKosherDevice().isEmpty() || women.getDevice().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getKosherOrNonKosherDevice().equals(women.getDevice());
    }

    //Checks the style parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isStyleMatch(PreferencesMen preferences, Women women) {
        if (preferences.getPreferredStyle() == null || preferences.getPreferredStyle().isEmpty() || women.getStyle().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getPreferredStyle().equals(women.getStyle());
    }

    //Checks the head covering parameter, between the man's preferences and the records that exist in the woman's table,
    // if a match is found returns a match found, if no match is found returns no match found,
    //If one/both of the fields have no data, will be return true , and there will be no check on this parameter.
    private boolean isHeadCoveringMatch(PreferencesMen preferences, Women women) {
        if (preferences.getHandkerchiefOrWig().isEmpty() || women.getHeadCovering().isEmpty()) {
            return true; // Ignore if either is empty
        }
        return preferences.getHandkerchiefOrWig().equals(women.getHeadCovering());
    }
}

