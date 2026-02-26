package com.montasirmoyen.universal_room_booker.repository;

import com.montasirmoyen.universal_room_booker.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // finds any existing booking that conflicts w/ the requested time range
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.startTime < :endTime " +
           "AND b.endTime > :startTime")
    List<Booking> findConflictingBookings(
        @Param("roomId") Long roomId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
}
