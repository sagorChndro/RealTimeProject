package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sagor.model.Twit;
import com.sagor.model.User;

public interface TwitRepository extends JpaRepository<Twit, Long> {

	List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();

	List<Twit> findByReTwitsUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAt(User user, Long userId);

	List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);

	@Query("SELECT t FROM Twit t JOIN t.likes l WHERE l.user.id=:userId")
	List<Twit> findByLikesUser_id(Long userId);
}
