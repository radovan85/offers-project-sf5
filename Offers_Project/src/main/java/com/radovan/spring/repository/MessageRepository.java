package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

	@Query(value = "select * from messages where receiver_id = :receiverId",nativeQuery=true)
	List<MessageEntity> findAllByReceiverId(@Param ("receiverId") Integer receiverId);
	
	@Query(value = "select * from messages where sender_id = :senderId",nativeQuery = true)
	List<MessageEntity> findAllBySenderId(@Param ("senderId") Integer senderId);
	
	@Modifying
	@Query(value = "delete from messages where id = :messageId",nativeQuery=true)
	void removeMessageById(@Param ("messageId") Integer id);
	
	@Modifying
	@Query(value = "delete from messages where receiver_id = :receiverId",nativeQuery=true)
	void removeAllByReceiverId(@Param ("receiverId") Integer receiverId);
}
