package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {

	@Transactional
	void deleteByMessage(Message message);

}
