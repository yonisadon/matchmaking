package org.example.service;


import org.example.DTO.MatchDto;
import org.example.DTO.MatchDtoForMen;
import org.example.DTO.MatchDtoForWomen;
import org.example.exception.ResourceNotFoundException;
import org.example.model.*;
import org.example.model.Women;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesWomenRepository;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.controller.MenController.saveFile;
import static org.example.service.MenServiceImpl.deletePicture;

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

    //service, Retrieving a record by ID card from the men's register, and presenting the details in an update form
    public Women getWomenById(int id) {
        return womenRepository.findById(id).orElse(null);
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
        existingWoman.setPhone(updatedWoman.getPhone());
        existingWoman.setSeeking(updatedWoman.getSeeking());
        existingWoman.setWork(updatedWoman.getWork());
        existingWoman.setStudies(updatedWoman.getStudies());

        return womenRepository.save(existingWoman);
    }

    public Women updateWomenImages(int id, MultipartFile profilePictureUrl, MultipartFile additionalPictureUrl, String deleteProfile, String deleteAdditionalPicture) throws IOException {
        Women existingWoman = womenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));

        // עדכון URLים של התמונות
        if ("true".equals(deleteProfile) && existingWoman.getProfilePictureUrl() != null) {
            deletePicture(existingWoman.getProfilePictureUrl()); // מחיקת הקובץ מהשרת
            System.out.println(existingWoman.getProfilePictureUrl());
            existingWoman.setProfilePictureUrl("");  // הסרת הנתיב ממסד הנתונים
        }
        if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
            System.out.println(existingWoman.getProfilePictureUrl());
            deletePicture(existingWoman.getProfilePictureUrl());
            System.out.println(existingWoman.getProfilePictureUrl());

            String newProfilePicturePath = saveFile(profilePictureUrl);
            existingWoman.setProfilePictureUrl(newProfilePicturePath);
            System.out.println(newProfilePicturePath);

        }
        if ("true".equals(deleteAdditionalPicture) && existingWoman.getAdditionalPictureUrl() != null) {
            deletePicture(existingWoman.getAdditionalPictureUrl()); // מחיקת הקובץ מהשרת
            existingWoman.setAdditionalPictureUrl("");  // הסרת הנתיב ממסד הנתונים
        }
        if (additionalPictureUrl != null && !additionalPictureUrl.isEmpty()) {
            deletePicture(existingWoman.getAdditionalPictureUrl());
            String additionalPicturePath = saveFile(additionalPictureUrl);
            existingWoman.setAdditionalPictureUrl(additionalPicturePath);
        }

        // שמירת השינויים במסד הנתונים
        return womenRepository.save(existingWoman);
    }


    public List<MatchDto> findMatchesForWomen() {
        List<Women> womenList = womenRepository.findAll();  // רשימה של נשים
        List<Men> menList = menRepository.findAll();  // רשימה של גברים

        List<MatchDto> matches = new ArrayList<>();

        for (Women women : womenList) {
            PreferencesWomen preferences = preferencesWomenRepository.findByWomenId(women.getId()) // אם יש לך העדפות לנשים
                    .orElseThrow(() -> new ResourceNotFoundException("לא נמצאו העדפות לאישה עם id: " + women.getId()));

            for (Men men : menList) {
                // השתמש בפונקציה matchesPreferences כדי לבדוק את ההתאמה
                if (matchesPreferences(preferences, men)) {
                    MatchDtoForWomen womenDto = new MatchDtoForWomen(
                            women.getId(),
                            women.getFirstName(),
                            women.getLastName(),
                            women.getAge(),
                            women.getHeight(),
                            women.getStatus(),
                            women.getStyle()
                    );
                    MatchDtoForMen menDto = new MatchDtoForMen(
                            men.getId(),
                            men.getFirstName(),
                            men.getLastName(),
                            men.getAge(),
                            men.getHeight(),
                            men.getStatus(),
                            men.getStyle()
                    );

                    MatchDto matchDto = new MatchDto(menDto, womenDto);

                    // אם יש התאמה, צור את ה-DTO
//                    MatchDto match = new MatchDto(
//                            women.getId(), women.getFirstName(), women.getLastName(), women.getAge(), women.getHeight(), women.getStatus(), women.getStyle(),
//                            men.getId(), men.getFirstName(), men.getLastName(), men.getAge(), men.getHeight(), men.getStatus(), men.getStyle()
//                    );
                    System.out.println("Women: " + women.getFirstName() + ", Men: " + men.getFirstName());
                    matches.add(matchDto);

                    //matches.add(match);
                }
            }
        }
        return matches;
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
                isHeadCoveringMatch(preferences, men)
                && isSimpleStudyMatch(preferences, men)
                ;
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

    private boolean isSimpleStudyMatch(PreferencesWomen preferences, Men men) {
        String preferredStudies = preferences.getPreferredStudies();
        String menStudies = men.getStudies();

        if (preferredStudies.isEmpty() || menStudies.isEmpty()) {
            return true;
        }

        // מילות המפתח של הלימודים שיכולים להתאים
        List<String> validStudies = Arrays.asList("אברך", "לומד תורה", "תואר", "אקדמאי");

        // המרת המילים בשני השדות לרשימות של מילים
        List<String> preferredStudiesWords = Arrays.asList(preferredStudies.split("\\s+"));
        List<String> menStudiesWords = Arrays.asList(menStudies.split("\\s+"));

        // בדיקה אם יש מילה מתאימה בשני השדות
        boolean preferredHasSpecialStudy = containsAny(preferredStudiesWords, validStudies);
        boolean menHasSpecialStudy = containsAny(menStudiesWords, validStudies);

        // אם המילה בהעדפות לא נמצאה בשדה לימודים של האישה, נחזיר false
        if (preferredHasSpecialStudy && !menHasSpecialStudy) {
            return false;
        }

        // אם יש מילים מתאימות בשני השדות, מחזירים true
        if (preferredHasSpecialStudy && menHasSpecialStudy) {
            return true;
        }

        // אם לא מצאנו התאמה, נחשב לתאמה (כמו שציינת)
        return true;
    }

    // פונקציה לעזור לבדוק אם שדה מכיל אחד מהמונחים המיוחדים
    private boolean containsAny(List<String> words, List<String> validStudies) {
        for (String word : words) {
            if (validStudies.contains(word)) {
                return true;
            }
        }
        return false;
    }
//    private boolean isFlexibleMatch(PreferencesWomen preferences, Men men) {
//        int matchCount = 0;
//
//        // בדיקה אם יש חפיפה בשדה "לימודים" בין העדפות הגבר לבין האישה
//        if (isWordMatch(preferences.getPreferredStudies(), men.getStudies())) {
//            matchCount++;
//        }
//
//        // בדיקה אם יש חפיפה בשדה "עבודה" בין העדפות הגבר לבין האישה
//        if (isWordMatch(preferences.getPreferredWork(), men.getWork())) {
//            matchCount++;
//        }
//
//        // בדיקה חוצה בין "לימודים" של הגבר ל"עבודה" של האישה
//        if (isWordMatch(preferences.getPreferredStudies(), men.getWork())) {
//            matchCount++;
//        }
//
//        // בדיקה חוצה בין "עבודה" של הגבר ל"לימודים" של האישה
//        if (isWordMatch(preferences.getPreferredWork(), men.getStudies())) {
//            matchCount++;
//        }
//
//        // החזרת התאמה אם נמצאו לפחות שתי חפיפות
//        return matchCount >= 2;
//    }
//
//    // פונקציה לבדיקת התאמה לפי מילות מפתח בשדה, תוך התעלמות משדות ריקים
//    private boolean isWordMatch(String preferenceField, String personField) {
//        // אם אחד השדות ריק או חסר, להתעלם ולהחזיר "התאמה"
//        if ((preferenceField == null || preferenceField.isEmpty()) ||
//                (personField == null || personField.isEmpty())) {
//            return true;
//        }
//
//        // פיצול הטקסט למילים נפרדות
//        Set<String> preferenceWords = new HashSet<>(Arrays.asList(preferenceField.split("\\s+")));
//        Set<String> personWords = new HashSet<>(Arrays.asList(personField.split("\\s+")));
//
//        // בדיקה אם יש לפחות מילה אחת משותפת
//        for (String word : preferenceWords) {
//            if (personWords.contains(word)) {
//                return true;
//            }
//        }
//
//        return false;
//    }


    public Women findShowSelectedImages(int id) {
        return womenRepository.findById(id).orElse(null);
    }

}
