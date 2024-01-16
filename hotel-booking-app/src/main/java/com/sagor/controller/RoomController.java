package com.sagor.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sagor.annotation.ApiController;
import com.sagor.exception.PhotoRetrievalException;
import com.sagor.exception.ResourceNotFoundException;
import com.sagor.model.BookedRoom;
import com.sagor.model.Room;
import com.sagor.response.RoomResponse;
import com.sagor.service.IRoomService;
import com.sagor.service.impl.BookingServiceImpl;

//@CrossOrigin("http://localhost:5050")
@RequestMapping("/room")
@ApiController
public class RoomController {

	private final IRoomService roomService;
	private final BookingServiceImpl bookingService;

	public RoomController(IRoomService roomService, BookingServiceImpl bookingService) {
		this.roomService = roomService;
		this.bookingService = bookingService;
	}

	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,
			@RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice)
			throws SerialException, IOException, SQLException {

		Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
		RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());

		return ResponseEntity.ok(response);

	}

	@GetMapping("/roomTypes")
	public List<String> getRoomTypes() {
		return roomService.getAllRoomTypes();
	}

	@GetMapping("/getAllRooms")
	public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException, IOException {
		List<Room> rooms = roomService.getAllRooms();
		List<RoomResponse> roomResponses = new ArrayList<>();
		for (Room room : rooms) {
			byte[] photoByte = roomService.getRoomPhotoByRoomId(room.getId());
			if (photoByte != null) {
				String base64Photo = Base64.encodeBase64String(photoByte);
				RoomResponse roomResponse = getRoomResponse(room);
				roomResponse.setPhoto(base64Photo);
				roomResponses.add(roomResponse);
			}
		}
		return ResponseEntity.ok(roomResponses);
	}

	@DeleteMapping("/deleteRoom/{roomId}")
	public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
		roomService.deleteRoom(roomId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{roomId}")
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
			@RequestParam(required = false) String roomType, @RequestParam(required = false) BigDecimal roomPrice,
			@RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {

		byte[] photoByte = photo != null && !photo.isEmpty() ? photo.getBytes()
				: roomService.getRoomPhotoByRoomId(roomId);
		Blob photoBlob = photoByte != null && photoByte.length > 0 ? new SerialBlob(photoByte) : null;
		Room theRoom = roomService.updateRoom(roomId, roomType, roomPrice, photoByte);
		theRoom.setPhoto(photoBlob);
		RoomResponse response = getRoomResponse(theRoom);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getRoom/{roomId}")
	public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId) {
		Optional<Room> theRoom = roomService.getRoomById(roomId);
		return theRoom.map(room -> {
			RoomResponse response = getRoomResponse(room);
			return ResponseEntity.ok(Optional.of(response));
		}).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
	}

	private RoomResponse getRoomResponse(Room room) {
		List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
//		List<BookingRoomResponse> bookingInfo = bookings.stream()
//				.map(booking -> new BookingRoomResponse(booking.getBookingId(), booking.getCheckInDate(),
//						booking.getCheckOutDate(), booking.getBookingConfirmationCode()))
//				.toList();
		byte[] photoByte = null;
		Blob photoBlob = room.getPhoto();
		if (photoBlob != null) {
			try {
				photoByte = photoBlob.getBytes(1, (int) photoBlob.length());
			} catch (SQLException e) {
				throw new PhotoRetrievalException("Error retrieving photo");
			}
		}
		return new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoByte);// bookingInfo
	}

	private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
		return bookingService.getAllBookingsByRoomId(roomId);
	}

}
