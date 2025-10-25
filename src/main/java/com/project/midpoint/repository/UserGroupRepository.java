package com.project.midpoint.repository;

import com.project.midpoint.model.UserGroups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroups, Long> {
    List<UserGroups> findByUserId(Long userId);
    List<UserGroups> findByGroupId(Long groupId);
    Optional<UserGroups> findByUserIdAndGroupId(Long userId, Long groupId);
    Integer countByGroupId(Long groupId);
    void deleteByGroupId(Long groupId);
}