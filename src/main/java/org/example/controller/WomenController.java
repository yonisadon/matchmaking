package org.example.controller;

import org.example.DTO.PreferencesMenDTO;
import org.example.DTO.PreferencesWomenDTO;
import org.example.model.*;
import org.example.model.Women;
import org.example.repository.PreferencesWomenRepository;
import org.example.repository.WomenRepository;
import org.example.service.AgeCalculator;
import org.example.service.WomenPreferencesServiceImpl;
import org.example.service.WomenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class WomenController {
    @Autowired
    private WomenRepository womenRepository;
    private final WomenServiceImpl womenService;
    private final WomenPreferencesServiceImpl womenPreferencesService;
    private final PreferencesWomenRepository preferencesWomenRepository;
    private static final Logger logger = LoggerFactory.getLogger(WomenController.class);


    @Autowired
    public WomenController(WomenServiceImpl womenService, WomenPreferencesServiceImpl womenPreferencesService, PreferencesWomenRepository preferencesWomenRepository) {
        this.womenService = womenService;
        this.womenPreferencesService = womenPreferencesService;
        this.preferencesWomenRepository = preferencesWomenRepository;
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
    public ResponseEntity<Map<String, Object>> saveWomenData(@RequestBody Women women) {
        try {
            System.out.println("Received POST request with data: " + women);
            System.out.println(women.getId());
            // קבל את התאריך הנוכחי
            LocalDate today = LocalDate.now();

            // בדוק אם תאריך הלידה לא NULL
            if (women.getDateOfBirth() != null) {
                // חישוב גיל בעזרת פונקציה
                int age = AgeCalculator.calculateAge(women.getDateOfBirth(), today);
                women.setAge(age); // עדכן את גיל האדם בטבלה
            } else {
                // טיפול במצב שבו תאריך הלידה אינו מוגדר
                System.out.println("Date of birth is null for man ID: " + women.getId());
            }
            Women savedWoman = womenService.addWomen(women);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", savedWoman.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

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
}



