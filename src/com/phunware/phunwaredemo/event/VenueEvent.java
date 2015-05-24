package com.phunware.phunwaredemo.event;

import com.phunware.phunwaredemo.model.VenueResult;

public class VenueEvent {

	public VenueResult result;
	
	public VenueEvent(VenueResult venueResult){
		this.result = venueResult;
	}
}
