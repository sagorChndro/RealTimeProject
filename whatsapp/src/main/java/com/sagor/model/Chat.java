package com.sagor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;

	private String chatName;

	private String chatImage;

	@ManyToMany
	private Set<User> admins = new HashSet<>();

	@Column(name = "is_group")
	private boolean isGroup;

	@JoinColumn(name = "createdBy")
	@ManyToOne
	private User createdBy;

	@ManyToMany
	private Set<User> users = new HashSet<>();

	@OneToMany
	List<Message> messages = new ArrayList<>();

}
