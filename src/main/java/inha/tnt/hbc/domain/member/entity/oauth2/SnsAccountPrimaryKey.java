package inha.tnt.hbc.domain.member.entity.oauth2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SnsAccountPrimaryKey implements Serializable {

	private static final long serialVersionUID = 2868210232929931052L;

	@Enumerated(EnumType.STRING)
	private SnsProvider provider;

	@Column(name = "sns_account_id")
	private Long id;

}
