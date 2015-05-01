package com.phunware.phunwaredemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.VenueDetailActivity;
import com.phunware.phunwaredemo.VenueListActivity;
import com.phunware.phunwaredemo.model.ScheduleItem;
import com.phunware.phunwaredemo.model.Venue;

/**
 * A fragment representing a single Venue detail screen. This fragment is either
 * contained in a {@link VenueListActivity} in two-pane mode (on tablets) or a
 * {@link VenueDetailActivity} on handsets.
 */
public class VenueDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_VENUE = "venue";

	/**
	 * The venue content this fragment is presenting.
	 */
	private Venue mVenue;
	
	/**
	 * AQuery representation of the view
	 */
	private AQuery mAquery;
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VenueDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_VENUE)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mVenue = (Venue)getArguments().getSerializable(ARG_VENUE);
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_venue_detail,
				container, false);
		
		mAquery = new AQuery(rootView);
		
		// Show the venue content
		if (mVenue != null) {
			mAquery.id(R.id.vTitle).text(mVenue.getName());
			mAquery.id(R.id.vAddress).text(mVenue.getAddress());
			StringBuilder builder = new StringBuilder(mVenue.getCity()).append(", ").append(mVenue.getState()).append(" ").append(mVenue.getZip());
			mAquery.id(R.id.vCity).text(builder.toString());
			
			mAquery.id(R.id.vImage).progress(R.id.progress).image(mVenue.getImageUrl(), true, true, 0, R.drawable.noimageavailable);

			if(mVenue.getSchedule() != null){				

		        ArrayAdapter<ScheduleItem> adapter = new ArrayAdapter<ScheduleItem>(getActivity(),
		                    android.R.layout.simple_list_item_1, mVenue.getSchedule()){

								@Override
								public View getView(int position,
										View convertView, ViewGroup parent) {
									
									TextView textView = (TextView) super.getView(position, convertView, parent);

									
							        ScheduleItem schedule = getItem(position);
							        StringBuilder builder = new StringBuilder(android.text.format.DateFormat.format("E M/dd h:mm a", schedule.getStartDate()))
							        							.append(" ").append(getResources().getString(R.string.to)).append(" ")
							        							.append(android.text.format.DateFormat.format("h:mm a", schedule.getEndDate()));
							        textView.setText(builder.toString());

							        return textView;
								}
		        
		        	
		        };
		       
		        mAquery.id(R.id.vScheduleList).adapter(adapter);
		        mAquery.id(R.id.vScheduleList).visibility(View.VISIBLE);	
			}
	        
			
		}

		return rootView;
	}
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.share_action_provider, menu);
    	super.onCreateOptionsMenu(menu, inflater);

        // Set file with share history to the provider and set the share intent.
        MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        
        actionItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
		        Intent shareIntent = new Intent(Intent.ACTION_SEND);        
		        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mVenue.getName());
		        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, mVenue.getDescription());        	
		        shareIntent.setType("text/plain");
		        startActivity(shareIntent);
				return false;
			}
		});
    }

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
    
    
}
