package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.message.entity.MessageFile;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {

}
