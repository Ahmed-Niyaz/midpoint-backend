# Midpoint Backend

A Spring Boot application that helps groups find the optimal meeting point and nearby venues. Users can share their locations, and the app calculates the geographic midpoint, discovers nearby restaurants and venues, and allows group voting on preferred destinations.

## Features

- **User Authentication**: JWT-based authentication and security with Spring Security
- **Group Management**: Create groups, manage members, and view group details
- **Location Sharing**: Users can share their current or specific locations with groups
- **Midpoint Calculation**: Automatically calculates the geographic center point of all group members
- **Venue Discovery**: Integrates with Google Places API to find nearby restaurants and venues around the calculated midpoint
- **Voting System**: Group members can vote on preferred venues
- **Responsive API**: RESTful endpoints for all core functionality

## Tech Stack

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 25
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT (JJWT 0.12.6)
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven

## Project Structure

```
src/main/java/com/project/midpoint/
├── controller/          # REST API endpoints
│   ├── GroupController
│   ├── MidpointController
│   ├── LocationController
│   ├── VotingController
│   ├── HomeController
│   └── UserController
├── service/            # Business logic layer
│   ├── MidpointService
│   ├── GroupService
│   ├── LocationService
│   ├── VotingService
│   ├── UserService
│   ├── GooglePlacesService
│   ├── JWTService
│   └── MyUserDetailsService
├── model/              # JPA entities
│   ├── Users
│   ├── Groups
│   ├── UserGroups
│   ├── UserLocations
│   ├── Venues
│   ├── Votes
│   └── UserPrincipal
├── repository/         # Data access layer
│   ├── UserRepository
│   ├── GroupRepository
│   ├── UserGroupRepository
│   ├── UserLocationsRepository
│   ├── VenuesRepository
│   └── VotesRepository
├── DTOs/              # Data transfer objects
│   ├── GroupRequest & GroupResponse
│   ├── LocationRequest & LocationResponse
│   ├── MemberResponse
│   ├── MidpointResponse
│   ├── UserResponse
│   ├── VenueResponse
│   ├── VoteResponse
│   └── AddMemberRequest
├── config/            # Configuration classes
│   └── SecurityConfig
├── filter/            # HTTP filters
│   └── JWTFilter
└── MidpointApplication.java
```

## Prerequisites

- Java 25 or higher
- Maven 3.6+
- PostgreSQL 12+
- Google Places API key

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd midpoint-backend
```

### 2. Configure Database

Create a PostgreSQL database:
```sql
CREATE DATABASE midpoint;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/midpoint
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Configure Google Places API

Add your Google Places API key to the `application.properties` file.

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## API Endpoints

### Groups
- `GET /api/groups` - Get all groups for authenticated user
- `GET /api/groups/{groupId}` - Get group details
- `POST /api/groups` - Create a new group
- `POST /api/groups/{groupId}/members` - Add member to group
- `DELETE /api/groups/{groupId}/members/{userId}` - Remove member from group

### Locations
- `POST /api/locations` - Share location with group
- `GET /api/groups/{groupId}/locations` - Get all shared locations in group

### Midpoint & Venues
- `GET /api/groups/{groupId}/midpoint` - Calculate midpoint for group
- `GET /api/groups/{groupId}/venues` - Get nearby venues
- `POST /api/groups/{groupId}/venues/refresh` - Refresh venue list with optional filters
  - Parameters: `category` (default: restaurant), `radius` (default: 5000 meters)

### Voting
- `POST /api/votes` - Cast a vote for a venue
- `GET /api/groups/{groupId}/votes` - Get voting results for group

## Authentication

The application uses JWT (JSON Web Tokens) for authentication. Include the JWT token in the `Authorization` header for protected endpoints:

```
Authorization: Bearer <your_jwt_token>
```

## Database Schema

The application uses Hibernate with `ddl-auto=update` mode for automatic schema management. Key entities include:

- **Users**: User accounts and authentication
- **Groups**: Meeting groups
- **UserGroups**: Many-to-many relationship between users and groups
- **UserLocations**: Location data shared by users
- **Venues**: Discovered restaurants/venues
- **Votes**: User votes on venues

## Configuration

Key properties in `application.properties`:

```properties
spring.application.name=midpoint
spring.datasource.url=jdbc:postgresql://localhost:5432/midpoint
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Development

- **IDE**: IntelliJ IDEA recommended (with Lombok support)
- **DevTools**: Spring Boot DevTools included for hot reload
- **Testing**: JUnit and Spring Security Test included

## Dependencies

Key dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- Spring Data PostgreSQL
- JWT (JJWT)
- Lombok
- Spring DevTools

## License

This project is part of the Midpoint application suite.

## Contributing

When contributing:
1. Follow Spring Framework conventions
2. Use descriptive commit messages
3. Ensure all tests pass before submitting PRs
