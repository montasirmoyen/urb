package com.montasirmoyen.universal_room_booker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montasirmoyen.universal_room_booker.dto.BookingRequest;
import com.montasirmoyen.universal_room_booker.entity.Booking;
import com.montasirmoyen.universal_room_booker.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request) {
        Booking savedBooking = bookingService.createBooking(request);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    } 
}
