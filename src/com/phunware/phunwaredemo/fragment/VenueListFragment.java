package com.phunware.phunwaredemo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.adapter.VenueAdapter;
import com.phunware.phunwaredemo.async.VenueClient;
import com.phunware.phunwaredemo.bus.BusProvider;
import com.phunware.phunwaredemo.event.VenueErrorEvent;
import com.phunware.phunwaredemo.event.VenueEvent;
import com.phunware.phunwaredemo.model.Venue;

/**
 * A list fragment representing a list of Venues. This fragment also supports
 * tablet devices by allowing list venues to be given an 'activated' state upon
 * selection. This helps indicate which venue is currently being viewed in a
 * {@link VenueDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class VenueListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated venue position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private static final String VENUE_ARG = "fragment.venue.arg";
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of venue
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an venue has been selected.
		 */
		public void onItemSelected(Venue venue);
	}

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks;

	/**
	 * The current activated venue position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VenueListFragment() {
	}

	public static VenueListFragment newInstance(){
		return new VenueListFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * Called when the bus posts an event for successful result.
	 * 
	 * <br>Call onEvent: Called in the same thread (default)
	 * <br>Call onEventMainThread: Called in Android UI's main thread
	 * <br>Call onEventBackgroundThread: Called in the background thread
	 * <br>Call onEventAsync: Called in a separate thread
	 * 
	 * @param event
	 */
	public void onEvent(VenueEvent  event) {
    	setListAdapter(new VenueAdapter(getActivity(), event.result.lstVenues));		
	}
	
	/**
	 * Called when the bus posts an event for error result.
	 * 
	 * @param event
	 */
	public void onEvent(VenueErrorEvent  event) {
		
		new AlertDialog.Builder(getActivity())
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.error)
		.setMessage(R.string.error_getting_info)
		.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				getActivity().finish(); 
			}

		})				
		.show();
    			
	}
	

	
	@Override
	public void onPause() {
		super.onPause();
		
		BusProvider.getInstance().unregister(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		BusProvider.getInstance().register(this);

		VenueClient.get(getActivity()).retrieveDateWS();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated venue position.
		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = null;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected((Venue)listView.getItemAtPosition(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated venue position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
			
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list venues will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
