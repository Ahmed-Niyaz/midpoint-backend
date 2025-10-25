package com.project.midpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "groupId"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;  // Foreign key to Users

    @Column(nullable = false)
    private Long groupId;  // Foreign key to Groups

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sharedAt;

    @PrePersist
    protected void onCreate() {
        this.sharedAt = LocalDateTime.now();
    }
}