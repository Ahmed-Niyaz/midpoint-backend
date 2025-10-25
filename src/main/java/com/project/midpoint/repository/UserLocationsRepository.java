package com.project.midpoint.repository;

import com.project.midpoint.model.UserLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLocationsRepository extends JpaRepository<UserLocations, Long> {
    List<UserLocations> findByGroupId(Long groupId);
    Optional<UserLocations> findByUserIdAndGroupId(Long userId, Long groupId);
    void deleteByUserIdAndGroupId(Long userId, Long groupId);
    void deleteByGroupId(Long groupId);
}