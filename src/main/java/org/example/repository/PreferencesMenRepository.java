package org.example.repository;

import org.example.model.Men;
import org.example.model.PreferencesMen;
import org.example.model.PreferencesWomen;
import org.example.model.Women;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferencesMenRepository extends JpaRepository<PreferencesMen, Integer> {

    Optional<PreferencesMen> findByMenId(int menId);
    Optional<PreferencesMen> findByIdPreferencesMen(int idPreferencesMen);


    @Repository
    public interface WomenRepository extends JpaRepository<Women, Integer> {
    }

    @Repository
    public interface MenRepository extends JpaRepository<Men, Integer> {
    }

    //Optional<PreferencesMen> findByAll(int menId);

    //Optional<PreferencesMen> findByAll();

}
