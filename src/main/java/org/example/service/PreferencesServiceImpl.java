package org.example.service;


import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.repository.PreferencesMenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
