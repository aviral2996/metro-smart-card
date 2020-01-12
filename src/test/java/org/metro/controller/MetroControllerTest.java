package org.metro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.metro.service.MetroService;
import org.metro.utility.Station;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MetroControllerTest {

	private MockMvc mockMvc;

	@Mock
	private MetroService metroService;

	@InjectMocks
	MetroController metroCardController = new MetroController();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(metroCardController).build();
	}

	@Test
	public void cardSwipeInShouldCardSwipeIn() throws Exception {
		Mockito.doNothing().when(metroService).cardSwipeIn(Mockito.anyLong(), Mockito.any(Station.class));
		MvcResult result = mockMvc.perform(put("/metro/card/swipeIn/1/A1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String response = result.getResponse().getContentAsString();
		Assert.assertNotNull(response);
	}

	@Test
	public void cardSwipeOutShouldCardSwipeOut() throws Exception {
		Mockito.doNothing().when(metroService).cardSwipeOut(Mockito.anyLong(), Mockito.any(Station.class));
		MvcResult result = mockMvc
				.perform(put("/metro/card/swipeOut/1/A1").contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		String response = result.getResponse().getContentAsString();
		Assert.assertNotNull(response);
	}

	@Test
	public void cardReportSummaryShouldCardReportSummary() throws Exception {
		Mockito.doNothing().when(metroService).cardReportSummary(Mockito.anyLong());
		MvcResult result = mockMvc.perform(get("/metro/card/report/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String response = result.getResponse().getContentAsString();
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getTotalFootFallShouldGetTotalFootFall() throws Exception {
		Mockito.when(metroService.totalFootFall(Mockito.any(Station.class))).thenReturn(1);
		MvcResult result = mockMvc.perform(get("/metro/footFall/A1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String response = result.getResponse().getContentAsString();
		Assert.assertNotNull(response);
	}

}
