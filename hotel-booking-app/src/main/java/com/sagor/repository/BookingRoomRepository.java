package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.BookedRoom;

public interface BookingRoomRepository extends JpaRepository<BookedRoom, Long> {

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> findByRoomId(Long roomId);

}
