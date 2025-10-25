package com.project.midpoint.controller;

import com.project.midpoint.DTOs.LocationRequest;
import com.project.midpoint.DTOs.LocationResponse;
import com.project.midpoint.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/{groupId}/location")
    public ResponseEntity<LocationResponse> shareLocation(
            @PathVariable Long groupId,
            @RequestBody LocationRequest request,
            @RequestHeader("userId") Long userId) {
        return ResponseEntity.ok(locationService.shareLocation(groupId, userId, request));
    }

    @GetMapping("/{groupId}/locations")
    public ResponseEntity<List<LocationResponse>> getGroupLocations(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(locationService.getGroupLocations(groupId));
    }

    @DeleteMapping("/{groupId}/locations")
    public ResponseEntity<String> deleteMyLocation(
            @PathVariable Long groupId,
            @RequestHeader("userId") Long userId) {
        locationService.deleteUserLocation(groupId, userId);
        return ResponseEntity.ok("Location deleted successfully");
    }
}