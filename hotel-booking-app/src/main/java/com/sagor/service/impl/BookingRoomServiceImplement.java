package com.sagor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.exception.InvalidBookingRequestException;
import com.sagor.model.BookedRoom;
import com.sagor.model.Room;
import com.sagor.repository.BookingRoomRepository;
import com.sagor.service.IBookingRoomService;
import com.sagor.service.IRoomService;

@Service
public class BookingRoomServiceImplement implements IBookingRoomService {

	private final BookingRoomRepository bookingRepository;
	private final IRoomService roomService;

	public BookingRoomServiceImplement(BookingRoomRepository bookingRepository, IRoomService roomService) {
		this.bookingRepository = bookingRepository;
		this.roomService = roomService;
	}

	public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
		return bookingRepository.findByRoomId(roomId);
	}

	@Override
	public List<BookedRoom> getAllBooking() {
		return bookingRepository.findAll();
	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in date must come before check-out date.");
		}
		Room room = roomService.getRoomById(roomId).get();
		List<BookedRoom> existingBookings = room.getBookings();
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
		if (roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRepository.save(bookingRequest);
		} else {
			throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates.");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		return bookingRepository.findByBookingConfirmationCode(confirmationCode);
	}

	@Override
	public void cancleBooking(Long bookingId) {
		bookingRepository.deleteById(bookingId);

	}

	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))

				);
	}
}
