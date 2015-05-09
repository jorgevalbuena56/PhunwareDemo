package com.phunware.phunwaredemo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.phunware.phunwaredemo.R;
 
/**
 * POJO Representing the Schedule
 * @author GeorgeLocal
 *
 */
public class ScheduleItem implements Parcelable {
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss Z");
 
	@SerializedName("start_date")
	private Date mStartDate;
	@SerializedName("end_date")
	private Date mEndDate;
 
	public ScheduleItem() {
 
	}
 
	public ScheduleItem(Date startDate, Date endDate) {
		mStartDate = startDate;
		mEndDate = endDate;
	}
 
	public ScheduleItem(String startDate, String endDate) throws ParseException {
		mStartDate = FORMATTER.parse(startDate);
		mEndDate = FORMATTER.parse(endDate);
	}
 
	public Date getStartDate() {
		return mStartDate;
	}
 
	public void setStartDate(Date startDate) {
		mStartDate = startDate;
	}
 
	public Date getEndDate() {
		return mEndDate;
	}
 
	public void setEndDate(Date endDate) {
		mEndDate = endDate;
	}
 
	public void setStartDate(String startDate) throws ParseException {
		mStartDate = FORMATTER.parse(startDate);
	}
 
	public void setEndDate(String endDate) throws ParseException {
		mEndDate = FORMATTER.parse(endDate);
	}
 
	public String getStartDateString() {
		return FORMATTER.format(mStartDate);
	}
 
	public String getEndDateString() {
		return FORMATTER.format(mEndDate);
	}
 
	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof ScheduleItem) {
			result = mStartDate.equals(((ScheduleItem) o).getStartDate())
					&& mEndDate.equals(((ScheduleItem) o).getEndDate());
		}
		return result;
	}
 
	@Override
	public int hashCode() {
		String s = getStartDateString() + getEndDateString();
		return s.hashCode();
	}
 

    protected ScheduleItem(Parcel in) {
        long tmpMStartDate = in.readLong();
        mStartDate = tmpMStartDate != -1 ? new Date(tmpMStartDate) : null;
        long tmpMEndDate = in.readLong();
        mEndDate = tmpMEndDate != -1 ? new Date(tmpMEndDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mStartDate != null ? mStartDate.getTime() : -1L);
        dest.writeLong(mEndDate != null ? mEndDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ScheduleItem> CREATOR = new Parcelable.Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel in) {
            return new ScheduleItem(in);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };

	@Override
	public String toString() {
        StringBuilder builder = new StringBuilder(android.text.format.DateFormat.format("E M/dd h:mm a", getStartDate()))
		.append(" ").append("to").append(" ")
		.append(android.text.format.DateFormat.format("h:mm a", getEndDate()));

		return builder.toString();
	}
    
    
}
