package org.example.job;

import org.example.model.Men;
import org.example.repository.MenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenAgeUpdateJob {

    @Autowired
    private MenRepository menRepository;

    @Autowired
    private AgeUpdateService<Men> ageUpdateService;

    @Scheduled(cron = "0 0 1 1 * ?")
    public void updateMenAges() {
        List<Men> menList = menRepository.findAll();
        ageUpdateService.updateAges(menList);
        menRepository.saveAll(menList);
    }
}