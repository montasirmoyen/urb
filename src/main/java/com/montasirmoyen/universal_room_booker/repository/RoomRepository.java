package com.montasirmoyen.universal_room_booker.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.montasirmoyen.universal_room_booker.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // inherited operations
}
