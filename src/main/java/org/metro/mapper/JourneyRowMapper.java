package org.metro.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.metro.models.Journey;
import org.metro.utility.Station;
import org.springframework.jdbc.core.RowMapper;

public class JourneyRowMapper implements RowMapper<Journey> {

	@Override
	public Journey mapRow(ResultSet rs, int rowNum) throws SQLException {
		Journey journey = new Journey();
		journey.setId(rs.getLong("id"));
		journey.setSource(Objects.nonNull(rs.getString("source")) ? Station.valueOf(rs.getString("source")) : null);
		journey.setSwipeIn(Objects.nonNull(rs.getTimestamp("swipeIn")) ? rs.getTimestamp("swipeIn").toLocalDateTime() : null);
		journey.setDestination(Objects.nonNull(rs.getString("destination")) ? Station.valueOf(rs.getString("destination")) : null);
		journey.setSwipeOut(Objects.nonNull(rs.getTimestamp("swipeOut")) ? rs.getTimestamp("swipeOut").toLocalDateTime() : null);
		journey.setCompleted(rs.getInt("completed"));
		journey.setFare(rs.getDouble("fare"));
		journey.setMetroCardId(rs.getLong("metroCardId"));
		return journey;
	}

}
