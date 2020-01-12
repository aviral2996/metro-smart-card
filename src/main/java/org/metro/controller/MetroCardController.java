package org.metro.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.metro.service.MetroCardService;
import org.metro.utility.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metro")
public class MetroCardController {

	@Autowired
	private MetroCardService metroService;

	@PutMapping(value = "/card/swipeIn/{metroCardId}/{source}")
	public void cardSwipeIn(@PathVariable Long metroCardId, @PathVariable Station source) {
		metroService.cardSwipeIn(metroCardId, source);
	}

	@PutMapping(value = "/card/swipeOut/{metroCardId}/{destination}")
	public void cardSwipeOut(@PathVariable Long metroCardId, @PathVariable Station destination) {
		metroService.cardSwipeOut(metroCardId, destination);
	}

	@GetMapping(value = "/card/report/{metroCardId}")
	public void cardReportSummary(@PathVariable Long metroCardId) throws IOException {
		metroService.cardReportSummary(metroCardId);
	}

	@GetMapping(value = "/footFall/{station}")
	public Integer totalFootFall(@PathVariable Station station) {
		return metroService.totalFootFall(station);
	}
}
