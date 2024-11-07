package org.example.controller;

import org.example.DTO.PreferencesMenDTO;
import org.example.DTO.PreferencesWomenDTO;
import org.example.job.AgeUpdateService;
import org.example.model.*;
import org.example.model.Women;
import org.example.repository.PreferencesWomenRepository;
import org.example.repository.WomenRepository;
import org.example.service.AgeCalculator;
import org.example.service.WomenPreferencesServiceImpl;
import org.example.service.WomenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static org.example.controller.MenController.saveFile;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class WomenController {
    @Autowired
    private WomenRepository womenRepository;
    private final WomenServiceImpl womenService;
    private final WomenPreferencesServiceImpl womenPreferencesService;
    private final PreferencesWomenRepository preferencesWomenRepository;
    private final AgeUpdateService<Women> ageUpdateService;

    private static final Logger logger = LoggerFactory.getLogger(WomenController.class);


    @Autowired
    public WomenController(WomenServiceImpl womenService, WomenPreferencesServiceImpl womenPreferencesService, PreferencesWomenRepository preferencesWomenRepository,
                           AgeUpdateService<Women> ageUpdateService) {
        this.womenService = womenService;
        this.womenPreferencesService = womenPreferencesService;
        this.preferencesWomenRepository = preferencesWomenRepository;
        this.ageUpdateService = ageUpdateService;
    }

    //לריצה ידנית של הג'וב כדי לבצע עדכון בגיל לשימוש טסט כרגע
    @GetMapping("/women/run-job")
    public String runJob() {
        logger.info("Running job manually");
        List<Women> womenList = womenRepository.findAll(); // שליפת כל הרשומות של הנשים
        ageUpdateService.runJobManually(womenList);   // עדכון הגילאים באופן ידני
        womenRepository.saveAll(womenList);  // שמירת כל הרשומות המעודכנות
        return "Job ran for women"; // דף או הודעה שתינתן למשתמש לאחר הריצה
    }
    @GetMapping("/women/searchAll")
    public List<Women> getAllWomen() {
        return womenService.getAllWomen();
    }

    @GetMapping("/women/search")
    public List<Women> searchWomen(@RequestParam String term, @RequestParam String criteria) {
        System.out.println(term + criteria);
        logger.info("Received search request for women with term: {} and criteria: {}", term, criteria);
        List<Women> results = womenService.searchWomen(term, criteria);
        System.out.println("Results: " + results);
        return results;
    }

@DeleteMapping("/women/delete/{id}")
public ResponseEntity<?> deleteWomen(@PathVariable int id) {
    try {
        // שלב 1: חיפוש רשומת העדפות אישה לפי ה-ID של הגבר
        Optional<PreferencesWomen> preferencesWomenOpt = preferencesWomenRepository.findByWomenId(id);

        // שלב 2: אם רשומת העדפות אישה קיימת, מחק אותה
        preferencesWomenOpt.ifPresent(preferencesWomen -> preferencesWomenRepository.deleteById(preferencesWomen.getIdPreferencesWomen()));

        // שלב 3: מחיקת רשומת אישה
        Women deleteWomen = womenService.deleteWomen(id);

        return ResponseEntity.ok(deleteWomen);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
    @GetMapping("/women/{id}")
    public ResponseEntity<Women> getWomenById(@PathVariable("id") int id) {
        Women women = womenService.getWomanById(id);
        if (women != null) {
            return new ResponseEntity<>(women, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/women/update/{id}")
    public ResponseEntity<?> updateWoman(@PathVariable int id, @RequestBody Women updatedWoman) {
        try {
            System.out.println("/{id}");
            Women updated = womenService.updateWoman(id, updatedWoman);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/women/update/images/{id}")
    public ResponseEntity<Women> updateWomenImages(
            @PathVariable int id,
            @RequestParam(value = "profilePictureUrl", required = false) MultipartFile profilePictureUrl,
            @RequestParam(value = "additionalPictureUrl", required = false) MultipartFile additionalPictureUrl,
            @RequestParam(value = "deleteProfilePicture", required = false) String deleteProfile,
            @RequestParam(value = "deleteAdditionalPicture", required = false) String deleteAdditionalPicture) throws IOException {

        Women updatedWomen = womenService.updateWomenImages(id, profilePictureUrl, additionalPictureUrl, deleteProfile, deleteAdditionalPicture);
        return ResponseEntity.ok(updatedWomen);
    }

//    @PutMapping("/men/update/{id}")
//    public ResponseEntity<?> updateMen(@PathVariable int id, @RequestBody Men updateMen) {
//        try {
//            System.out.println(menService.updateMen(id, updateMen));
//            Men updated = menService.updateMen(id, updateMen);
//            return ResponseEntity.ok(updated);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    //update row in the table of preferencesMen, by id of the men table.
    @PutMapping("preferences_women/savePreferences/update/{womenId}")
    public ResponseEntity<?> updatePreferredWomen(@PathVariable int womenId, @RequestBody PreferencesWomen updatePreferencesWomen) {
        try {
            System.out.println("/{womenId}");
            PreferencesWomen updated = womenPreferencesService.updatePreferredWomen(womenId, updatePreferencesWomen);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/women/saveWomen")
    public ResponseEntity<Map<String, Object>> saveWomenData(
            @RequestParam("status") String status,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam("age") int age,
            @RequestParam("height") float height,
            @RequestParam("location") String location,
            @RequestParam("style") String style,
            @RequestParam("community") String community,
            @RequestParam("headCovering") String headCovering,
            @RequestParam("device") String device,
            @RequestParam("phone") String phone,
            @RequestParam("seeking") String seeking,
            @RequestParam(value = "profilePictureUrl", required = false) MultipartFile profilePictureUrl,
            @RequestParam(value = "additionalPictureUrl", required = false) MultipartFile additionalPictureUrl
    ) {
        try {
            Women women = new Women();
            women.setStatus(status);
            women.setFirstName(firstName);
            women.setLastName(lastName);
            women.setDateOfBirth(dateOfBirth);
            women.setAge(age);
            women.setHeight(height);
            women.setLocation(location);
            women.setStyle(style);
            women.setCommunity(community);
            women.setHeadCovering(headCovering);
            women.setDevice(device);
            women.setPhone(phone);
            women.setSeeking(seeking);

            if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
                String profilePicturePath = saveFile(profilePictureUrl); // פונקציה לשמירת קובץ
                women.setProfilePictureUrl(profilePicturePath);
            } else {
                women.setProfilePictureUrl(""); // או להשאיר ריק
            }
            if (additionalPictureUrl != null && !additionalPictureUrl.isEmpty()) {
                String additionalPicturePath = saveFile(additionalPictureUrl); // פונקציה לשמירת קובץ
                women.setAdditionalPictureUrl(additionalPicturePath);
            } else {
                women.setAdditionalPictureUrl(""); // או להשאיר ריק
            }

            Women saveWomen = womenService.addWomen(women);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", saveWomen.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    private String saveFile(MultipartFile file) throws IOException {
//        // יצירת שם קובץ ייחודי
//        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//        Path path = Paths.get("D:\\matchmaking\\src\\main\\resources\\static\\images\\" + fileName);
//        Files.copy(file.getInputStream(), path);
//        return fileName; // החזר רק את שם הקובץ
//
//    }
//    public ResponseEntity<Map<String, Object>> saveWomenData(@RequestBody Women women) {
//        try {
//            System.out.println("Received POST request with data: " + women);
//            System.out.println(women.getId());
//            // קבל את התאריך הנוכחי
//            LocalDate today = LocalDate.now();
//
//            // בדוק אם תאריך הלידה לא NULL
//            if (women.getDateOfBirth() != null) {
//                // חישוב גיל בעזרת פונקציה
//                int age = AgeCalculator.calculateAge(women.getDateOfBirth(), today);
//                women.setAge(age); // עדכן את גיל האדם בטבלה
//            } else {
//                // טיפול במצב שבו תאריך הלידה אינו מוגדר
//                System.out.println("Date of birth is null for man ID: " + women.getId());
//            }
//            Women savedWoman = womenService.addWomen(women);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("id", savedWoman.getId());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//            response.put("message", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

    @PostMapping("/women/savePreferences")
    public ResponseEntity<Map<String, Object>> savePreferences(@RequestBody PreferencesWomen preferencesWomen) {
        try {

            // לוודא ש-idMen מועבר ונשמר בצורה נכונה
            Women women = preferencesWomen.getWomen();
            if (women == null || women.getId() == 0) {
                throw new IllegalArgumentException("Men object or idMen is missing");
            }

            PreferencesWomen savedPreferences = womenPreferencesService.addPreferencesWomen(preferencesWomen);

            System.out.println("Saved preferences: " + savedPreferences);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("idPreferencesMen", savedPreferences.getIdPreferencesWomen());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
// שמירת ערך הid של טבלת אישה לביצוע שליפה ולשת, אותה בטבלה במסך בצד שמאל לצורך התאמה
    @GetMapping("preferences_women/byWomenId/{womenId}")
    public ResponseEntity<PreferencesWomenDTO> getPreferencesById(@PathVariable int womenId) {
        PreferencesWomen preferences = womenPreferencesService.getPreferencesWomenByWomenId(womenId);
        if (preferences != null) {
            PreferencesWomenDTO dto = convertToDTO(preferences);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // שמירת הid של טבלת העדפות לצורך שליפת הנתונים למסך עדכון ואחכ לצורך עדכון הפרטים בצורה תקינה
    @GetMapping("preferences_women/byWomenIdPreferences/{idPreferencesWomen}")
    public ResponseEntity<PreferencesWomenDTO> getPreferencesByIdPreferences(@PathVariable int idPreferencesWomen) {
        PreferencesWomen preferences = womenPreferencesService.getPreferencesWomenByPreferencesId(idPreferencesWomen);
        if (preferences != null) {
            PreferencesWomenDTO dto = convertToDTO(preferences);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private PreferencesWomenDTO convertToDTO(PreferencesWomen preferences) {
        PreferencesWomenDTO dto = new PreferencesWomenDTO();
        dto.setIdPreferencesWomen(preferences.getIdPreferencesWomen());
        dto.setPreferredRegion(preferences.getPreferredRegion());
        dto.setPreferredCommunity(preferences.getPreferredCommunity());
        dto.setHandkerchiefOrWig(preferences.getHandkerchiefOrWig());
        dto.setPreferredStyle(preferences.getPreferredStyle());
        dto.setKosherOrNonKosherDevice(preferences.getKosherOrNonKosherDevice());
        dto.setPreferredStatus(preferences.getPreferredStatus());
        dto.setPreferredAgeFrom(preferences.getPreferredAgeFrom());
        dto.setPreferredAgeTo(preferences.getPreferredAgeTo());

        dto.setPreferredHeightFrom(preferences.getPreferredHeightFrom());
        dto.setPreferredHeightTo(preferences.getPreferredHeightTo());
        dto.setWomenId(preferences.getWomen() != null ? preferences.getWomen().getId() : 0);
        System.out.println(dto.getWomenId());
        System.out.println(dto.getIdPreferencesWomen());
        System.out.println(dto.getHandkerchiefOrWig());
        return dto;
    }

    @GetMapping("/women/searchMatches")
    public List<Men> searchMatches(@RequestParam int womenId) {
        return womenService.findMatchesByWomenPreferences(womenId);
    }

    @GetMapping("/women/getImages/{id}")
    public ResponseEntity<Map<String, Object>> showSelectedImages(@PathVariable("id") int id) {
        Women women = womenService.findShowSelectedImages(id);

        if (women != null) {
            Map<String, Object> response = new HashMap<>();
            if (women.getProfilePictureUrl() != null) {
                response.put("profilePictureUrl", women.getProfilePictureUrl());
            } else {
                response.put("profilePictureUrl", "No profile image");
            }

            if (women.getAdditionalPictureUrl() != null) {
                response.put("additionalPictureUrl", women.getAdditionalPictureUrl());
            } else {
                response.put("additionalPictureUrl", "No additional image");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}



