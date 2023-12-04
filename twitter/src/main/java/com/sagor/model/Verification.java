package com.sagor.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Verification {

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(LocalDateTime endsAt) {
		this.endsAt = endsAt;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	private boolean status = false;
	private LocalDateTime startAt;
	private LocalDateTime endsAt;
	private String planType; /// user kon plan choose korbe monthly or annually
}
