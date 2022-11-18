package inha.tnt.hbc.domain.message.repository.jdbc;

import java.util.List;

import inha.tnt.hbc.domain.message.entity.MessageFile;

public interface MessageFileRepositoryJdbc {

	void saveAllInBatch(List<MessageFile> messageFiles);

}
