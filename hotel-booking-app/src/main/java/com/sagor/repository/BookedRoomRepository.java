package com.sagor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.BookedRoom;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {

}
