package org.example.controller;

import org.example.model.Men;
import org.example.service.MenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/men")
public class MenController {

    private final MenServiceImpl menService;
    private static final Logger logger = LoggerFactory.getLogger(WomenController.class);

    @Autowired
    public MenController(MenServiceImpl menService) {
        this.menService = menService;
    }

//    @GetMapping
//    public ResponseEntity<List<Men>> getAllMen() {
//        List<Men> menList = menService.getAllMen();
//        return new ResponseEntity<>(menList, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Men> getMenById(@PathVariable("id") int id) {
        Men men = menService.getMenById(id);
        if (men != null) {
            return new ResponseEntity<>(men, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Men> addMen(@RequestBody Men men) {
        System.out.println("Received POST request with data: " + men);
        menService.addMen(men);
        return new ResponseEntity<>(men, HttpStatus.CREATED);

    }

    @GetMapping("/search")
    public List<Men> searchMen(@RequestParam String term, @RequestParam String criteria) {
        List<Men> results = menService.searchMen(term, criteria);
        logger.info("Received search request for women with term: {} and criteria: {}", term, criteria);
        System.out.println("Results: " + results);
        return results;
        //return menService.searchMen(term, criteria);
    }
}


