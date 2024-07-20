package org.example.service;

import org.example.model.Men;
import java.util.List;
import java.util.Optional;


public interface MenService {
    List<Men> getAllMen();
    Men getMenById(int id);
    Men addMen(Men men);
    List<Men> searchMen(String term, String criteria);
    Men deleteMen(int id);

}

