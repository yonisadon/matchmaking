package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.PreferencesMenDTO;
import org.example.job.AgeUpdateService;
import org.example.service.AgeCalculator;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Women;
import org.example.repository.MenRepository;
import org.example.repository.PreferencesMenRepository;
import org.springframework.web.bind.annotation.GetMapping;

import org.example.service.MenServiceImpl;
import org.example.service.MenPreferencesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class MenController {
    @Autowired
    private MenRepository menRepository;
    private final MenServiceImpl menService;
    private final MenPreferencesServiceImpl menPreferencesService;
    private final PreferencesMenRepository preferencesMenRepository;
    private static final Logger logger = LoggerFactory.getLogger(MenController.class);

    private final AgeUpdateService<Men> ageUpdateService;


    @Autowired
    public MenController(MenServiceImpl menService, MenPreferencesServiceImpl menPreferencesService, PreferencesMenRepository preferencesMenRepository,
                         AgeUpdateService<Men> ageUpdateService){
        this.menService = menService;
        this.menPreferencesService = menPreferencesService;
        this.preferencesMenRepository = preferencesMenRepository;
        this.ageUpdateService = ageUpdateService;

    }

    //לריצה ידנית של הג'וב כדי לבצע עדכון בגיל לשימוש טסט כרגע
    @GetMapping("/run-job")
    public String runJob() {
        logger.info("Running job manually");
        List<Men> menList = menRepository.findAll(); // שליפת כל הרשומות של הגברים
        ageUpdateService.runJobManually(menList);   // עדכון הגילאים באופן ידני
        menRepository.saveAll(menList);  // שמירת כל הרשומות המעודכנות
        return "Job ran for men"; // דף או הודעה שתינתן למשתמש לאחר הריצה
    }

    //    @GetMapping("/run-job")
//    public String runJob() {
//        logger.info("Running job manually");
//        List<Men> menList = menRepository.findAll();
//        ageUpdateService.runJobManually(menList);
//        return "job-ran for men"; // דף או הודעה שתינתן למשתמש לאחר הריצה
//    }
    //    @GetMapping("/searchAll")
//    public List<Men> getAllMen() {
//        List<Men> menList = menService.getAllMen();
//        System.out.println("Returning number of records: " + menList.size());
//        List<Men> menRepositoryList = menRepository.findAll();
//        System.out.println(menRepositoryList);
//
//        return menList;
//    }
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


    @GetMapping("/men/{id}")
    public ResponseEntity<Men> getMenById(@PathVariable("id") int id) {
        Men men = menService.getMenById(id);
        if (men != null) {
            return new ResponseEntity<>(men, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/men/search")
    public List<Men> searchMen(@RequestParam String term, @RequestParam String criteria) {
        List<Men> results = menService.searchMen(term, criteria);

        logger.info("Received search request for women with term: {} and criteria: {}", term, criteria);
        System.out.println("Results: " + results);
        return results;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Men> getMenById(@PathVariable("id") int id) {
//        Men men = menService.getMenById(id);
//        if (men != null) {
//            return new ResponseEntity<>(men, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/men/saveMen")
    public ResponseEntity<Map<String, Object>> saveMenData(@RequestBody Men men) {
        try {
            System.out.println("Received POST request with data: " + men);
            System.out.println(men.getId());
            //men.setAge(AgeCalculator.calculateAge(men.getDateOfBirth(), men.));
            // קבל את התאריך הנוכחי
            LocalDate today = LocalDate.now();

            // בדוק אם תאריך הלידה לא NULL
            if (men.getDateOfBirth() != null) {
                // חישוב גיל בעזרת פונקציה
                int age = AgeCalculator.calculateAge(men.getDateOfBirth(), today);
                men.setAge(age); // עדכן את גיל האדם בטבלה
            } else {
                // טיפול במצב שבו תאריך הלידה אינו מוגדר
                System.out.println("Date of birth is null for man ID: " + men.getId());
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
//    public ResponseEntity<?> deleteMen(@PathVariable int id, @PathVariable int menId) {
//        try {
//            Men deleteMen = menService.deleteMen(id);
//            PreferencesMen preferencesMen = menPreferencesService.deletePreferencesMen(menId);
//            Map<String, Object> response = new HashMap<>();
//            response.put("deletedMen", deleteMen);
//            response.put("deletedPreferencesMen", preferencesMen);
//            return ResponseEntity.ok(response);
//
//            //return ResponseEntity.ok(deleteMen);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
    }

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
// and הצגת הנתונים של ההתאמות דרך menId
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

        // update preferences and save
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


    @GetMapping("/men/searchMatches")
    public List<Women> searchMatches(@RequestParam int menId) {
        return menService.findMatchesByMenPreferences(menId);
    }
    }






