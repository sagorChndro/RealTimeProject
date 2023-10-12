package com.sagor.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Twit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	private String content;
	private String message;
	private String video;
	private String image;

	@OneToMany(mappedBy = "twit", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();

	@OneToMany
	private List<Twit> replyTwit = new ArrayList<>();

	@ManyToMany
	private List<User> reTwitsUser = new ArrayList<>();

	@ManyToOne
	private Twit replyFor;

	private boolean isReply;
	private boolean isTwit;
	private LocalDateTime createdAt;

}
