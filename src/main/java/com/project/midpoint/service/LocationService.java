package com.project.midpoint.service;

import com.project.midpoint.DTOs.LocationRequest;
import com.project.midpoint.DTOs.LocationResponse;
import com.project.midpoint.model.UserLocations;
import com.project.midpoint.model.Users;


import com.project.midpoint.repository.UserLocationsRepository;
import com.project.midpoint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserLocationsRepository userLocationsRepository;
    private final UserRepository userRepository;

    @Transactional
    public LocationResponse shareLocation(Long groupId, Long userId, LocationRequest request) {
        // Check if location already exists for this user in this group
        UserLocations location = userLocationsRepository
                .findByUserIdAndGroupId(userId, groupId)
                .orElse(new UserLocations());

        // Update or create location
        location.setUserId(userId);
        location.setGroupId(groupId);
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());

        UserLocations savedLocation = userLocationsRepository.save(location);

        // Fetch user details for response
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(savedLocation, user);
    }

    public List<LocationResponse> getGroupLocations(Long groupId) {
        List<UserLocations> locations = userLocationsRepository.findByGroupId(groupId);

        return locations.stream()
                .map(location -> {
                    Users user = userRepository.findById(location.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return mapToResponse(location, user);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUserLocation(Long groupId, Long userId) {
        userLocationsRepository.deleteByUserIdAndGroupId(userId, groupId);
    }

    private LocationResponse mapToResponse(UserLocations location, Users user) {
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setLatitude(location.getLatitude());
        response.setLongitude(location.getLongitude());
        response.setSharedAt(location.getSharedAt());
        return response;
    }
}