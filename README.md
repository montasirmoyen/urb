# Universal Room Booker

This was part of a challenge, learn more in my dev blog: https://montasirmoyen.com/blog/urb

What it is: A backend system for managing room reservations. You can create rooms with capacity limits and handle booking requests while preventing overlapping reservations.

## Prerequisites

- Docker and Docker Compose (for Option 1), OR
- Java 21 or higher (for Option 2)
- Maven 3.6+ (for Option 2)
- PostgreSQL 12+ (for Option 2)

## Setup
 
### Option 1: Using Docker (Recommended)

The easiest way to get started. Make sure you have Docker and Docker Compose installed.

```bash
docker-compose up
```

This will start PostgreSQL and the application. The server will be available at `http://localhost:8080`

### Option 2: Manual Setup

#### 1. Database Setup

Create a PostgreSQL database for the application:

```sql
CREATE DATABASE urb_db;
```

#### 2. Configure Environment

Set the database password (DB_PASSWORD) as an environment variable:

```bash
export DB_PASSWORD=your_postgres_password
```

Or update `src/main/resources/application.yaml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/urb_db
    username: postgres
    password: your_password
```

#### 3. Build the Project

```bash
mvn clean install
```

## Running the Application

**If using Docker (Option 1):**

The application starts automatically when you run `docker-compose up`. Skip to API Endpoints below.

**If using Manual Setup (Option 2):**

Start the application with:

```bash
mvn spring-boot:run
```

The server will be available at `http://localhost:8080`

## API Endpoints

### Rooms

**Create a room**
```
POST /api/rooms
Content-Type: application/json

{
  "name": "Conference Room A",
  "capacity": 10
}
```

**Get all rooms**
```
GET /api/rooms
```

### Bookings

**Create a booking**
```
POST /api/bookings
Content-Type: application/json

{
  "roomId": 1,
  "startTime": "2026-02-26T14:00:00",
  "endTime": "2026-02-26T15:00:00",
  "bookedBy": "John Doe"
}
```

The system will reject overlapping bookings for the same room.

## Project Structure

```
src/main/java/com/montasirmoyen/universal_room_booker/
├── UniversalRoomBookerApplication.java    # Main application class
├── controller/                             # REST controllers
│   ├── RoomController.java
│   └── BookingController.java
├── service/                                # Business logic
│   └── BookingService.java
├── entity/                                 # JPA entities
│   ├── Room.java
│   └── Booking.java
├── repository/                             # Database access
│   ├── RoomRepository.java
│   └── BookingRepository.java
├── dto/                                    # Data transfer objects
│   └── BookingRequest.java
└── exception/                              # Exception handling
    └── GlobalExceptionHandler.java
```

## Tech Stack

- Spring Boot 3.5.11
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Jakarta Validation
- JUnit 5