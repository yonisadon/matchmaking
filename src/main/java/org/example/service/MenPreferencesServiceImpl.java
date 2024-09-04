package org.example.service;


import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.repository.PreferencesMenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MenPreferencesServiceImpl {
    @Autowired
    private final PreferencesMenRepository preferencesMenRepository;

    public MenPreferencesServiceImpl(PreferencesMenRepository preferencesMenRepository) {
        this.preferencesMenRepository = preferencesMenRepository;
    }

    public PreferencesMen addPreferencesMen(PreferencesMen preferencesMen) {
        if (preferencesMen.getPreferredAgeFrom() > preferencesMen.getPreferredAgeTo() ||
                preferencesMen.getPreferredAgeFrom() == 0 && preferencesMen.getPreferredAgeTo() != 0 ||
                preferencesMen.getPreferredAgeFrom() != 0 && preferencesMen.getPreferredAgeTo() == 0) {
            throw new IllegalArgumentException("גיל ההתחלתי לא יכול להיות גדול מהגיל הסופי");
        }

        if (preferencesMen.getPreferredHeightFrom() > preferencesMen.getPreferredHeightTo() ||
                preferencesMen.getPreferredHeightFrom() == 0 && preferencesMen.getPreferredHeightTo() != 0 ||
                preferencesMen.getPreferredHeightFrom() != 0 && preferencesMen.getPreferredHeightTo() == 0) {
            throw new IllegalArgumentException("גובה התחלתי לא יכול להיות גדול מהגובה הסופי");
        }
        return preferencesMenRepository.save(preferencesMen);

    }
    //הצגת נתונים ובלחיצה על עדכן העדפות שליפה לפי menId
    public PreferencesMen getPreferencesMenByMenId(int menId) {
        System.out.println(menId);
        System.out.println(preferencesMenRepository);
        return preferencesMenRepository.findByMenId(menId)
                .orElseThrow(() -> new EntityNotFoundException("Preferences for menId " + menId + " not found"));
    }
    // update preferences from women and save
    public PreferencesMen getPreferencesMenByPreferencesId(int idPreferencesMen) {
        System.out.println(idPreferencesMen);
        System.out.println(preferencesMenRepository);
        return preferencesMenRepository.findByIdPreferencesMen(idPreferencesMen)
                .orElseThrow(() -> new EntityNotFoundException("Preferences for menId " + idPreferencesMen + " not found"));
    }

    public PreferencesMen updatePreferredMen(int menId, PreferencesMen updatePreferences) {
        System.out.println(menId);
        System.out.println(updatePreferences);
        PreferencesMen existingUpMen = preferencesMenRepository.findByIdPreferencesMen(menId).orElseThrow(() -> new EntityNotFoundException("preferences men not found "+ menId));

//        if (updateMen.getAge() <= 0 || updateMen.getAge() > 120) {
//            throw new IllegalArgumentException("Age must be between 1 and 120");
//        }
        // עדכון השדות
        existingUpMen.setPreferredRegion(updatePreferences.getPreferredRegion());
        existingUpMen.setPreferredCommunity(updatePreferences.getPreferredCommunity());
        existingUpMen.setHandkerchiefOrWig(updatePreferences.getHandkerchiefOrWig());
        existingUpMen.setPreferredStyle(updatePreferences.getPreferredStyle());
        existingUpMen.setKosherOrNonKosherDevice(updatePreferences.getKosherOrNonKosherDevice());
        existingUpMen.setPreferredStatus(updatePreferences.getPreferredStatus());
        existingUpMen.setPreferredAgeFrom(updatePreferences.getPreferredAgeFrom());
        existingUpMen.setPreferredAgeTo(updatePreferences.getPreferredAgeTo());

        existingUpMen.setPreferredHeightFrom(updatePreferences.getPreferredHeightFrom());
        existingUpMen.setPreferredHeightTo(updatePreferences.getPreferredHeightTo());
        return preferencesMenRepository.save(existingUpMen);
    }
}
//    public PreferencesMen deletePreferencesMen(int menId) {
//        Optional<PreferencesMen> preferencesMen = preferencesMenRepository.findById(menId);
//        if (preferencesMen.isPresent()) {
//            preferencesMenRepository.deleteById(menId);
//            return preferencesMen.get();
//        } else {
//            throw new EntityNotFoundException("men with ID " + menId + " not found.");
//        }
//    }



