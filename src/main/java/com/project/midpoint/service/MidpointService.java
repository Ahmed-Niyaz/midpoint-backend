package com.project.midpoint.service;

import com.project.midpoint.DTOs.MidpointResponse;
import com.project.midpoint.DTOs.VenueResponse;
import com.project.midpoint.model.UserLocations;
import com.project.midpoint.model.Venues;
import com.project.midpoint.repository.UserLocationsRepository;
import com.project.midpoint.repository.VenuesRepository;
import com.project.midpoint.repository.VotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MidpointService {

    private final UserLocationsRepository userLocationsRepository;
    private final VenuesRepository venuesRepository;
    private final VotesRepository votesRepository;
    private final GooglePlacesService googlePlacesService; // You'll need to implement this

    public MidpointResponse calculateMidpoint(Long groupId) {
        List<UserLocations> locations = userLocationsRepository.findByGroupId(groupId);

        if (locations.isEmpty()) {
            throw new RuntimeException("No locations shared in this group");
        }

        // Calculate average latitude and longitude
        BigDecimal avgLat = locations.stream()
                .map(UserLocations::getLatitude)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(locations.size()), 8, RoundingMode.HALF_UP);

        BigDecimal avgLon = locations.stream()
                .map(UserLocations::getLongitude)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(locations.size()), 8, RoundingMode.HALF_UP);

        MidpointResponse response = new MidpointResponse();
        response.setLatitude(avgLat);
        response.setLongitude(avgLon);
        response.setTotalLocations(locations.size());

        return response;
    }

    public List<VenueResponse> getGroupVenues(Long groupId) {
        List<Venues> venues = venuesRepository.findByGroupId(groupId);

        return venues.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VenueResponse> refreshVenues(Long groupId, String category, Integer radius) {
        // Calculate midpoint first
        MidpointResponse midpoint = calculateMidpoint(groupId);

        // Delete existing venues for this group
        venuesRepository.deleteByGroupId(groupId);

        // Fetch new venues from Google Places API
        List<Venues> newVenues = googlePlacesService.fetchNearbyVenues(
                groupId,
                midpoint.getLatitude(),
                midpoint.getLongitude(),
                category,
                radius
        );

        // Save all venues
        List<Venues> savedVenues = venuesRepository.saveAll(newVenues);

        return savedVenues.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private VenueResponse mapToResponse(Venues venue) {
        VenueResponse response = new VenueResponse();
        response.setId(venue.getId());
        response.setPlaceId(venue.getPlaceId());
        response.setName(venue.getName());
        response.setAddress(venue.getAddress());
        response.setLatitude(venue.getLatitude());
        response.setLongitude(venue.getLongitude());
        response.setRating(venue.getRating());
        response.setCategory(venue.getCategory());
        response.setPhotoReference(venue.getPhotoReference());
        response.setVoteCount(votesRepository.countByVenueId(venue.getId()));
        response.setAddedAt(venue.getAddedAt());
        return response;
    }
}