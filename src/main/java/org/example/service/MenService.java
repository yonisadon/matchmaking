package org.example.service;

import org.example.model.Men;

import java.util.List;


public interface MenService {
    List<Men> getAllMen();
    Men getMenById(int id);
    Men addMen(Men men);
}

