package com.project.midpoint.repository;

import com.project.midpoint.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotesRepository extends JpaRepository<Votes, Long> {

    Optional<Votes> findByUserIdAndVenueId(Long userId, Long venueId);

    Optional<Votes> findByUserIdAndGroupId(Long userId, Long groupId);

    List<Votes> findByGroupId(Long groupId);

    Integer countByVenueId(Long venueId);


    @Modifying
    void deleteByGroupId(Long groupId);

    @Modifying
    void deleteByUserIdAndGroupId(Long userId, Long groupId);  // ADD THIS
}

