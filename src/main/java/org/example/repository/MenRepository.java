package org.example.repository;

import org.example.model.Men;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenRepository extends JpaRepository<Men, Integer> {

    List<Men> findByFirstNameContainingIgnoreCase(String term);
    List<Men> findByLastNameContainingIgnoreCase(String term);
    List<Men> findByFirstName(String firstName);
    List<Men> findByAge(Integer age);
    List<Men> findByLocation(String location);
    List<Men> findByLastName(String lastName);
    List<Men> findByStatus(String status);
    List<Men> findByStyle(String style);
    List<Men> findByHeight(Float height);
   // List<Men> findAll();




}


