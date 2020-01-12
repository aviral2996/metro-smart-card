package org.metro.controller;

import java.io.IOException;

import org.metro.service.MetroService;
import org.metro.utility.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metro")
public class MetroController {

	@Autowired
	private MetroService metroService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MetroController.class);

	@PutMapping(value = "/card/swipeIn/{metroCardId}/{source}")
	public void cardSwipeIn(@PathVariable Long metroCardId, @PathVariable Station source) {
		metroService.cardSwipeIn(metroCardId, source);
		LOGGER.info("Card swiped in successfully.");
	}

	@PutMapping(value = "/card/swipeOut/{metroCardId}/{destination}")
	public void cardSwipeOut(@PathVariable Long metroCardId, @PathVariable Station destination) {
		metroService.cardSwipeOut(metroCardId, destination);
		LOGGER.info("Card swiped out successfully.");
	}

	@GetMapping(value = "/card/report/{metroCardId}")
	public void cardReportSummary(@PathVariable Long metroCardId) throws IOException {
		metroService.cardReportSummary(metroCardId);
		LOGGER.info("Metro card report generated successfully.");
	}

	@GetMapping(value = "/footFall/{station}")
	public Integer totalFootFall(@PathVariable Station station) {
		Integer footFalls = metroService.totalFootFall(station);
		LOGGER.info("Total foot falls for metro station " + station.toString() + " is " + footFalls);
		return footFalls;
	}
}
