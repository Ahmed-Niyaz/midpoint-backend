package com.project.midpoint.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueResponse {
    private Long id;
    private String placeId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal rating;
    private String category;
    private String photoReference;
    private Integer voteCount;
    private LocalDateTime addedAt;
}