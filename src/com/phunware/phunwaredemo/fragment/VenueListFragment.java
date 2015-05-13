package com.phunware.phunwaredemo.fragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.adapter.VenueAdapter;
import com.phunware.phunwaredemo.model.Venue;
import com.phunware.phunwaredemo.service.VenueService;

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
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		public void onItemSelected(Venue venue) {
		}
	};

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated venue position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	private AQuery mAquery;
	private List<Venue> mVenuesList;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VenueListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Retain this fragment across configuration changes.
	    setRetainInstance(true);
	    
	    if(mVenuesList == null)
	    	retrieveDateWS();
	}

	/**
	 * This Method is the one in charge to connect with the Rest Service and retrieve
	 * the venue's data. It uses Retrofit to create the http connection ans Gson to deserialize
	 * and convert into the Venue Objet
	 */
	private void retrieveDateWS(){
		
		
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd HH:mm:ss Z")
	    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
	    .create();
		
		//The url is store in a properties file in the asset folder which give us flexibility
		//if the server changes.
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint(getResources().getString(R.string.ws_url))
		.setConverter(new GsonConverter(gson))
		.build();

		VenueService service = restAdapter.create(VenueService.class);
		
		
		Callback<List<Venue>> callback = new Callback<List<Venue>>(){
		    @Override
		    public void success(List<Venue> venues, Response response) {		    	
		    	mVenuesList = venues;
		    	setListAdapter(new VenueAdapter(getActivity(), mVenuesList));
		    }
		
		    @Override
		    public void failure(RetrofitError retrofitError) {
		    	
		    	new AlertDialog.Builder(mAquery.getContext())
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.error)
				.setMessage(R.string.error_getting_info)
				.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						((Activity)mAquery.getContext()).finish(); 
					}

				})				
				.show();
		    	
		    }
		
		};
		
		service.listVenues(callback);
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
		mCallbacks = sDummyCallbacks;
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
