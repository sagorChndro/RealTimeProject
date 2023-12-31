package com.sagor.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private String message;
	private boolean status;

	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}

}
