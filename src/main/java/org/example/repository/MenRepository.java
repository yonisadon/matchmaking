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
    //List<Men> findByID(int id);


//    @Query("SELECT new org.example.dto.ManMatchDto(" +
//            "m.id, m.firstName, m.age, m.height, m.status, m.style, " +
//            "w.id, w.firstName, w.age, w.height, w.status, w.style) " +
//            "FROM Men m " +
//            "JOIN Women w " +
//            "WHERE w.age BETWEEN m.preferenceAgeFrom AND m.preferenceAgeTo " +
//            "AND w.height BETWEEN m.preferenceHeightFrom AND m.preferenceHeightTo " +
//            "AND w.style = m.style " +
//            "AND w.status = m.status")
//    List<MenMatchDto> findMatchesForMen();




    //List<Men> findAll();




}


