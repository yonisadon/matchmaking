package org.example.service;

import org.example.model.PreferencesMen;
import org.example.model.PreferencesWomen;
import org.example.repository.PreferencesWomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class WomenPreferencesServiceImpl {

    @Autowired
    private final PreferencesWomenRepository preferencesWomenRepository;


        public WomenPreferencesServiceImpl(PreferencesWomenRepository preferencesWomenRepository) {
            this.preferencesWomenRepository = preferencesWomenRepository;
        }

        public PreferencesWomen addPreferencesWomen(PreferencesWomen preferencesWomen) {
            if (preferencesWomen.getPreferredAgeFrom() > preferencesWomen.getPreferredAgeTo() ||
                    preferencesWomen.getPreferredAgeFrom() == 0 && preferencesWomen.getPreferredAgeTo() != 0 ||
                    preferencesWomen.getPreferredAgeFrom() != 0 && preferencesWomen.getPreferredAgeTo() == 0) {
                throw new IllegalArgumentException("גיל ההתחלתי לא יכול להיות גדול מהגיל הסופי");
            }

            if (preferencesWomen.getPreferredHeightFrom() > preferencesWomen.getPreferredHeightTo() ||
                    preferencesWomen.getPreferredHeightFrom() == 0 && preferencesWomen.getPreferredHeightTo() != 0 ||
                    preferencesWomen.getPreferredHeightFrom() != 0 && preferencesWomen.getPreferredHeightTo() == 0) {
                throw new IllegalArgumentException("גובה התחלתי לא יכול להיות גדול מהגובה הסופי");
            }
            return preferencesWomenRepository.save(preferencesWomen);
        }

        public PreferencesWomen getPreferencesWomenByWomenId(int womenId) {
            System.out.println(womenId);
            return preferencesWomenRepository.findByWomenId(womenId)
                    .orElseThrow(() -> new EntityNotFoundException("Preferences for womenId " + womenId + " not found"));
        }

    public PreferencesWomen getPreferencesWomenByPreferencesId(int idPreferencesWomen) {
        System.out.println(idPreferencesWomen);
        return preferencesWomenRepository.findByIdPreferencesWomen(idPreferencesWomen)
                .orElseThrow(() -> new EntityNotFoundException("Preferences for womenId " + idPreferencesWomen + " not found"));
    }

    public PreferencesWomen updatePreferredWomen(int womenId, PreferencesWomen updatePreferencesWomen) {
        System.out.println(womenId);
        System.out.println(updatePreferencesWomen);
        PreferencesWomen existingUpWomen = preferencesWomenRepository.findByIdPreferencesWomen(womenId).orElseThrow(() -> new EntityNotFoundException("preferences women not found "+ womenId));

        // עדכון השדות
        existingUpWomen.setPreferredRegion(updatePreferencesWomen.getPreferredRegion());
        existingUpWomen.setPreferredCommunity(updatePreferencesWomen.getPreferredCommunity());
        existingUpWomen.setHandkerchiefOrWig(updatePreferencesWomen.getHandkerchiefOrWig());
        existingUpWomen.setPreferredStyle(updatePreferencesWomen.getPreferredStyle());
        existingUpWomen.setKosherOrNonKosherDevice(updatePreferencesWomen.getKosherOrNonKosherDevice());
        existingUpWomen.setPreferredStatus(updatePreferencesWomen.getPreferredStatus());
        existingUpWomen.setPreferredAgeFrom(updatePreferencesWomen.getPreferredAgeFrom());
        existingUpWomen.setPreferredAgeTo(updatePreferencesWomen.getPreferredAgeTo());
        existingUpWomen.setPreferredWork(updatePreferencesWomen.getPreferredWork());
        existingUpWomen.setPreferredStudies(updatePreferencesWomen.getPreferredStudies());
        existingUpWomen.setPreferredHeightFrom(updatePreferencesWomen.getPreferredHeightFrom());
        existingUpWomen.setPreferredHeightTo(updatePreferencesWomen.getPreferredHeightTo());
        return preferencesWomenRepository.save(existingUpWomen);
    }
}
//        public List<PreferencesWomen> getAllPreferencesWomen() {
//            return preferencesWomenRepository.findAll();
//        }
//
//        public void deletePreferencesWomenById(int id) {
//            preferencesWomenRepository.deleteById(id);
//        }



