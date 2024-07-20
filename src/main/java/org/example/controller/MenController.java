package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.repository.MenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;

import org.example.service.MenServiceImpl;
import org.example.service.PreferencesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/men")
public class MenController {
    @Autowired
    private MenRepository menRepository;
    private final MenServiceImpl menService;
    private final PreferencesServiceImpl preferencesService;
    private static final Logger logger = LoggerFactory.getLogger(MenController.class);


@Autowired
public MenController(MenServiceImpl menService, PreferencesServiceImpl preferencesService, ObjectMapper objectMapper) {
    this.menService = menService;
    this.preferencesService = preferencesService;
}

//    @GetMapping("/searchAll")
//    public List<Men> getAllMen() {
//        List<Men> menList = menService.getAllMen();
//        System.out.println("Returning number of records: " + menList.size());
//        List<Men> menRepositoryList = menRepository.findAll();
//        System.out.println(menRepositoryList);
//
//        return menList;
//    }
    @GetMapping("/searchAll")
    public ResponseEntity<List<Men>> getAllMen() {
        try {
            List<Men> menList = menRepository.findAll();
            return ResponseEntity.ok(menList);
        } catch (Exception e) {
            // טיפול בשגיאות
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Men> getMenById(@PathVariable("id") int id) {
        Men men = menService.getMenById(id);
        if (men != null) {
            return new ResponseEntity<>(men, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<Men> searchMen(@RequestParam String term, @RequestParam String criteria) {
        List<Men> results = menService.searchMen(term, criteria);

        logger.info("Received search request for women with term: {} and criteria: {}", term, criteria);
        //System.out.println("Results: " + results);
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

@PostMapping("/saveMen")
public ResponseEntity<Map<String, Object>> saveMenData(@RequestBody Men men) {
    try {
        System.out.println("Received POST request with data: " + men);
        System.out.println(men.getId());
        System.out.println("firstName: " + men.getFirstName());
        System.out.println("lastName: " + men.getLastName());
        System.out.println("age: " + men.getAge());
        System.out.println("height: " + men.getHeight());
        System.out.println("location: " + men.getLocation());
        System.out.println("style: " + men.getStyle());
        System.out.println("seeking: " + men.getSeeking());
        System.out.println("status: " + men.getStatus());

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

    @PostMapping("/savePreferences")
    public ResponseEntity<Map<String, Object>> savePreferences(@RequestBody PreferencesMen preferencesMen) {
        try {
            System.out.println("1: " + preferencesMen.getPreferredRegion());
            System.out.println("2: " + preferencesMen.getPreferredCommunity());
            System.out.println("3: " + preferencesMen.getPreferredStatus());
            System.out.println("4: " + preferencesMen.getHandkerchiefOrWig());
            System.out.println("5: " + preferencesMen.getKosherOrNonKosherDevice());
            System.out.println("6: " + preferencesMen.getSmokerOrNonSmoker());

            // לוודא ש-idMen מועבר ונשמר בצורה נכונה
            Men men = preferencesMen.getMen();
            if (men == null || men.getId() == 0) {
                throw new IllegalArgumentException("Men object or idMen is missing");
            }

            PreferencesMen savedPreferences = preferencesService.addPreferencesMen(preferencesMen);
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


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletemen(@PathVariable int id) {
        try {
            Men deleteMen = menService.deleteMen(id);
            return ResponseEntity.ok(deleteMen);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMen(@PathVariable int id, @RequestBody Men updateMen) {
        try {
            Men updated = menService.updateMen(id, updateMen);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


