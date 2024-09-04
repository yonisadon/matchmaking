package org.example.job;

import org.example.model.Women;
import org.example.repository.WomenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WomenAgeUpdateJob {

    @Autowired
    private WomenRepository womenRepository;

    @Autowired
    private AgeUpdateService<Women> ageUpdateService;

    @Scheduled(cron = "0 0 1 1 * ?")
    public void updateWomenAges() {
        List<Women> womenList = womenRepository.findAll();
        ageUpdateService.updateAges(womenList);
        womenRepository.saveAll(womenList);
    }
}