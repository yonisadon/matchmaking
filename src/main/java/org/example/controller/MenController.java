package org.example.controller;

import org.example.model.Men;
import org.example.service.MenServiceImpl;
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

    @Autowired
    public MenController(MenServiceImpl menService) {
        this.menService = menService;
    }

    @GetMapping
    public ResponseEntity<List<Men>> getAllMen() {
        List<Men> menList = menService.getAllMen();
        return new ResponseEntity<>(menList, HttpStatus.OK);
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

    @PostMapping
    //public ResponseEntity<Void> addMen(@RequestBody Men men) {
        public ResponseEntity<Men> addMen(@RequestBody Men men) {

        System.out.println("Received POST request with data: " + men);
//        System.out.println("firstName: " + men.getFirstName());
//        System.out.println("lastName: " + men.getLastName());
//        System.out.println("age: " + men.getAge());
//        System.out.println("height: " + men.getHeight());
//        System.out.println("location: " + men.getLocation());
//        System.out.println("style: " + men.getStyle());
//        System.out.println("seeking: " + men.getSeeking());
//        System.out.println("status: " + men.getStatus());
        menService.addMen(men);
        //return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(men, HttpStatus.CREATED);

    }

}
