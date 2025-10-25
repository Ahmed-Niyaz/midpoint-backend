package com.project.midpoint.DTOs;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GroupResponse {
    private Long id;
    private String name;
    private Long ownerId;
    private String ownerName;
    private Integer memberCount;
    private LocalDateTime createdAt;
    private List<MemberResponse> members;
}