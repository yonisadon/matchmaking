package org.example.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class id {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // הגדרת ID כמנוהל ע"י מסד הנתונים
    private int id;
}
