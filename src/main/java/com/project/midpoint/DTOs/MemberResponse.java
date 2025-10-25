package com.project.midpoint.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MemberResponse {
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime joinedAt;
}