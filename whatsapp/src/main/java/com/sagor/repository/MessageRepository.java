package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagor.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("Select m from Message m join m.chat c where c.Id=:chatId")
	public List<Message> findByChatId(@Param("chatId") Integer chatId);

}
