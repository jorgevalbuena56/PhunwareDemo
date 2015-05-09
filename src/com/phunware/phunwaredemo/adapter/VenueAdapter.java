package com.phunware.phunwaredemo.adapter;

import java.util.Collections;
import java.util.List;

import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.model.Venue;
import com.phunware.phunwaredemo.view.VenueView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class VenueAdapter extends BaseAdapter {

	private List<Venue> mVenues = Collections.emptyList();
	private Context mContext;

	public VenueAdapter(Context context, List<Venue> venues) {
		this.mVenues = venues;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		VenueView venueView;
		
	    if (convertView == null) {
	    	venueView = (VenueView) LayoutInflater.from(mContext)
	          .inflate(R.layout.venue_view, parent, false);
	    } else {
	    	venueView = (VenueView) convertView;
	    }
		

		venueView.setItem(getItem(position));

		return venueView;
	}

	public void updateVenues(List<Venue> venues) {
		this.mVenues = venues;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mVenues.size();
	}

	@Override
	public Venue getItem(int position) {
		return mVenues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
