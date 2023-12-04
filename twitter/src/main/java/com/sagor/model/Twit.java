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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Twit> getReplyTwit() {
		return replyTwit;
	}

	public void setReplyTwit(List<Twit> replyTwit) {
		this.replyTwit = replyTwit;
	}

	public List<User> getReTwitsUser() {
		return reTwitsUser;
	}

	public void setReTwitsUser(List<User> reTwitsUser) {
		this.reTwitsUser = reTwitsUser;
	}

	public Twit getReplyFor() {
		return replyFor;
	}

	public void setReplyFor(Twit replyFor) {
		this.replyFor = replyFor;
	}

	public boolean isReply() {
		return isReply;
	}

	public void setReply(boolean isReply) {
		this.isReply = isReply;
	}

	public boolean isTwit() {
		return isTwit;
	}

	public void setTwit(boolean isTwit) {
		this.isTwit = isTwit;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
