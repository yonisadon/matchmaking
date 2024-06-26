package org.example.service;

import org.example.model.Woman;

import java.util.List;

public interface WomenService {
    List<Woman> getAllWomen();
    Woman getWomanById(int id);
    Woman addWoman(Woman woman);
}

