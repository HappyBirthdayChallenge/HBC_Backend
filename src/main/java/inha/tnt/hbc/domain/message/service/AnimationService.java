package inha.tnt.hbc.domain.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Animation;
import inha.tnt.hbc.domain.message.entity.AnimationTypes;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.repository.AnimationRepository;

@Service
@RequiredArgsConstructor
public class AnimationService {

	private final AnimationRepository animationRepository;

	@Transactional
	public void save(Message message, AnimationTypes type) {
		final Animation animation = Animation.builder()
			.message(message)
			.type(type)
			.build();
		animationRepository.save(animation);
	}

	public void deleteByMessage(Message message) {
		animationRepository.deleteByMessage(message);
	}

}
