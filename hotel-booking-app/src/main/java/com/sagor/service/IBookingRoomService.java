package com.sagor.service;

import java.util.List;

import com.sagor.model.BookedRoom;

public interface IBookingRoomService {

	List<BookedRoom> getAllBooking();

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	String saveBooking(Long roomId, BookedRoom bookingRequest);

	void cancleBooking(Long bookingId);

}
