package inha.tnt.hbc.domain.message.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.entity.MessageFile;

@RequiredArgsConstructor
public class MessageFileRepositoryJdbcImpl implements MessageFileRepositoryJdbc {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void saveAllInBatch(List<MessageFile> messageFiles) {
		final String sql =
			"INSERT INTO rooms (`name`, `type`, `uuid`, `message_id`) " +
				"VALUES(?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(
			sql,
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, messageFiles.get(i).getName());
					ps.setString(2, messageFiles.get(i).getType().name());
					ps.setString(3, messageFiles.get(i).getUuid());
					ps.setString(4, messageFiles.get(i).getMessage().getId().toString());
				}

				@Override
				public int getBatchSize() {
					return messageFiles.size();
				}
			});
	}

}
