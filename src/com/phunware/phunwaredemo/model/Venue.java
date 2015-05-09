package com.phunware.phunwaredemo.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * POJO Representing the Venue
 * @author GeorgeLocal
 *
 */
public class Venue implements Parcelable {
	 
	// Core fields
	@SerializedName("id")
	private long mId;
	@SerializedName("pcode")
	private int mPcode;
	@SerializedName("latitude")
	private double mLatitude;
	@SerializedName("longitude")
	private double mLongitude;
	@SerializedName("name")
	private String mName;
	@SerializedName("address")
	private String mAddress;
	@SerializedName("city")
	private String mCity;
	@SerializedName("state")
	private String mState;
	@SerializedName("zip") 
	private String mZip;
	@SerializedName("phone")
	private String mPhone;
 
	// Super Bowl venue fields
	@SerializedName("tollfreephone")
	private String mTollFreePhone;
	private String mUrl;
	@SerializedName("description")
	private String mDescription;
	@SerializedName("ticket_link")
	private String mTicketLink;
	@SerializedName("image_url")
	private String mImageUrl;
	@SerializedName("schedule")
	private List<ScheduleItem> mSchedule;
 
	// computed fields
	private float mDistance;
 
	public String getDescription() {
		return mDescription;
	}
 
	public void setDescription(String description) {
		mDescription = description;
	}
 
	public String getTicketLink() {
		return mTicketLink;
	}
 
	public void setTicketLink(String ticketLink) {
		mTicketLink = ticketLink;
	}
 
	public List<ScheduleItem> getSchedule() {
		return mSchedule;
	}
 
	public void setSchedule(List<ScheduleItem> schedule) {
		mSchedule = schedule;
	}
 
	public String getTollFreePhone() {
		return mTollFreePhone;
	}
 
	public void setTollFreePhone(String tollFreePhone) {
		mTollFreePhone = tollFreePhone;
	}
 
	public String getUrl() {
		return mUrl;
	}
 
	public void setUrl(String url) {
		mUrl = url;
	}
 
	public Venue() {
 
	}
 
	public long getId() {
		return mId;
	}
 
	public void setId(long id) {
		mId = id;
	}
 
	public String getName() {
		return mName;
	}
 
	public void setName(String name) {
		mName = name;
	}
 
	public String getAddress() {
		return mAddress;
	}
 
	public void setAddress(String address) {
		mAddress = address;
	}
 
	public String getCity() {
		return mCity;
	}
 
	public void setCity(String city) {
		mCity = city;
	}
 
	public String getState() {
		return mState;
	}
 
	public void setState(String state) {
		mState = state;
	}
 
	public String getZip() {
		return mZip;
	}
 
	public void setZip(String zip) {
		mZip = zip;
	}
 
	public String getPhone() {
		return mPhone;
	}
 
	public void setPhone(String phone) {
		mPhone = phone;
	}
 
	public double getLatitude() {
		return mLatitude;
	}
 
	public void setLatitude(int latitude) {
		mLatitude = latitude;
	}
 
	public double getLongitude() {
		return mLongitude;
	}
 
	public void setLongitude(int longitude) {
		mLongitude = longitude;
	}
 
	public float getDistance() {
		return mDistance;
	}
 
	public void setDistance(float distance) {
		mDistance = distance;
	}
 
	@Override
	public boolean equals(Object o) {
		if (o instanceof Venue && ((Venue) o).getId() == mId) {
			return true;
		}
		return false;
	}
 
	@Override
	public int hashCode() {
		return Long.valueOf(mId).hashCode();
	}
 
	public int getPcode() {
		return mPcode;
	}
 
	public void setPcode(int pcode) {
		mPcode = pcode;
	}
 
	public String getImageUrl() {
		return mImageUrl;
	}
 
	public void setImageUrl(String imageUrl) {
		mImageUrl = imageUrl;
	}
 

    protected Venue(Parcel in) {
        mId = in.readLong();
        mPcode = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mName = in.readString();
        mAddress = in.readString();
        mCity = in.readString();
        mState = in.readString();
        mZip = in.readString();
        mPhone = in.readString();
        mTollFreePhone = in.readString();
        mUrl = in.readString();
        mDescription = in.readString();
        mTicketLink = in.readString();
        mImageUrl = in.readString();
        
        if (in.readByte() == 0x01) {
            mSchedule = new ArrayList<ScheduleItem>();
            in.readList(mSchedule, ScheduleItem.class.getClassLoader());
        } else {
            mSchedule = null;
        }
        mDistance = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeInt(mPcode);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mCity);
        dest.writeString(mState);
        dest.writeString(mZip);
        dest.writeString(mPhone);
        dest.writeString(mTollFreePhone);
        dest.writeString(mUrl);
        dest.writeString(mDescription);
        dest.writeString(mTicketLink);
        dest.writeString(mImageUrl);
        if (mSchedule == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSchedule);
        }
        dest.writeFloat(mDistance);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}
