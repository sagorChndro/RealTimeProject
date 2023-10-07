package com.sagor.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Verification {

	private boolean status = false;
	private LocalDateTime startAt;
	private LocalDateTime endsAt;
	private String planType; /// user kon plan choose korbe monthly or annually
}
