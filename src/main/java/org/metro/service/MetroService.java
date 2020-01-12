package org.metro.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.metro.exception.InsufficientBalanceException;
import org.metro.exception.InvalidCardException;
import org.metro.exception.SwipeInException;
import org.metro.exception.SwipeOutException;
import org.metro.models.Journey;
import org.metro.models.MetroCard;
import org.metro.repository.MetroRepository;
import org.metro.utility.CreateCardReport;
import org.metro.utility.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetroService {

	@Autowired
	private MetroRepository metroCardRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(MetroService.class);

	@Transactional
	public void cardSwipeIn(Long metroCardId, Station source) {
		MetroCard metroCard = validateMetroCard(metroCardId);
		Journey lastJourney = metroCardRepository.lastJourney(metroCardId);
		if (!Objects.isNull(lastJourney)) {
			if (lastJourney.getCompleted() == 0) {
				LOGGER.error("First swipe out and complete your last journey.");
				throw new SwipeInException();
			}
			if (metroCard.getBalance() < 50) {
				LOGGER.error("Minimum balance should be 50.");
				throw new InsufficientBalanceException();
			}
		}
		Journey journey = getJourneyData(metroCardId, source);
		metroCardRepository.insertJourney(journey);
	}

	@Transactional
	public void cardSwipeOut(Long metroCardId, Station destination) {
		MetroCard metroCard = validateMetroCard(metroCardId);
		Journey journey = metroCardRepository.lastJourney(metroCardId);
		if (Objects.isNull(journey) || journey.getCompleted() == 1) {
			LOGGER.error("First swipe in.");
			throw new SwipeOutException();
		}
		LocalDateTime date = LocalDateTime.now();
		DayOfWeek currentDay = date.getDayOfWeek();

		Double fare = getFare(currentDay, journey.getSource(), destination);
		metroCard.setBalance(metroCard.getBalance() - fare);

		journey.setDestination(destination);
		journey.setCompleted(1);
		journey.setFare(fare);
		journey.setSwipeOut(LocalDateTime.now());

		metroCardRepository.updateJourney(journey);
		metroCardRepository.updateMetroCardBalance(metroCard);
		LOGGER.info("Jouney completed and metro card balance updated.");
	}

	public void cardReportSummary(Long metroCardId) throws IOException {
		MetroCard metroCard = validateMetroCard(metroCardId);
		List<Journey> journeyList = metroCardRepository.completedJourneyPerCard(metroCardId);
		CreateCardReport.createMetroCardReport(metroCard, journeyList);
	}

	public Integer totalFootFall(Station station) {
		return metroCardRepository.footFallsPerStation(station);
	}

	private Double getFare(DayOfWeek currentDay, Station source, Station destination) {
		Double fare = 0d;
		Integer noOfStations = source.getStationCount(destination);
		if (currentDay.equals(DayOfWeek.SATURDAY) || currentDay.equals(DayOfWeek.SUNDAY)) {
			fare = noOfStations * 5.5;
		} else {
			fare = (double) (noOfStations * 7);
		}
		return fare;
	}

	private Journey getJourneyData(Long metroCardId, Station source) {
		Journey transaction = new Journey();
		transaction.setMetroCardId(metroCardId);
		transaction.setSource(source);
		transaction.setCompleted(0);
		transaction.setSwipeIn(LocalDateTime.now());
		return transaction;
	}

	private MetroCard validateMetroCard(Long id) {
		MetroCard metroCard = metroCardRepository.findById(id);
		if (metroCard == null) {
			LOGGER.error("Invalid Metro Card.");
			throw new InvalidCardException();
		}
		return metroCard;
	}

}
