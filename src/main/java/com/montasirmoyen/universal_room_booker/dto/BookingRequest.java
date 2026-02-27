package com.montasirmoyen.universal_room_booker.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bookedBy;
}
