package inha.tnt.hbc.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.message.entity.Decoration;
import inha.tnt.hbc.domain.message.repository.querydsl.DecorationRepositoryQuerydsl;

public interface DecorationRepository extends JpaRepository<Decoration, Long>, DecorationRepositoryQuerydsl {

}
