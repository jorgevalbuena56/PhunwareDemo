package com.phunware.phunwaredemo.event;

import retrofit.RetrofitError;

public class VenueErrorEvent {

	private RetrofitError error;
    
    public VenueErrorEvent(RetrofitError error){          
        this.error = error;
    }
     
    public RetrofitError getError(){
        return error;
    }
}
