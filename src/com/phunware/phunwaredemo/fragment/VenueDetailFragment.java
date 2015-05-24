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

	public static VenueDetailFragment newInstance(Venue venue) {
		Bundle args = new Bundle();
		args.putParcelable(ARG_VENUE, venue);
		VenueDetailFragment fragment = new VenueDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_VENUE)) {
			// Load the venue specified by the fragment
			// arguments. Instead of calling for the details, all the object is passed by to
			// the fragment.
			mVenue = (Venue)getArguments().getParcelable(ARG_VENUE);
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
		                    android.R.layout.simple_list_item_1, mVenue.getSchedule());
		       
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
		        // Verify that there are applications registered to handle this intent
		        // (resolveActivity returns null if none are registered)
		        if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
		            startActivity(shareIntent);
		        } 
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
