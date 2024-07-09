package org.example.service;

import org.example.model.Men;
import org.example.model.Woman;

import java.util.List;


public interface MenService {
    List<Men> getAllMen();
    Men getMenById(int id);
    Men addMen(Men men);
    List<Men> searchMen(String term, String criteria);
    Men deleteMen(int id);

}

