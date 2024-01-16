package com.sagor.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookedRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	@Column(name = "check_In")
	private LocalDate checkInDate;
	@Column(name = "check_Out")
	private LocalDate checkOutDate;
	@Column(name = "guest_FullName")
	private String guestFullName;
	@Column(name = "guest_Email")
	private String guestEmail;
	@Column(name = "adults")
	private int numOfAdults;
	@Column(name = "children")
	private int numOfChildren;
	@Column(name = "total_Guest")
	private int totalNumOfGuest;
	@Column(name = "comfirmation_Code")
	private String bookingConfirmationCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	public BookedRoom(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName,
			String guestEmail, int numOfAdults, int numOfChildren, int totalNumOfGuest, String bookingConfirmationCode,
			Room room) {
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.guestFullName = guestFullName;
		this.guestEmail = guestEmail;
		this.numOfAdults = numOfAdults;
		this.numOfChildren = numOfChildren;
		this.totalNumOfGuest = totalNumOfGuest;
		this.bookingConfirmationCode = bookingConfirmationCode;
		this.room = room;
	}

	public BookedRoom() {

	}

	public void calculateTotalNumerOfGuest() {
		this.totalNumOfGuest = this.numOfAdults + this.numOfChildren;
	}

	public int getNumOfAdults() {
		return numOfAdults;
	}

	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults = numOfAdults;
		calculateTotalNumerOfGuest();
	}

	public int getNumOfChildren() {
		return numOfChildren;
	}

	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
		calculateTotalNumerOfGuest();
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getGuestFullName() {
		return guestFullName;
	}

	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public int getTotalNumOfGuest() {
		return totalNumOfGuest;
	}

	public void setTotalNumOfGuest(int totalNumOfGuest) {
		this.totalNumOfGuest = totalNumOfGuest;
	}

	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
