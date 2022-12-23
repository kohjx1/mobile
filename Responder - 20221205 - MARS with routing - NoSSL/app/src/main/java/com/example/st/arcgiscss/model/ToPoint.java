package com.example.st.arcgiscss.model;

import org.json.JSONObject;

import java.io.Serializable;


public class ToPoint implements Serializable {
	
    private String lat;
    private String lng;
    
    
	public ToPoint () {
		
	}	
        
    public ToPoint (JSONObject json) {
    
        this.lat = json.optString("lat");
        this.lng = json.optString("lng");

    }
    
    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


    
}
