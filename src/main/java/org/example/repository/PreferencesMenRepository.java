package org.example.repository;

import org.example.model.PreferencesMen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesMenRepository extends JpaRepository<PreferencesMen, Integer> {
}