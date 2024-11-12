package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Women;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesMenRepository;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import static org.example.controller.MenController.saveFile;

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
        System.out.println(men.getProfilePictureUrl());
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
//    public Men updateMen(int id, Men updateMen) {
//        Men existingMen = menRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("לא נמצא"));
//
////        if (updateMen.getAge() <= 0 || updateMen.getAge() > 120) {
////            throw new IllegalArgumentException("Age must be between 1 and 120");
////        }
//        // עדכון השדות
//        existingMen.setDevice(updateMen.getDevice());
//        existingMen.setCommunity(updateMen.getCommunity());
//        existingMen.setHeadCovering(updateMen.getHeadCovering());
//        existingMen.setStyle(updateMen.getStyle());
//        existingMen.setDateOfBirth(updateMen.getDateOfBirth());
//        existingMen.setAge(updateMen.getAge());
//        existingMen.setFirstName(updateMen.getFirstName());
//        existingMen.setLastName(updateMen.getLastName());
//        existingMen.setHeight(updateMen.getHeight());
//        existingMen.setStatus(updateMen.getStatus());
//        existingMen.setLocation(updateMen.getLocation());
//        existingMen.setPhone(updateMen.getPhone());
//        existingMen.setSeeking(updateMen.getSeeking());
//
//        return menRepository.save(existingMen);
//    }
    public Men updateMen(int id, Men updateMen)  {
        Men existingMen = menRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("לא נמצא"));

        // עדכון השדות
        //existingMen.setId(updateMen.getId());
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
        existingMen.setPhone(updateMen.getPhone());
        existingMen.setSeeking(updateMen.getSeeking());
        existingMen.setWork(updateMen.getWork());
        existingMen.setStudies(updateMen.getStudies());

        return menRepository.save(existingMen);
    }

    public Men updateMenImages(int id, MultipartFile profilePictureUrl, MultipartFile additionalPictureUrl, String deleteProfile, String deleteAdditionalPicture) throws IOException {
        Men existingMen = menRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));

        // עדכון URLים של התמונות
        if ("true".equals(deleteProfile) && existingMen.getProfilePictureUrl() != null) {
            deletePicture(existingMen.getProfilePictureUrl()); // מחיקת הקובץ מהשרת
            System.out.println(existingMen.getProfilePictureUrl());
            existingMen.setProfilePictureUrl("");  // הסרת הנתיב ממסד הנתונים
        }
        if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
            System.out.println(existingMen.getProfilePictureUrl());
            deletePicture(existingMen.getProfilePictureUrl());
            System.out.println(existingMen.getProfilePictureUrl());

            String newProfilePicturePath = saveFile(profilePictureUrl);
            existingMen.setProfilePictureUrl(newProfilePicturePath);
            System.out.println(newProfilePicturePath);

        }
        if ("true".equals(deleteAdditionalPicture) && existingMen.getAdditionalPictureUrl() != null) {
            deletePicture(existingMen.getAdditionalPictureUrl()); // מחיקת הקובץ מהשרת
            existingMen.setAdditionalPictureUrl("");  // הסרת הנתיב ממסד הנתונים
        }
        if (additionalPictureUrl != null && !additionalPictureUrl.isEmpty()) {
            deletePicture(existingMen.getAdditionalPictureUrl());
            String additionalPicturePath = saveFile(additionalPictureUrl);
            existingMen.setAdditionalPictureUrl(additionalPicturePath);
        }

        // שמירת השינויים במסד הנתונים
        return menRepository.save(existingMen);
    }

    public static void deletePicture(String fileName) {
        String directoryPath = "D:\\matchmaking\\src\\main\\resources\\static\\images\\"; // נתיב לתיקיית הקבצים
        File file = new File(directoryPath + fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("הקובץ נמחק בהצלחה: " + file.getAbsolutePath());
            } else {
                System.out.println("לא הצלחנו למחוק את הקובץ: " + file.getAbsolutePath());
            }
        } else {
            System.out.println("הקובץ לא קיים: " + file.getAbsolutePath());
        }

    }


    // פונקציה לחילוץ שם בסיסי של קובץ לאחר קו תחתון
