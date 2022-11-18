package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.repository.jdbc.MessageFileRepositoryJdbc;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long>, MessageFileRepositoryJdbc {

}
