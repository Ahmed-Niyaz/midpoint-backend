package com.project.midpoint.controller;

import com.project.midpoint.DTOs.MidpointResponse;
import com.project.midpoint.DTOs.VenueResponse;
import com.project.midpoint.service.MidpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class MidpointController {

    private final MidpointService midpointService;

    @GetMapping("/{groupId}/midpoint")
    public ResponseEntity<MidpointResponse> calculateMidpoint(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(midpointService.calculateMidpoint(groupId));
    }

    @GetMapping("/{groupId}/venues")
    public ResponseEntity<List<VenueResponse>> getGroupVenues(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(midpointService.getGroupVenues(groupId));
    }

    @PostMapping("/{groupId}/venues/refresh")
    public ResponseEntity<List<VenueResponse>> refreshVenues(
            @PathVariable Long groupId,
            @RequestParam(required = false, defaultValue = "restaurant") String category,
            @RequestParam(required = false, defaultValue = "5000") Integer radius) {
        return ResponseEntity.ok(midpointService.refreshVenues(groupId, category, radius));
    }
}