package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Animation;
import inha.tnt.hbc.domain.message.entity.Message;

public interface AnimationRepository extends JpaRepository<Animation, Long> {

	@Transactional
	void deleteByMessage(Message message);

}
