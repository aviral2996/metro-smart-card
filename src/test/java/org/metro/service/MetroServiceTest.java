package org.metro.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.metro.exception.InsufficientBalanceException;
import org.metro.exception.InvalidCardException;
import org.metro.exception.SwipeInException;
import org.metro.exception.SwipeOutException;
import org.metro.models.Journey;
import org.metro.models.MetroCard;
import org.metro.repository.MetroRepository;
import org.metro.utility.Station;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class MetroServiceTest {

	@Mock
	private MetroRepository metroCardRepository;

	@InjectMocks
	MetroService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void cardSwipeInShouldCardSwipeIn() {
		Journey journey = new Journey();
		journey.setCompleted(1);
		MetroCard metro = new MetroCard();
		metro.setBalance(100d);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(metro);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).insertJourney(Mockito.any(Journey.class));
		service.cardSwipeIn(1L, Station.A1);
	}
	
	@Test(expected=InsufficientBalanceException.class)
	public void cardSwipeInShouldThrowInsufficientBalanceException() {
		Journey journey = new Journey();
		journey.setCompleted(1);
		MetroCard metro = new MetroCard();
		metro.setBalance(40d);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(metro);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).insertJourney(Mockito.any(Journey.class));
		service.cardSwipeIn(1L, Station.A1);
	}
	
	@Test(expected=InvalidCardException.class)
	public void cardSwipeInShouldThrowInvalidCardException() {
		Journey journey = new Journey();
		journey.setCompleted(1);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).insertJourney(Mockito.any(Journey.class));
		service.cardSwipeIn(1L, Station.A1);
	}
	
	@Test(expected=SwipeInException.class)
	public void cardSwipeInShouldThrowSwipeInException() {
		Journey journey = new Journey();
		journey.setCompleted(0);
		MetroCard metro = new MetroCard();
		metro.setBalance(100d);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(metro);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).insertJourney(Mockito.any(Journey.class));
		service.cardSwipeIn(1L, Station.A1);
	}
	
	@Test
	public void cardSwipeOutShouldCardSwipeOut() {
		Journey journey = new Journey();
		journey.setCompleted(0);
		journey.setSource(Station.A3);
		MetroCard metro = new MetroCard();
		metro.setBalance(100d);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(metro);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).updateJourney(Mockito.any(Journey.class));
		Mockito.doNothing().when(metroCardRepository).updateMetroCardBalance(Mockito.any(MetroCard.class));
		service.cardSwipeOut(1L, Station.A1);
	}
	
	@Test(expected=SwipeOutException.class)
	public void cardSwipeOutShouldThrowSwipeOutException() {
		Journey journey = new Journey();
		journey.setCompleted(1);
		journey.setSource(Station.A3);
		MetroCard metro = new MetroCard();
		metro.setBalance(100d);
		Mockito.when(metroCardRepository.findById(Mockito.anyLong())).thenReturn(metro);
		Mockito.when(metroCardRepository.lastJourney(Mockito.anyLong())).thenReturn(journey);
		Mockito.doNothing().when(metroCardRepository).updateJourney(Mockito.any(Journey.class));
		Mockito.doNothing().when(metroCardRepository).updateMetroCardBalance(Mockito.any(MetroCard.class));
		service.cardSwipeOut(1L, Station.A1);
	}

	@Test
	public void getTotalFootFallShouldgetTotalFootFall() {
		Mockito.when(metroCardRepository.footFallsPerStation(Mockito.any(Station.class))).thenReturn(1);
		Integer footfalls = service.totalFootFall(Station.A1);
		Assert.assertNotNull(footfalls);
	}
	
}
