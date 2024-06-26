package org.example.controller;

import org.example.model.Woman;
import org.example.service.MenServiceImpl;
import org.example.service.WomenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/women")
public class WomenController {
    private final WomenServiceImpl womenService;

    @Autowired
    public WomenController(WomenServiceImpl womenService) {
        this.womenService = womenService;
    }

    @GetMapping
    public ResponseEntity<List<Woman>> getAllWomen() {
        List<Woman> womanList = womenService.getAllWomen();
        return new ResponseEntity<>(womanList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Woman> getWomenById(@PathVariable("id") int id) {
        Woman woman = womenService.getWomanById(id);
        if (woman != null) {
            return new ResponseEntity<>(woman, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Woman> addWomen(@RequestBody Woman woman) {
        System.out.println("Received POST request with data: " + woman);
        System.out.println("firstName: " + woman.getFirstName());
        System.out.println("lastName: " + woman.getLastName());
        System.out.println("age: " + woman.getAge());
        System.out.println("height: " + woman.getHeight());
        System.out.println("location: " + woman.getLocation());
        System.out.println("style: " + woman.getStyle());
        System.out.println("seeking: " + woman.getSeeking());
        System.out.println("status: " + woman.getStatus());
        womenService.addWoman(woman);
        return new ResponseEntity<>(woman, HttpStatus.CREATED);
    }

    // שאר הפעולות כמו getAllWomen, getWomenById וכו'
}


