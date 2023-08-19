package com.sagor.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	private String fullName;
	private String email;
	private String profile_picture;
	private String password;

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		User other = (User) obj;
//		return Objects.equals(Id, other.Id) && Objects.equals(fullName, other.fullName)
//				&& Objects.equals(email, other.email) && Objects.equals(profile_picture, other.profile_picture)
//				&& Objects.equals(password, other.password);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(Id, fullName, email, profile_picture, password);
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(email, other.email)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(password, other.password)
				&& Objects.equals(profile_picture, other.profile_picture);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id, email, fullName, password, profile_picture);
	}

}
