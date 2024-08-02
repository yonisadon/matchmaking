package org.example.service;


import org.example.model.PreferencesMen;
import org.example.repository.PreferencesMenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PreferencesServiceImpl {
    @Autowired
    private final PreferencesMenRepository preferencesMenRepository;

    public PreferencesServiceImpl(PreferencesMenRepository preferencesMenRepository) {
        this.preferencesMenRepository = preferencesMenRepository;
    }

    public PreferencesMen addPreferencesMen(PreferencesMen preferencesMen) {
        return preferencesMenRepository.save(preferencesMen);

    }
//    public List<PreferencesMen> getPreferencesMen(int id) {
//        List<PreferencesMen> preferencesMenList = preferencesMenRepository.findAll();
//        System.out.println(preferencesMenList);
//        System.out.println("Number of records found: " + preferencesMenList.size());
//        return preferencesMenList;
//    }

    //@Override
    public PreferencesMen getPreferencesMenByMenId(int menId) {
        System.out.println(menId);
        System.out.println(preferencesMenRepository);
        return preferencesMenRepository.findByMenId(menId)
                .orElseThrow(() -> new EntityNotFoundException("Preferences for menId " + menId + " not found"));
    }

}
