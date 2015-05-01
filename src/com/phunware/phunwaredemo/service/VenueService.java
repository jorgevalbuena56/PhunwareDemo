package com.phunware.phunwaredemo.service;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

import com.phunware.phunwaredemo.model.Venue;


public interface VenueService {
	@GET("/nflapi-static.json")
	void listVenues(Callback<List<Venue>> callback);
}
