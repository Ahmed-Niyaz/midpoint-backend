package com.project.midpoint.repository;

import com.project.midpoint.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenuesRepository extends JpaRepository<Venues, Long> {

    List<Venues> findByGroupId(Long groupId);

    @Modifying
    void deleteByGroupId(Long groupId);  // ADD THIS if not already present
}