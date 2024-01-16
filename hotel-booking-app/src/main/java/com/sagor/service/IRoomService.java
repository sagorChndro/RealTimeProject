package com.sagor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.sagor.model.Room;

public interface IRoomService {

	Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice)
			throws IOException, SerialException, SQLException;

	List<String> getAllRoomTypes();

	List<Room> getAllRooms() throws SQLException;

	byte[] getRoomPhotoByRoomId(Long id) throws SQLException, IOException;

	void deleteRoom(Long roomId);

	Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoByte);

	Optional<Room> getRoomById(Long roomId);

}
