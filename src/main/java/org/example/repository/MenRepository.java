package org.example.repository;

import org.example.model.Men;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenRepository extends JpaRepository<Men, Integer> {
}
        // ניתן להוסיף כאן שיטות נוספות לקריאה, כתיבה ועריכה של נתונים
        // לדוגמה:
        // Men findByName(String name);
        // List<Men> findByAgeGreaterThan(int age);



