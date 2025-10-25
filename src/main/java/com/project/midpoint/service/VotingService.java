package com.project.midpoint.service;

import com.project.midpoint.DTOs.VoteResponse;
import com.project.midpoint.model.Users;
import com.project.midpoint.model.Venues;
import com.project.midpoint.model.Votes;
import com.project.midpoint.repository.UserRepository;
import com.project.midpoint.repository.VenuesRepository;
import com.project.midpoint.repository.VotesRepository;
import com.project.midpoint.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotesRepository votesRepository;
    private final VenuesRepository venuesRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final EntityManager entityManager;  // Add this

    @Transactional
    public VoteResponse voteForVenue(Long venueId, Long userId) {
        // Check if venue exists
        Venues venue = venuesRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Long groupId = venue.getGroupId();

        // Check if user already has a vote in this group
        Optional<Votes> existingVote = votesRepository.findByUserIdAndGroupId(userId, groupId);

        if (existingVote.isPresent()) {
            Votes oldVote = existingVote.get();

            // If voting for the same venue, throw error
            if (oldVote.getVenueId().equals(venueId)) {
                throw new RuntimeException("You have already voted for this venue");
            }

            // Delete the old vote and flush to database immediately
            votesRepository.delete(oldVote);
            entityManager.flush();  // Force the delete to execute NOW
        }

        // Create new vote
        Votes vote = new Votes();
        vote.setUserId(userId);
        vote.setVenueId(venueId);
        vote.setGroupId(groupId);

        Votes savedVote = votesRepository.save(vote);

        // Fetch user details
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(savedVote, user, venue);
    }

    @Transactional
    public void deleteVote(Long voteId, Long userId) {
        Votes vote = votesRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("Vote not found"));

        // Ensure user can only delete their own vote
        if (!vote.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own votes");
        }

        votesRepository.deleteById(voteId);
    }

    public List<VoteResponse> getGroupVotes(Long groupId) {
        List<Votes> votes = votesRepository.findByGroupId(groupId);

        return votes.stream()
                .map(vote -> {
                    Users user = userRepository.findById(vote.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Venues venue = venuesRepository.findById(vote.getVenueId())
                            .orElseThrow(() -> new RuntimeException("Venue not found"));
                    return mapToResponse(vote, user, venue);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void clearAllVotes(Long groupId, Long userId) {
        // Verify user is group owner
        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getOwnerId().equals(userId)) {
            throw new RuntimeException("Only group owner can clear all votes");
        }

        votesRepository.deleteByGroupId(groupId);
    }

    private VoteResponse mapToResponse(Votes vote, Users user, Venues venue) {
        VoteResponse response = new VoteResponse();
        response.setId(vote.getId());
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setVenueId(venue.getId());
        response.setVenueName(venue.getName());
        response.setVotedAt(vote.getVotedAt());
        return response;
    }


}