package com.montasirmoyen.universal_room_booker.controller;
 
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.montasirmoyen.universal_room_booker.entity.Room;
import com.montasirmoyen.universal_room_booker.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
