package com.project.midpoint.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private BigDecimal latitude;
    private BigDecimal longitude;
}