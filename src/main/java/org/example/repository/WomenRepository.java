package org.example.repository;

import org.example.model.Woman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WomenRepository extends JpaRepository<Woman, Integer> {
    // פונקציות מותאמות אישית במידת הצורך
}

