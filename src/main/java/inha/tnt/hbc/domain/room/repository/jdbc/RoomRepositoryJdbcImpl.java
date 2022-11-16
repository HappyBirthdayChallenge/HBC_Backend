package inha.tnt.hbc.domain.room.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.room.entity.Room;

@RequiredArgsConstructor
public class RoomRepositoryJdbcImpl implements RoomRepositoryJdbc {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void saveAllInBatch(List<Room> rooms) {
		final String now = LocalDateTime.now().toString();
		final String sql =
			"INSERT INTO rooms (`create_at`, `update_at`, `cake_type`, `room_type`, `member_id`) " +
				"VALUES(?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(
			sql,
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, now);
					ps.setString(2, now);
					ps.setString(3, rooms.get(i).getCakeType().name());
					ps.setString(4, rooms.get(i).getRoomType().name());
					ps.setString(5, rooms.get(i).getMember().getId().toString());
				}

				@Override
				public int getBatchSize() {
					return rooms.size();
				}
			});
	}

}
