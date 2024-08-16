package org.example.repository;

import org.example.model.Men;
import org.example.model.PreferencesWomen;
import org.example.model.Women;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferencesWomenRepository extends JpaRepository<PreferencesWomen, Integer> {

    Optional<PreferencesWomen> findByWomenId(int womenId);

    @Repository
    public interface WomenRepository extends JpaRepository<Women, Integer> {
    }

    @Repository
    public interface MenRepository extends JpaRepository<Men, Integer> {
    }
}
