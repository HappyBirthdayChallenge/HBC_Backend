package inha.tnt.hbc.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccount;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccountPrimaryKey;

public interface SnsAccountRepository extends JpaRepository<SnsAccount, SnsAccountPrimaryKey> {

	@Query("select sa from SnsAccount sa join fetch sa.member where sa.id = :id")
	Optional<SnsAccount> findWithMemberById(@Param("id") SnsAccountPrimaryKey id);

}
