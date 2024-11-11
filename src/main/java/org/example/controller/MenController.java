package org.example.controller;

import org.example.DTO.PreferencesMenDTO;
import org.example.job.AgeUpdateService;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Women;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesMenRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import org.example.service.MenServiceImpl;
import org.example.service.MenPreferencesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class MenController {
    @Autowired
    private MenRepository menRepository;
    private final MenServiceImpl menService;
    private final MenPreferencesServiceImpl menPreferencesService;
    private final PreferencesMenRepository preferencesMenRepository;
    private final AgeUpdateService<Men> ageUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(MenController.class);


//constructor.
    @Autowired
    public MenController(MenServiceImpl menService, MenPreferencesServiceImpl menPreferencesService, PreferencesMenRepository preferencesMenRepository,
                         AgeUpdateService<Men> ageUpdateService){
        this.menService = menService;
        this.menPreferencesService = menPreferencesService;
        this.preferencesMenRepository = preferencesMenRepository;
        this.ageUpdateService = ageUpdateService;

    }

    //לריצה ידנית של הג'וב כדי לבצע עדכון בגיל לשימוש טסט כרגע
    @GetMapping("/men/run-job")
    public String runJob() {
        logger.info("Running job manually");
        List<Men> menList = menRepository.findAll(); // שליפת כל הרשומות של הגברים
        ageUpdateService.runJobManually(menList);   // עדכון הגילאים באופן ידני
        menRepository.saveAll(menList);  // שמירת כל הרשומות המעודכנות
        return "Job ran for men"; // דף או הודעה שתינתן למשתמש לאחר הריצה
    }

    // general search in the table of men, after clicking in search button.
    // continue in function getAllMen in class MenServiceImpl.
    @GetMapping("/men/searchAll")
    public ResponseEntity<List<Men>> getAllMen() {
        try {
            List<Men> menList = menRepository.findAll();
            System.out.println(menList);
            System.out.println(menRepository.findAll());
            return ResponseEntity.ok(menList);
        } catch (Exception e) {
            // טיפול בשגיאות
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieving a record by ID from the table of men, if you click on the update record button.
    // continue in function getMenById in class MenServiceImpl.
    @GetMapping("/men/{id}")
    public ResponseEntity<Men> getMenById(@PathVariable("id") int id) {
        Men men = menService.getMenById(id);
        if (men != null) {
            return new ResponseEntity<>(men, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Search by any condition (first name or age...) from the table of men, after clicking in search button.
    // continue in function searchMen in class MenServiceImpl.
    @GetMapping("/men/search")
    public List<Men> searchMen(@RequestParam String term, @RequestParam String criteria) {
        List<Men> results = menService.searchMen(term, criteria);

        logger.info("Received search request for women with term: {} and criteria: {}", term, criteria);
        System.out.println("Results: " + results);
        return results;
    }

    //Saving data by creating a new record in the table of men.
    // continue in function addMen in class MenServiceImpl.
    @PostMapping("/men/saveMen")
    public ResponseEntity<Map<String, Object>> saveMenData(
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
            @RequestParam("work") String work,
            @RequestParam("studies") String studies,
            @RequestParam(value = "profilePictureUrl", required = false) MultipartFile profilePictureUrl,
            @RequestParam(value = "additionalPictureUrl", required = false) MultipartFile additionalPictureUrl
    ) {
        try {
            Men men = new Men();
            men.setStatus(status);
            men.setFirstName(firstName);
            men.setLastName(lastName);
            men.setDateOfBirth(dateOfBirth);
            men.setAge(age);
            men.setHeight(height);
            men.setLocation(location);
            men.setStyle(style);
            men.setCommunity(community);
            men.setHeadCovering(headCovering);
            men.setDevice(device);
            men.setPhone(phone);
            men.setSeeking(seeking);
            men.setWork(work);
            men.setStudies(studies);
            if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
                String profilePicturePath = saveFile(profilePictureUrl); // פונקציה לשמירת קובץ
                men.setProfilePictureUrl(profilePicturePath);
            } else {
                men.setProfilePictureUrl(""); // או להשאיר ריק
            }
            if (additionalPictureUrl != null && !additionalPictureUrl.isEmpty()) {
                String additionalPicturePath = saveFile(additionalPictureUrl); // פונקציה לשמירת קובץ
                men.setAdditionalPictureUrl(additionalPicturePath);
            } else {
                men.setAdditionalPictureUrl(""); // או להשאיר ריק

            }

            Men savedMen = menService.addMen(men);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", savedMen.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public static String saveFile(MultipartFile file) throws IOException {
        // יצירת שם קובץ ייחודי
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("D:\\matchmaking\\src\\main\\resources\\static\\images\\" + fileName);
        System.out.println(path);
        Files.copy(file.getInputStream(), path);
        return fileName; // החזר רק את שם הקובץ

    }
    
//    private String saveFile(MultipartFile file) throws IOException {
//        // כאן תממש את לוגיקת שמירת הקובץ במערכת שלך
//        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//        // ודא שיש לך backslash בסוף הנתיב
//        Path path = Paths.get("D:\\PIC\\" + fileName);
//
//        // שמירת הקובץ בתיקייה שציינת
//        Files.copy(file.getInputStream(), path);
//
//        return path.toString();
//    }


//    @PostMapping("/men/saveMen")
//    public ResponseEntity<Map<String, Object>> saveMenData(@RequestBody Men men) {
//        try {
//            System.out.println("Received POST request with data: " + men);
//            System.out.println(men.getId());
//            //men.setAge(AgeCalculator.calculateAge(men.getDateOfBirth(), men.));
//            // קבל את התאריך הנוכחי
//            LocalDate today = LocalDate.now();
//
//            // בדוק אם תאריך הלידה לא NULL
//            if (men.getDateOfBirth() != null) {
//                // חישוב גיל בעזרת פונקציה
//                int age = AgeCalculator.calculateAge(men.getDateOfBirth(), today);
//                men.setAge(age); // עדכן את גיל האדם בטבלה
//            } else {
//                // טיפול במצב שבו תאריך הלידה אינו מוגדר
//                System.out.println("Date of birth is null for man ID: " + men.getId());
//            }
//            System.out.println(men.getProfilePictureUrl());
//            Men savedMen = menService.addMen(men);
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("id", savedMen.getId());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//            response.put("message", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

    //Saving data by creating a new record in the table of preferencesMen(This table is linked to men table).
    @PostMapping("/men/savePreferences")
    public ResponseEntity<Map<String, Object>> savePreferences(@RequestBody PreferencesMen preferencesMen) {

        try {
            Men men = preferencesMen.getMen();
            if (men == null || men.getId() == 0) {
                throw new IllegalArgumentException("Men object or idMen is missing");
            }

            PreferencesMen savedPreferences = menPreferencesService.addPreferencesMen(preferencesMen);

            System.out.println("Saved preferences: " + savedPreferences);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("idPreferencesMen", savedPreferences.getIdPreferencesMen());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //deleting row in the table of men and table of preferencesMen by id from the men table.
    //continue in function deleteMen in class MenServiceImpl.
    @DeleteMapping("/men/delete/{id}")
    public ResponseEntity<?> deleteMen(@PathVariable int id) {
        try {
            // שלב 1: חיפוש רשומת העדפות גבר לפי ה-ID של הגבר
            Optional<PreferencesMen> preferencesMenOpt = preferencesMenRepository.findByMenId(id);

            // שלב 2: אם רשומת העדפות גבר קיימת, מחק אותה
            preferencesMenOpt.ifPresent(preferencesMen -> preferencesMenRepository.deleteById(preferencesMen.getIdPreferencesMen()));

            // שלב 3: מחיקת רשומת הגבר
            Men deleteMen = menService.deleteMen(id);

            return ResponseEntity.ok(deleteMen);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //update row in the table of men, by id.
    //continue in function updateMen in class MenServiceImpl.
    @PutMapping("/men/update/{id}")
    public ResponseEntity<?> updateMen(@PathVariable int id, @RequestBody Men updateMen) {
        try {
            System.out.println(menService.updateMen(id, updateMen));
            Men updated = menService.updateMen(id, updateMen);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/men/update/images/{id}")
    public ResponseEntity<Men> updateMenImages(
            @PathVariable int id,
            @RequestParam(value = "profilePictureUrl", required = false) MultipartFile profilePictureUrl,
            @RequestParam(value = "additionalPictureUrl", required = false) MultipartFile additionalPictureUrl,
            @RequestParam(value = "deleteProfilePicture", required = false) String deleteProfile,
            @RequestParam(value = "deleteAdditionalPicture", required = false) String deleteAdditionalPicture) throws IOException {

        Men updatedMen = menService.updateMenImages(id, profilePictureUrl, additionalPictureUrl, deleteProfile, deleteAdditionalPicture);
        return ResponseEntity.ok(updatedMen);
    }



    //update row in the table of preferencesMen, by id of the men table.
    @PutMapping("preferences_men/savePreferences/update/{menId}")
    public ResponseEntity<?> updatePreferredMen(@PathVariable int menId, @RequestBody PreferencesMen updatePreferences) {
        try {
            System.out.println(menId);
            System.out.println(updatePreferences);
            PreferencesMen updated = menPreferencesService.updatePreferredMen(menId, updatePreferences);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //Displaying data according to an ID search in the men's table,
    // but the data on the website screen for finding the match and displaying the data,
    // will be taken from the preferencesMen table
    @GetMapping("preferences_men/byMenId/{menId}")
        public ResponseEntity<PreferencesMenDTO> getPreferencesById(@PathVariable int menId) {
            PreferencesMen preferences = menPreferencesService.getPreferencesMenByMenId(menId);
            if (preferences != null) {
                PreferencesMenDTO dto = convertToDTO(preferences);
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    //Continue the process of displaying or updating data by using the DTO class, and the identifier is idPreferencesMen in preferencesMen table.
    @GetMapping("preferences_men/byMenIdPreferences/{idPreferencesMen}")
    public ResponseEntity<PreferencesMenDTO> getPreferencesByIdPreferences(@PathVariable int idPreferencesMen) {
        PreferencesMen preferences = menPreferencesService.getPreferencesMenByPreferencesId(idPreferencesMen);
        if (preferences != null) {
            PreferencesMenDTO dto = convertToDTO(preferences);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        private PreferencesMenDTO convertToDTO(PreferencesMen preferences) {
            PreferencesMenDTO dto = new PreferencesMenDTO();
            dto.setIdPreferencesMen(preferences.getIdPreferencesMen());
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
            dto.setMenId(preferences.getMen() != null ? preferences.getMen().getId() : 0);
            System.out.println(dto.getMenId());
            System.out.println(dto.getIdPreferencesMen());
            System.out.println(dto.getHandkerchiefOrWig());
            return dto;
        }

    // search matches by id in the table of men.
    //continue in function findMatchesByMenPreferences in MenServiceImpl class.
    @GetMapping("/men/searchMatches")
    public List<Women> searchMatches(@RequestParam int menId) {
        return menService.findMatchesByMenPreferences(menId);
    }

    @GetMapping("/men/getImages/{id}")
    public ResponseEntity<Map<String, Object>> showSelectedImages(@PathVariable("id") int id) {
        Men men = menService.findShowSelectedImages(id);

        if (men != null) {
            Map<String, Object> response = new HashMap<>();
            if (men.getProfilePictureUrl() != null) {
                response.put("profilePictureUrl", men.getProfilePictureUrl());
            } else {
                response.put("profilePictureUrl", "No profile image");
            }

            if (men.getAdditionalPictureUrl() != null) {
                response.put("additionalPictureUrl", men.getAdditionalPictureUrl());
            } else {
                response.put("additionalPictureUrl", "No additional image");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/men/getImages/{id}")
//    public ResponseEntity<Men> showSelectedImages(@PathVariable("id") int id) {
//        Men men = menService.findShowSelectedImages(id);
//        if (men != null) {
//            return new ResponseEntity<>(men, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    }






