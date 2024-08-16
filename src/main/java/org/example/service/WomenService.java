package org.example.service;

import org.example.model.Women;
import org.example.model.Women;

import java.util.List;

public interface WomenService {
    List<Women> getAllWomen();
    Women getWomanById(int id);
    Women addWomen(Women women);

    List<Women> searchWomen(String term, String criteria);

    Women deleteWomen(int id);
}

