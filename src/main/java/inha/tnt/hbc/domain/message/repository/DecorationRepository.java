package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Decoration;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.repository.querydsl.DecorationRepositoryQuerydsl;

@Transactional(readOnly = true)
public interface DecorationRepository extends JpaRepository<Decoration, Long>, DecorationRepositoryQuerydsl {

	@Transactional
	void deleteByMessage(Message message);

	Decoration findByMessage(Message message);

}
