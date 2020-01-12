package org.metro.utility;

public enum Station {
A1,
A2,
A3,
A4,
A5,
A6,
A7,
A8,
A9,
A10;
	
	public Integer getStationCount(Station station) {
		return Math.abs(station.ordinal() - this.ordinal());
	}
}
