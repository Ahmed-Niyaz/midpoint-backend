package com.project.midpoint.repository;

import com.project.midpoint.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {
}