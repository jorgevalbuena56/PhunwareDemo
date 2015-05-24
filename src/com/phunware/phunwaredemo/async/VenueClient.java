package com.phunware.phunwaredemo.async;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.bus.BusProvider;
import com.phunware.phunwaredemo.event.VenueErrorEvent;
import com.phunware.phunwaredemo.event.VenueEvent;
import com.phunware.phunwaredemo.model.VenueResult;
import com.phunware.phunwaredemo.service.VenueService;

public class VenueClient {

	private static VenueClient sVenueClient;
	private VenueResult sVenueResult;
	private Context mAppContext;

	private VenueClient(Context appContext) {
		mAppContext = appContext;
	}

	public static VenueClient get(Context appContext) {
		if (sVenueClient == null) {
			sVenueClient = new VenueClient(appContext.getApplicationContext());
		}
		return sVenueClient;
	}
	/**
	 * This Method is the one in charge to connect with the Rest Service and retrieve
	 * the venue's data. It uses Retrofit to create the http connection ans Gson to deserialize
	 * and convert into the Venue Objet
	 */
	public void retrieveDateWS(){

		if(sVenueResult == null){

			Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd HH:mm:ss Z")
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.create();

			//The url is store in a properties file in the asset folder which give us flexibility
			//if the server changes.
			RestAdapter restAdapter = new RestAdapter.Builder()
			.setEndpoint(mAppContext.getResources().getString(R.string.ws_url))
			.setConverter(new GsonConverter(gson))
			.build();

			VenueService service = restAdapter.create(VenueService.class);


			Callback<VenueResult> callback = new Callback<VenueResult>(){
				@Override
				public void success(VenueResult venueResult, Response response) {
					sVenueResult = venueResult;
					BusProvider.getInstance().post(new VenueEvent(venueResult));
				}

				@Override
				public void failure(RetrofitError retrofitError) {
					BusProvider.getInstance().post(new VenueErrorEvent(retrofitError));
				}

			};

			service.listVenues(callback);

		}else{
			
			BusProvider.getInstance().post(sVenueResult);
		}


	}
}
