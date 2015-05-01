package com.phunware.phunwaredemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.phunware.phunwaredemo.fragment.VenueDetailFragment;
import com.phunware.phunwaredemo.fragment.VenueListFragment;
import com.phunware.phunwaredemo.model.Venue;
import com.phunware.phunwaredemo.utils.Utils;

/**
 * An activity representing a list of Venues. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link VenueDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link VenueListFragment} and the item details (if present) is a
 * {@link VenueDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link VenueListFragment.Callbacks} interface to listen for item selections.
 */
public class VenueListActivity extends ActionBarActivity implements
		VenueListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_venue_list);

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

	/**
	 * Callback method from {@link VenueListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Venue venue) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putSerializable(VenueDetailFragment.ARG_VENUE, venue);
			VenueDetailFragment fragment = new VenueDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.venue_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, VenueDetailActivity.class);
			detailIntent.putExtra(VenueDetailFragment.ARG_VENUE, venue);
			startActivity(detailIntent);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	Utils.closeApplicationMessage(this);		   		        		        
	    }
	    return false;
	}
}
