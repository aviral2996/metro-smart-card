package org.metro.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.metro.exception.MetroRepositoryException;
import org.metro.mapper.JourneyRowMapper;
import org.metro.mapper.MetroRowMapper;
import org.metro.models.Journey;
import org.metro.models.MetroCard;
import org.metro.utility.Station;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MetroRepositoryTest {

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;

	@InjectMocks
	MetroRepository metroRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findByIdShouldFindById() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(MetroRowMapper.class))).thenReturn(null);
		MetroCard metro = metroRepository.findById(1L);
		Assert.assertEquals(metro, null);
	}


	@Test(expected=MetroRepositoryException.class)
	public void findByIdShouldThrowException() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(MetroRowMapper.class))).thenThrow(new MetroRepositoryException());
		MetroCard metro = metroRepository.findById(1L);
		Assert.assertEquals(metro, null);
	}
	
	@Test
	public void lastJourneyShouldGetLastJourney() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(JourneyRowMapper.class))).thenReturn(null);
		Journey journey = metroRepository.lastJourney(1L);
		Assert.assertEquals(journey, null);
	}


	@Test(expected=MetroRepositoryException.class)
	public void lastJourneyShouldThrowException() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(JourneyRowMapper.class))).thenThrow(new MetroRepositoryException());
		Journey journey = metroRepository.lastJourney(1L);
		Assert.assertEquals(journey, null);
	}

	@Test
	public void completedJourneyPerCardShouldGetCompletedJourneyPerCard() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(JourneyRowMapper.class))).thenReturn(null);
		List<Journey> journey = metroRepository.completedJourneyPerCard(1L);
		Assert.assertEquals(journey, null);
	}
	
	@Test(expected=MetroRepositoryException.class)
	public void completedJourneyPerCardShouldThrowException() {
		Mockito.when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),Mockito.any(JourneyRowMapper.class))).thenThrow(new MetroRepositoryException());
		List<Journey> journey = metroRepository.completedJourneyPerCard(1L);
		Assert.assertEquals(journey, null);
	}
}
