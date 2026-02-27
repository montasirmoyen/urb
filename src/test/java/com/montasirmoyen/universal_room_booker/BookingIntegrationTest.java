package com.montasirmoyen.universal_room_booker;

import com.montasirmoyen.universal_room_booker.dto.BookingRequest;
import com.montasirmoyen.universal_room_booker.entity.Room;
import com.montasirmoyen.universal_room_booker.repository.RoomRepository;
import com.montasirmoyen.universal_room_booker.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional // rolls back the db after each test so it stays clean
public class BookingIntegrationTest {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomRepository roomRepository;

    private Long testRoomId;

    @BeforeEach
    void setUp() {
        // creates a room to use for tests
        Room room = Room.builder()
                .name("Conference Room A")
                .capacity(10)
                .build();
        testRoomId = roomRepository.save(room).getId();
    }

    @Test
    void shouldPreventOverlappingBookings() {
        // creates a successful initial booking 2pm - 3pm
        BookingRequest firstRequest = new BookingRequest();
        firstRequest.setRoomId(testRoomId);
        firstRequest.setStartTime(LocalDateTime.now().plusHours(2));
        firstRequest.setEndTime(LocalDateTime.now().plusHours(3));
        firstRequest.setBookedBy("User A");

        assertNotNull(bookingService.createBooking(firstRequest));

        // attempts an overlapping booking 2:30pm - 3:30pm
        BookingRequest overlappingRequest = new BookingRequest();
        overlappingRequest.setRoomId(testRoomId);
        overlappingRequest.setStartTime(LocalDateTime.now().plusHours(2).plusMinutes(30));
        overlappingRequest.setEndTime(LocalDateTime.now().plusHours(3).plusMinutes(30));
        overlappingRequest.setBookedBy("User B");

        // verifys that the system throws the conflict exception
        assertThrows(IllegalStateException.class, () -> {
            bookingService.createBooking(overlappingRequest);
        });
    }
}