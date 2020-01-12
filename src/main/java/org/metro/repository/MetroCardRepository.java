package org.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.metro.exception.MetroRepositoryException;
import org.metro.mapper.JourneyRowMapper;
import org.metro.mapper.MetroRowMapper;
import org.metro.models.Journey;
import org.metro.models.MetroCard;
import org.metro.utility.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MetroCardRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public MetroCard findById(Long id) {
		MetroCard metro = null;
		try {
			String sql = "SELECT * FROM METROCARD where id = :metroCardId";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("metroCardId", id);
			List<MetroCard> metroCard = jdbcTemplate.query(sql, parameters, new MetroRowMapper());
			metro = (metroCard != null && !metroCard.isEmpty()) ? metroCard.get(0) : null;
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
		return metro;
	}

	public Journey lastJourney(Long id) {
		Journey journey = null;
		try {
			String sql = "SELECT top 1 * FROM Journey where metroCardId = :metroCardId order by id desc";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("metroCardId", id);
			List<Journey> journeyList = jdbcTemplate.query(sql, parameters, new JourneyRowMapper());
			journey = (journeyList != null && !journeyList.isEmpty()) ? journeyList.get(0) : null;
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
		return journey;

	}

	public List<Journey> completedJourneyPerCard(Long id) {
		List<Journey> journeyList = null;
		try {
			String sql = "SELECT * FROM Journey where metroCardId = :metroCardId and completed = 1 order by id desc";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("metroCardId", id);
			journeyList = jdbcTemplate.query(sql, parameters, new JourneyRowMapper());
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
		return journeyList;
	}

	public Integer footFallsPerStation(Station station) {
		Integer footFalls = 0;
		try {
			String sql = "select count(*) from journey where source = :station or destination = :station";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("station", station.toString());
			footFalls = jdbcTemplate.queryForObject(sql, parameters, Integer.class);
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
		return footFalls;

	}

	public void insertJourney(Journey journey) {
		try {
			String sql = "insert into Journey(source,swipeIn,destination,swipeOut,completed,fare,metroCardId) values(:source,:swipeIn,:destination,:swipeOut,:completed,:fare,:metroCardId)";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("source", Objects.nonNull(journey.getSource()) ? journey.getSource().toString() : null);
			parameters.put("swipeIn", journey.getSwipeIn());
			parameters.put("destination",
					Objects.nonNull(journey.getDestination()) ? journey.getDestination().toString() : null);
			parameters.put("swipeOut", journey.getSwipeOut());
			parameters.put("completed", journey.getCompleted());
			parameters.put("fare", journey.getFare());
			parameters.put("metroCardId", journey.getMetroCardId());
			jdbcTemplate.update(sql, parameters);
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
	}

	public void updateJourney(Journey journey) {
		try {
			String sql = "update Journey set destination = :destination , completed = :completed,fare = :fare, swipeOut = :swipeOut where id = :id";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("destination",
					Objects.nonNull(journey.getDestination()) ? journey.getDestination().toString() : null);
			parameters.put("swipeOut", journey.getSwipeOut());
			parameters.put("completed", journey.getCompleted());
			parameters.put("fare", journey.getFare());
			parameters.put("id", journey.getId());
			jdbcTemplate.update(sql, parameters);
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
	}

	public void updateMetroCardBalance(MetroCard metroCard) {
		try {
			String sql = "update MetroCard set balance = :balance where id = :id";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("balance", metroCard.getBalance());
			parameters.put("id", metroCard.getId());
			jdbcTemplate.update(sql, parameters);
		} catch (RuntimeException e) {
			throw new MetroRepositoryException();
		}
	}

}
