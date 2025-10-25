package com.project.midpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "groupId"})  // Changed from venueId to groupId
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;  // Foreign key to Users

    @Column(nullable = false)
    private Long venueId;  // Foreign key to Venues

    @Column(nullable = false)
    private Long groupId;  // Foreign key to Groups - ADD THIS FIELD

    @Column(nullable = false, updatable = false)
    private LocalDateTime votedAt;

    @PrePersist
    protected void onCreate() {
        this.votedAt = LocalDateTime.now();
    }
}