package org.example.repository;


import org.example.model.Men;
import org.example.model.Women;
import org.example.model.Women;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WomenRepository extends JpaRepository<Women, Integer> {
    List<Women> findByFirstNameContainingIgnoreCase(String term);
    List<Women> findByFirstName(String firstName);
    List<Women> findByAge(Integer age);
    List<Women> findByLocation(String location);
    List<Women> findByLastName(String lastName);
    List<Women> findByStatus(String status);
    List<Women> findByStyle(String style);
    List<Women> findByHeight(Float height);
}
