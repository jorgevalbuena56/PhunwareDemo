package com.phunware.phunwaredemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.phunware.phunwaredemo.fragment.VenueDetailFragment;
import com.phunware.phunwaredemo.fragment.VenueListFragment;
import com.phunware.phunwaredemo.model.Venue;
import com.phunware.phunwaredemo.utils.Utils;

/**
 * An activity representing a list of Venues. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of venues, which when touched, lead to a
 * {@link VenueDetailActivity} representing the venue details. On tablets, the
 * activity presents the list of venues and venue details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of venues is a
 * {@link VenueListFragment} and the venue details (if present) is a
 * {@link VenueDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link VenueListFragment.Callbacks} interface to listen for item selections.
 */
public class VenueListActivity extends SingleFragmentActivity implements
		VenueListFragment.Callbacks {

/*	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		if (findViewById(R.id.venue_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((VenueListFragment) getSupportFragmentManager().findFragmentById(
					R.id.venue_list)).setActivateOnItemClick(true);
		}
	}
*/
	/**
	 * Callback method from {@link VenueListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Venue venue) {
		if (findViewById(R.id.detail_fragment_container) != null) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detail_fragment_container);

			Bundle arguments = new Bundle();
			arguments.putParcelable(VenueDetailFragment.ARG_VENUE, venue);
			VenueDetailFragment newDetail = VenueDetailFragment.newInstance(venue);
			newDetail.setArguments(arguments);

			if (oldDetail != null) {
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detail_fragment_container, newDetail);
			ft.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, VenueDetailActivity.class);
			detailIntent.putExtra(VenueDetailFragment.ARG_VENUE, venue);
			startActivity(detailIntent);
		}
	}
	/*
	 * When the user presses on key back, instead of closing immediately the app, first I ask the 
	 * user if he really wants to close it
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	Utils.closeApplicationMessage(this);		   		        		        
	    }
	    return false;
	}

	@Override
	protected Fragment createFragment() {		
		return VenueListFragment.newInstance();
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.activity_masterdetail;
	}
}
