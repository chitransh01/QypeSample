package com.qype.android;

import android.app.Activity;
import android.os.Bundle;

import com.qype.android.sdk.Qype;
import com.qype.android.sdk.QypeOAuthListener;

public class QypeActivity extends Activity {

	private static final String API_KEY    = "nm4LL0wkllyQL0oVCA0MA";
	private static final String API_SECRET = "jJVmOSIhN11AnKGm7O0HG1qVmESkhHSo9dFPXQZlFjw";
	
	private Qype mQype;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        try {
        	mQype = new Qype(this, API_KEY, API_SECRET);
        	if(mQype.isLoggedIn()) {
        		sampleRequests();
        	}else {
            	mQype.authorize(new QypeOAuthListener() {				
    				public void onQypeOAuthError(int errorCode, String description, String failingUrl) {}				
    				public void onQypeOAuthComplete() {
    					sampleRequests();
    				}				
    				public void onQypeOAuthCancel() {}
    			});        		
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	private void sampleRequests() { // note: you need a pro account for some of the following requests
		// test the OAuth success
		String url = "http://api.qype.com/oauth/test_request";
		System.out.println(mQype.request(url));
				
		// Check 'http://apidocs.qype.com/common_api_tasks' for common use of Qype API
		
		// 1. Searching for places
		String search_term = "restaurant", city = "paris";
		url = "http://api.qype.com/v1/places?show="+search_term+"&in="+city+"";
		System.out.println(mQype.request(url));
		
		// 2. Finding nearby places
		double latitude = 0.0, longitude = 0.0;
		url = "http://api.qype.com/v1/positions/"+latitude+","+longitude+"/places";
		System.out.println(mQype.request(url));
		
		// 3. Retrieve place details
		String place_id = "67370";
		url = "http://api.qype.com/v1/places/"+place_id;
		System.out.println(mQype.request(url));
		
		// 4. Get the reviews for the place with id='817694' in a JSON format 
		place_id = "817694";
		url = "http://api.qype.com/v1/reviews/"+place_id+".json";
		System.out.println(mQype.request(url));
		
		// 5. Checks authorized user into a place
		place_id = "67370";
		url = "http://api.qype.com/v1/places/"+place_id+"/checkins";
		System.out.println(mQype.request(url));
	}
}
