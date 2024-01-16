package com.sagor.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sagor.exception.InternalServerException;
import com.sagor.exception.ResourceNotFoundException;
import com.sagor.model.Room;
import com.sagor.repository.RoomRepository;
import com.sagor.service.IRoomService;

@Service
public class RoomServiceImpl implements IRoomService {

	private final RoomRepository roomRepository;

	public RoomServiceImpl(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice)
			throws IOException, SerialException, SQLException {
		Room room = new Room();
		room.setRoomType(roomType);
		room.setRoomPrice(roomPrice);

		if (!file.isEmpty()) {
			byte[] photoByte = file.getBytes();
			Blob photoBlob = new SerialBlob(photoByte);
			room.setPhoto(photoBlob);
		}
		return roomRepository.save(room);
	}

	@Override
	public List<String> getAllRoomTypes() {
		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	@Override
	public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if (theRoom.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, room not found!");
		}
		Blob photoBlob = theRoom.get().getPhoto();
		if (photoBlob != null) {
			return photoBlob.getBytes(1, (int) photoBlob.length());
		}
		return null;
	}

	@Override
	public void deleteRoom(Long roomId) {
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if (theRoom.isPresent()) {
			roomRepository.deleteById(roomId);
		}

	}

	@Override
	public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoByte) {
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
		if (roomType != null)
			room.setRoomType(roomType);
		if (roomPrice != null)
			room.setRoomPrice(roomPrice);
		if (photoByte != null && photoByte.length > 0) {
			try {
				room.setPhoto(new SerialBlob(photoByte));
			} catch (SQLException e) {
				throw new InternalServerException("Error updating room");
			}
		}
		return roomRepository.save(room);
	}

	@Override
	public Optional<Room> getRoomById(Long roomId) {
		return Optional.of(roomRepository.findById(roomId).get());
	}

}
