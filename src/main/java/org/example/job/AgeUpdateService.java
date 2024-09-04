package org.example.job;

import org.example.model.Person;
import org.example.service.AgeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


//@Component
//public class AgeUpdateJob {

//    @Autowired
//    private MenRepository menRepository;
//    private static final Logger logger = LoggerFactory.getLogger(MenController.class);

//    @Scheduled(cron = "0 0 1 1 * ?") // ריצה פעם בחודש ביום הראשון ב-01:00
//    public void updateAges() {
//        List<Men> menList = menRepository.findAll();
//        LocalDate today = LocalDate.now();
//
//        for (Men man : menList) {
//            if (man.getDateOfBirth() == null) {
//                logger.warn("Date of birth is null for man ID: {}", man.getId());
//                continue; // דלג על משתמשים עם תאריך לידה null
//            }
//
//            int newAge = AgeCalculator.calculateAge(man.getDateOfBirth(),today);
//            man.setAge(newAge);
//            menRepository.save(man);
//            logger.info("Updated age for man ID: {} to age: {}", man.getId(), newAge);
//        }
//        for (Men man : menList) {
//            LocalDate dob = man.getDateOfBirth();
//            if (dob != null) {
//                int newAge = AgeCalculator.calculateAge(dob);
//                man.setAge(newAge);
//                menRepository.save(man);
//            } else {
//                // אפשר להוסיף טיפול במקרה של תאריך NULL, כמו רישום ביומן או התעלמות
//                // לדוגמה:
//                System.out.println("Date of birth is null for man ID: " + man.getId());
//            }
//        }


    @Service
    public class AgeUpdateService<T extends Person> {

        private static final Logger logger = LoggerFactory.getLogger(AgeUpdateService.class);

        @Transactional
        public void updateAges(List<T> entities) {
            LocalDate today = LocalDate.now();

            for (T entity : entities) {
                if (entity.getDateOfBirth() == null) {
                    logger.warn("Date of birth is null for ID: {}", entity.getId());
                    continue;
                }

                int newAge = AgeCalculator.calculateAge(entity.getDateOfBirth(), today);
                entity.setAge(newAge);
                logger.info("Updated age for ID: {} to age: {}", entity.getId(), newAge);
            }
        }


    // מתודה להרצה ידנית
    public void runJobManually(List<T> entities) {
        updateAges(entities);
    }
}