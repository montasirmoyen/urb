package com.montasirmoyen.universal_room_booker.service;

import org.springframework.stereotype.Service;

import com.montasirmoyen.universal_room_booker.entity.Booking;
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
    public Booking createBooking(Booking booking) {
        // general validation
        if (booking.getEndTime().isBefore(booking.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // check for overlaps
        List<Booking> overlapping = bookingRepository.findConflictingBookings(
            booking.getRoom().getId(),
            booking.getStartTime(),
            booking.getEndTime()
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Room is already booked for this time range");
        }

        return bookingRepository.save(booking);
    }
}
