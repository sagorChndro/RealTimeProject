package com.sagor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sagor.annotation.ApiController;
import com.sagor.exception.InvalidBookingRequestException;
import com.sagor.exception.ResourceNotFoundException;
import com.sagor.model.BookedRoom;
import com.sagor.model.Room;
import com.sagor.response.BookingRoomResponse;
import com.sagor.response.RoomResponse;
import com.sagor.service.IBookingRoomService;
import com.sagor.service.IRoomService;

@ApiController
@RequestMapping("/bookings")
public class BookingRoomController {

	private final IBookingRoomService bookingRoomService;
	private final IRoomService roomService;

	public BookingRoomController(IBookingRoomService bookingRoomService, IRoomService roomService) {
		this.bookingRoomService = bookingRoomService;
		this.roomService = roomService;
	}

	@GetMapping("/all-bookings")
	public ResponseEntity<List<BookingRoomResponse>> getAllBookings() {
		List<BookedRoom> bookings = bookingRoomService.getAllBooking();
		List<BookingRoomResponse> bookingRoomResponses = new ArrayList<>();
		for (BookedRoom booking : bookings) {
			BookingRoomResponse bookingResponse = getBookingResponse(booking);
			bookingRoomResponses.add(bookingResponse);
		}
		return ResponseEntity.ok(bookingRoomResponses);
	}

	@GetMapping("/confirmation/{confirmationCode}")
	public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
		try {
			BookedRoom booking = bookingRoomService.findByBookingConfirmationCode(confirmationCode);
			BookingRoomResponse bookingResponse = getBookingResponse(booking);
			return ResponseEntity.ok(bookingResponse);
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest) {
		try {
			String confirmationCode = bookingRoomService.saveBooking(roomId, bookingRequest);
			return ResponseEntity.ok("Room Booked Successfully, Your Confirmation Code is : " + confirmationCode);
		} catch (InvalidBookingRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/booking/{bookingId}/delete")
	public void cancleBooking(@PathVariable Long bookingId) {
		bookingRoomService.cancleBooking(bookingId);
	}

	private BookingRoomResponse getBookingResponse(BookedRoom booking) {
		Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
		RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
		return new BookingRoomResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(),
				booking.getGuestFullName(), booking.getGuestEmail(), booking.getNumOfAdults(),
				booking.getNumOfChildren(), booking.getTotalNumOfGuest(), booking.getBookingConfirmationCode(), room);
	}

}
