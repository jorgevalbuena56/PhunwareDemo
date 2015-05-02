package com.phunware.phunwaredemo.service;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

import com.phunware.phunwaredemo.model.Venue;

/**
 * Interface used by Retrofit to make the http call
 * @author GeorgeLocal
 *
 */
public interface VenueService {
	@GET("/nflapi-static.json")
	void listVenues(Callback<List<Venue>> callback);
}
