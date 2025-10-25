package com.project.midpoint.service;

import com.project.midpoint.model.Venues;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GooglePlacesService {

    private final Random random = new Random();

    // Dummy venue data
    private final List<DummyVenue> DUMMY_VENUES = Arrays.asList(
            new DummyVenue("ChIJ1", "The Urban Café", "restaurant", 4.5),
            new DummyVenue("ChIJ2", "Pizza Paradise", "restaurant", 4.2),
            new DummyVenue("ChIJ3", "Burger Junction", "restaurant", 4.0),
            new DummyVenue("ChIJ4", "Sushi World", "restaurant", 4.7),
            new DummyVenue("ChIJ5", "Pasta House", "restaurant", 4.3),
            new DummyVenue("ChIJ6", "Coffee Corner", "cafe", 4.6),
            new DummyVenue("ChIJ7", "Tea Garden", "cafe", 4.4),
            new DummyVenue("ChIJ8", "Brewhouse", "cafe", 4.1),
            new DummyVenue("ChIJ9", "The Food Court", "restaurant", 3.9),
            new DummyVenue("ChIJ10", "Fine Dine Restaurant", "restaurant", 4.8),
            new DummyVenue("ChIJ11", "Street Food Hub", "restaurant", 4.2),
            new DummyVenue("ChIJ12", "Vegan Delights", "restaurant", 4.5),
            new DummyVenue("ChIJ13", "BBQ Nation", "restaurant", 4.4),
            new DummyVenue("ChIJ14", "Asian Fusion", "restaurant", 4.3),
            new DummyVenue("ChIJ15", "Mediterranean Grill", "restaurant", 4.6)
    );

    public List<Venues> fetchNearbyVenues(Long groupId, BigDecimal latitude,
                                          BigDecimal longitude, String category, Integer radius) {

        List<Venues> venues = new ArrayList<>();

        // Filter venues by category
        List<DummyVenue> filteredVenues = DUMMY_VENUES.stream()
                .filter(v -> v.category.equalsIgnoreCase(category))
                .toList();

        // If no matching category, use all venues
        if (filteredVenues.isEmpty()) {
            filteredVenues = DUMMY_VENUES;
        }

        // Generate 5-10 random venues
        int venueCount = 5 + random.nextInt(6); // 5 to 10 venues

        for (int i = 0; i < Math.min(venueCount, filteredVenues.size()); i++) {
            DummyVenue dummyVenue = filteredVenues.get(i);

            // Generate random coordinates near the midpoint (within radius)
            // Convert radius from meters to approximate degrees (very rough: 1 degree ≈ 111km)
            double radiusInDegrees = radius / 111000.0;

            BigDecimal venueLat = latitude.add(
                    BigDecimal.valueOf((random.nextDouble() - 0.5) * radiusInDegrees * 2)
                            .setScale(8, RoundingMode.HALF_UP)
            );

            BigDecimal venueLon = longitude.add(
                    BigDecimal.valueOf((random.nextDouble() - 0.5) * radiusInDegrees * 2)
                            .setScale(8, RoundingMode.HALF_UP)
            );

            // Generate random address
            String address = generateRandomAddress(dummyVenue.name);

            Venues venue = new Venues();
            venue.setGroupId(groupId);
            venue.setPlaceId(dummyVenue.placeId);
            venue.setName(dummyVenue.name);
            venue.setAddress(address);
            venue.setLatitude(venueLat);
            venue.setLongitude(venueLon);
            venue.setRating(BigDecimal.valueOf(dummyVenue.rating));
            venue.setCategory(dummyVenue.category);
            venue.setPhotoReference("photo_ref_" + random.nextInt(1000));

            venues.add(venue);
        }

        return venues;
    }

    private String generateRandomAddress(String venueName) {
        List<String> areas = Arrays.asList(
                "Connaught Place", "Saket", "Hauz Khas", "Nehru Place",
                "Rajouri Garden", "Lajpat Nagar", "Karol Bagh", "Rohini",
                "Dwarka", "Janakpuri", "Vasant Kunj", "Greater Kailash"
        );

        String area = areas.get(random.nextInt(areas.size()));
        int streetNumber = 1 + random.nextInt(500);

        return streetNumber + ", " + area + ", New Delhi, Delhi 110001";
    }

    // Inner class for dummy venue data
    private static class DummyVenue {
        String placeId;
        String name;
        String category;
        double rating;

        DummyVenue(String placeId, String name, String category, double rating) {
            this.placeId = placeId;
            this.name = name;
            this.category = category;
            this.rating = rating;
        }
    }
}