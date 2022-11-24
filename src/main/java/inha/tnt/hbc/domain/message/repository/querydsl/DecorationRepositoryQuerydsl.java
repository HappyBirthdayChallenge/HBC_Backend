package inha.tnt.hbc.domain.message.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Decoration;

public interface DecorationRepositoryQuerydsl {

	@Transactional(readOnly = true)
	Page<Decoration> findAllByRoomIdAndCategory(Long roomId, String category, Pageable pageable);

}
