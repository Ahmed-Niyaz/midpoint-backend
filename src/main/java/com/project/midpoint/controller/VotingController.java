package com.project.midpoint.controller;

import com.project.midpoint.DTOs.VoteResponse;
import com.project.midpoint.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @PostMapping("/api/venues/{venueId}/vote")
    public ResponseEntity<VoteResponse> voteForVenue(
            @PathVariable Long venueId,
            @RequestHeader("userId") Long userId) {
        return ResponseEntity.ok(votingService.voteForVenue(venueId, userId));
    }

    @GetMapping("/api/groups/{groupId}/votes")
    public ResponseEntity<List<VoteResponse>> getGroupVotes(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(votingService.getGroupVotes(groupId));
    }

    @DeleteMapping("/api/votes/{voteId}")
    public ResponseEntity<String> deleteVote(
            @PathVariable Long voteId,
            @RequestHeader("userId") Long userId) {
        votingService.deleteVote(voteId, userId);
        return ResponseEntity.ok("Vote deleted successfully");
    }

    @DeleteMapping("/api/groups/{groupId}/votes")
    public ResponseEntity<String> clearAllVotes(
            @PathVariable Long groupId,
            @RequestHeader("userId") Long userId) {
        votingService.clearAllVotes(groupId, userId);
        return ResponseEntity.ok("All votes cleared successfully");
    }
}