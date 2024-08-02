package org.example.repository;

import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.Woman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferencesMenRepository extends JpaRepository<PreferencesMen, Integer> {

    Optional<PreferencesMen> findByMenId(int menId);


    @Repository
    public interface WomenRepository extends JpaRepository<Woman, Integer> {
    }

    @Repository
    public interface MenRepository extends JpaRepository<Men, Integer> {
    }

    //Optional<PreferencesMen> findByAll(int menId);

    //Optional<PreferencesMen> findByAll();

}