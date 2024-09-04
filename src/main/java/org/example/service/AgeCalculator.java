package org.example.service;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    //public static int calculateAge(LocalDate dateOfBirth) {
        public static int calculateAge(LocalDate dateOfBirth, LocalDate today) {
            if (dateOfBirth == null || today == null) {
                throw new IllegalArgumentException("Date of birth and today date must not be null");
            }

            int age = today.getYear() - dateOfBirth.getYear();
            if (today.getMonthValue() < dateOfBirth.getMonthValue() ||
                    (today.getMonthValue() == dateOfBirth.getMonthValue() && today.getDayOfMonth() < dateOfBirth.getDayOfMonth())) {
                age--;
            }
            return age;
        }
    }
//        if (dateOfBirth != null) {
//            LocalDate today = LocalDate.now();
//            return Period.between(dateOfBirth, today).getYears();
//        }
//        return 0; // במקרה של תאריך לא תקין או null
//    }

