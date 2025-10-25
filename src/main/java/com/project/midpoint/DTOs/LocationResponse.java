package com.project.midpoint.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime sharedAt;
}