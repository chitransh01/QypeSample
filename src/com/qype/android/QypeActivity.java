package com.qype.android;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qype.android.sdk.Qype;
import com.qype.android.sdk.QypeOAuthListener;
import com.qype.data.Checkin;
import com.qype.data.Place;

public class QypeActivity extends ListActivity {
	
	public static final String TAG = QypeActivity.class.getSimpleName();

	/**more details on http://apidocs.qype.com/resource_reference */
	public enum ResourceType {None, Place, Places, Reviews, Checkins, Assets, Users, Locators, PlaceCategories, Coupons, Positions, BoundingBoxes, ContactRequests, Contacts, Badges, Events};
	
	private static final String API_KEY    = "";
	private static final String API_SECRET = "";
	
	private Qype mQype;
	private String[] items = new String[]{
			"test the OAuth success", "Searching for places", "Finding nearby places", "Retrieve place details", 
			"Get the reviews for the place with id='817694' in a JSON format", "Checks authorized user into a place"
			}; 
	private ArrayList<String> urls = new ArrayList<String>();
	private ArrayList<ResourceType> resources = new ArrayList<ResourceType>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items));
        initQype();                
	}
	
	private void initQype() { // note: you need a pro account for some of the following requests
		mQype = new Qype(this, API_KEY, API_SECRET);
		// test the OAuth success
		String url = "http://api.qype.com/oauth/test_request"+".json";
		urls.add(url);
		resources.add(ResourceType.None);
						
		// Check 'http://apidocs.qype.com/common_api_tasks' for common use of Qype API
				
		// 1. Searching for places
		String search_term = "restaurant", city = "paris";
		url = "http://api.qype.com/v1/places?show="+search_term+"&in="+city+".json";
		urls.add(url);
		resources.add(ResourceType.Places);
		
		// 2. Finding nearby places
		double latitude = 0.0, longitude = 0.0;
		url = "http://api.qype.com/v1/positions/"+latitude+","+longitude+"/places"+".json";
		urls.add(url);
		resources.add(ResourceType.Places);
		
		// 3. Retrieve place details
		String place_id = "67370";
		url = "http://api.qype.com/v1/places/"+place_id+".json";
		urls.add(url);
		resources.add(ResourceType.Place);
				
		// 4. Get the reviews for the place with id='817694' in a JSON format 
		place_id = "817694";
		url = "http://api.qype.com/v1/reviews/"+place_id+".json";
		urls.add(url);
		resources.add(ResourceType.Reviews);
		
		// 5. Checks authorized user into a place
		place_id = "67370";
		url = "http://api.qype.com/v1/places/"+place_id+"/checkins"+".json";
		urls.add(url);		
		resources.add(ResourceType.Checkins);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final String url = urls.get(position);
		final ResourceType res = resources.get(position);
		/*Intent intent = new Intent();
		intent.putExtra("url", urls.get(position));
		startActivity(intent);*/
		try {
			if(mQype.isLoggedIn()) {
				handleResponse(res, mQype.request(url));
        	}else {
            	mQype.authorize(new QypeOAuthListener() {				
    				public void onQypeOAuthError(int errorCode, String description, String failingUrl) {}				
    				public void onQypeOAuthComplete() {
    					handleResponse(res, mQype.request(url));
    				}				
    				public void onQypeOAuthCancel() {}
    			});        		
        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void handleResponse(ResourceType resource, String response) {		
		switch(resource) {
		case Checkins:			
			Checkin.parseJSON(response);
			break;
		case Place:
			try {
				Place.parseJSONObject(new JSONObject(response).getJSONObject("place"));				
			}catch (Exception e) {}			
			break;
		case Places:
			Log.i(TAG, "Places: "+response);
			break;
		case Reviews:
			Log.i(TAG, "Reviews: "+response);
			break;
		default:
			Log.i(TAG, response);
		}
	}
}
