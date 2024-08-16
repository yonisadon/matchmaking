package org.example.service;

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

//        public List<PreferencesWomen> getAllPreferencesWomen() {
//            return preferencesWomenRepository.findAll();
//        }
//
//        public void deletePreferencesWomenById(int id) {
//            preferencesWomenRepository.deleteById(id);
//        }
}


