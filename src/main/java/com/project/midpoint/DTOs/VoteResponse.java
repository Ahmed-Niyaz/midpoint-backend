package com.project.midpoint.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private Long venueId;
    private String venueName;
    private LocalDateTime votedAt;
}