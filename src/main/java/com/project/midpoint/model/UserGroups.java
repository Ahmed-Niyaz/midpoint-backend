package com.project.midpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_groups", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "groupId"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;  // Foreign key to Users

    @Column(nullable = false)
    private Long groupId;  // Foreign key to Groups

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate() {
        this.joinedAt = LocalDateTime.now();
    }
}