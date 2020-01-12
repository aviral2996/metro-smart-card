package org.metro.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.metro.models.MetroCard;
import org.springframework.jdbc.core.RowMapper;

public class MetroRowMapper  implements RowMapper<MetroCard> {

	@Override
	public MetroCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		MetroCard metro = new MetroCard();
		metro.setId(rs.getLong("id"));
		metro.setName(rs.getString("name"));
		metro.setBalance(rs.getDouble("balance"));
		return metro;
	}

}