//    private String extractBaseFileName(String filePath) {
//        if (filePath == null || filePath.isEmpty()) {
//            return "";
//        }
//        int underscoreIndex = filePath.indexOf("_");
//        return underscoreIndex != -1 ? filePath.substring(underscoreIndex + 1) : filePath;
//    }

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
                isHeadCoveringMatch(preferences, women)
                && isSimpleStudyMatch(preferences, women)
                //&& isFlexibleMatch(preferences, women)
                ;

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



    private boolean isSimpleStudyMatch(PreferencesMen preferences, Women women) {
        String preferredStudies = preferences.getPreferredStudies();
        String womanStudies = women.getStudies();

        if (preferredStudies.isEmpty() || womanStudies.isEmpty()) {
            return true;
        }

        // מילות המפתח של הלימודים שיכולים להתאים
        List<String> validStudies = Arrays.asList("אברך", "לומד תורה", "תואר", "אקדמאי");

        // המרת המילים בשני השדות לרשימות של מילים
        List<String> preferredStudiesWords = Arrays.asList(preferredStudies.split("\\s+"));
        List<String> womanStudiesWords = Arrays.asList(womanStudies.split("\\s+"));

        // בדיקה אם יש מילה מתאימה בשני השדות
        boolean preferredHasSpecialStudy = containsAny(preferredStudiesWords, validStudies);
        boolean womanHasSpecialStudy = containsAny(womanStudiesWords, validStudies);

        // אם המילה בהעדפות לא נמצאה בשדה לימודים של האישה, נחזיר false
        if (preferredHasSpecialStudy && !womanHasSpecialStudy) {
            return false;
        }

        // אם יש מילים מתאימות בשני השדות, מחזירים true
        if (preferredHasSpecialStudy && womanHasSpecialStudy) {
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


    private boolean isFlexibleMatch(PreferencesMen preferences, Women women) {
        int matchCount = 0;

        // בדיקה אם יש חפיפה בשדה "לימודים" בין העדפות הגבר לבין האישה
        if (isWordMatch(preferences.getPreferredStudies(), women.getStudies())) {
            matchCount++;
        }

        // בדיקה אם יש חפיפה בשדה "עבודה" בין העדפות הגבר לבין האישה
        if (isWordMatch(preferences.getPreferredWork(), women.getWork())) {
            matchCount++;
        }

        // בדיקה חוצה בין "לימודים" של הגבר ל"עבודה" של האישה
        if (isWordMatch(preferences.getPreferredStudies(), women.getWork())) {
            matchCount++;
        }

        // בדיקה חוצה בין "עבודה" של הגבר ל"לימודים" של האישה
        if (isWordMatch(preferences.getPreferredWork(), women.getStudies())) {
            matchCount++;
        }

        // החזרת התאמה אם נמצאו לפחות שתי חפיפות
        return matchCount >= 2;
    }

    // פונקציה לבדיקת התאמה לפי מילות מפתח בשדה, תוך התעלמות משדות ריקים
    private boolean isWordMatch(String preferenceField, String personField) {
        // אם אחד השדות ריק או חסר, להתעלם ולהחזיר "התאמה"
        if ((preferenceField == null || preferenceField.isEmpty()) ||
                (personField == null || personField.isEmpty())) {
            return true;
        }

        // פיצול הטקסט למילים נפרדות
        Set<String> preferenceWords = new HashSet<>(Arrays.asList(preferenceField.split("\\s+")));
        Set<String> personWords = new HashSet<>(Arrays.asList(personField.split("\\s+")));

        // בדיקה אם יש לפחות מילה אחת משותפת
        for (String word : preferenceWords) {
            if (personWords.contains(word)) {
                return true;
            }
        }

        return false;
    }



    public Men findShowSelectedImages(int id) {
        return menRepository.findById(id).orElse(null);
    }


}

