package org.example.repository;


import org.example.model.Woman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface WomenRepository extends JpaRepository<Woman, Integer> {
//    List<Woman> findByFirstNameOrAgeOrLocationOrLastNameOrStatusOrStyle(String firstName, Integer age, String location, String lastName, String status, String style);
//
//}

@Repository
public interface WomenRepository extends JpaRepository<Woman, Integer> {
    List<Woman> findByFirstName(String firstName);
    List<Woman> findByAge(Integer age);
    List<Woman> findByLocation(String location);
    List<Woman> findByLastName(String lastName);
    List<Woman> findByStatus(String status);
    List<Woman> findByStyle(String style);
    List<Woman> findByHeight(Float height);

}
