package com.qype.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/** source: http://apidocs.qype.com/checkins_resource*/
public class Checkin extends Resource {
	
	public static final String TAG = Checkin.class.getSimpleName();
	
	public boolean liked;
	public boolean became_champion;
	public boolean active;
	public Date created_at;
	public double latitude, longitude;
	
	@Override
	public String toString() {
		return "Checkin [id=" + id + ", created=" + created + ", updated="
				+ updated + ", liked=" + liked + ", became_champion="
				+ became_champion + ", active=" + active + ", created_at="
				+ created_at + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

	public static List<Checkin> parseJSON(String string) {
		List<Checkin> checkins = new ArrayList<Checkin>();
		try {
			JSONObject jsonObject = new JSONObject(string);
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			int length = jsonArray.length();
			for(int i=0; i<length; i++) {
				JSONObject obj= jsonArray.getJSONObject(i).getJSONObject("checkin");
				Checkin checkin = parseJSONObject(obj); //sGson.fromJson(obj.toString(), Checkin.class);  
				checkins.add(checkin);
				Log.i(TAG, i+": "+checkin);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return checkins;
	}
	
	public static Checkin parseJSONObject(JSONObject obj) {
		Checkin checkin = new Checkin();
		try {					
			checkin.id = obj.getString("id");
			checkin.created = sDateFormat.parse(obj.getString("created"));
			checkin.updated = sDateFormat.parse(obj.getString("updated"));
			checkin.liked = obj.getBoolean("liked");
			checkin.became_champion = obj.getBoolean("became_champion");
			checkin.active = obj.getBoolean("active");
			checkin.created_at = sDateFormat.parse(obj.getString("created_at"));
			String[] point = obj.getString("point").split(",");
			checkin.latitude = Double.parseDouble(point[0]);
			checkin.longitude = Double.parseDouble(point[1]);
		}catch(Exception e) {			
			e.printStackTrace();
		}		
		return checkin;
	}
}
