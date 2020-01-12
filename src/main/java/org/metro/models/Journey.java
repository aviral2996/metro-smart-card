package org.metro.models;

import java.time.LocalDateTime;

import org.metro.utility.Station;

public class Journey {

	private Long id;
	private Station source;
	private LocalDateTime swipeIn;
	private Station destination;
	private LocalDateTime swipeOut;
	private Integer completed;
	private Double fare;
	private Long metroCardId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Station getSource() {
		return source;
	}
	public void setSource(Station source) {
		this.source = source;
	}
	public Station getDestination() {
		return destination;
	}
	public void setDestination(Station destination) {
		this.destination = destination;
	}
	
	
	public LocalDateTime getSwipeIn() {
		return swipeIn;
	}
	public void setSwipeIn(LocalDateTime swipeIn) {
		this.swipeIn = swipeIn;
	}
	public LocalDateTime getSwipeOut() {
		return swipeOut;
	}
	public void setSwipeOut(LocalDateTime swipeOut) {
		this.swipeOut = swipeOut;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Double getFare() {
		return fare;
	}
	public void setFare(Double fare) {
		this.fare = fare;
	}
	public Long getMetroCardId() {
		return metroCardId;
	}
	public void setMetroCardId(Long metroCardId) {
		this.metroCardId = metroCardId;
	}
	
	
}
