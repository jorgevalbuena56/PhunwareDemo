package com.phunware.phunwaredemo.view;

import com.phunware.phunwaredemo.R;
import com.phunware.phunwaredemo.model.Venue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenueView extends LinearLayout{

	private TextView mTitleTextView;
    private TextView mAddresTextView;

    public static VenueView inflate(ViewGroup parent) {
    	VenueView venueView = (VenueView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_view, parent, false);
        return venueView;
    }

    public VenueView(Context c) {
        this(c, null);
    }

    public VenueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
	public VenueView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.venue_view_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        mTitleTextView = (TextView) findViewById(R.id.vTitle);
        mAddresTextView = (TextView) findViewById(R.id.vDescription);

    }

    public void setItem(Venue venue) {
        mTitleTextView.setText(venue.getName());
        mAddresTextView.setText(venue.getAddress());

    }

	public TextView getmTitleTextView() {
		return mTitleTextView;
	}

	public void setmTitleTextView(TextView mTitleTextView) {
		this.mTitleTextView = mTitleTextView;
	}

	public TextView getmAddresTextView() {
		return mAddresTextView;
	}

	public void setmAddresTextView(TextView mAddresTextView) {
		this.mAddresTextView = mAddresTextView;
	}
    

}
