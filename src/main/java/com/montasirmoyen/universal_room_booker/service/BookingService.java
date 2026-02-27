package com.montasirmoyen.universal_room_booker.service;

import org.springframework.stereotype.Service;

import com.montasirmoyen.universal_room_booker.dto.BookingRequest;
import com.montasirmoyen.universal_room_booker.entity.Booking;
import com.montasirmoyen.universal_room_booker.entity.Room;
import com.montasirmoyen.universal_room_booker.repository.BookingRepository;
import com.montasirmoyen.universal_room_booker.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

// skipping this and putting logic in the controller, you would break the separation of concerns
@Service
@RequiredArgsConstructor // this auto injects the repo's
public class BookingService {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public Booking createBooking(BookingRequest request) {
        // now that we have room's repository, we can:
        Room room = roomRepository.findById(request.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("Room not found ID: " + request.getRoomId()));

        // general validation
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // check for overlaps
        List<Booking> overlapping = bookingRepository.findConflictingBookings(
            room.getId(),
            request.getStartTime(),
            request.getEndTime()
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Room is already booked for this time range");
        }

        // mapping dto to entity
        Booking booking = Booking.builder()
                .room(room)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .bookedBy(request.getBookedBy())
                .build();

        return bookingRepository.save(booking);
    }
}
