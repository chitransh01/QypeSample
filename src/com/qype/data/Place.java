package com.qype.data;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class Place extends Resource {

	public static final String TAG = Place.class.getSimpleName();
	
	public Address address;
	public String title;
	public boolean closed;
	public String email;
	public double average_rating;
	public boolean explicit_content;
	public String phone;
	//image
	public String owner_description_text;
	
	public String opening_hours;	
	public List<Category> categories;
	public String url;
	//
	public boolean liked;
	public double latitude, longitude;
		
	public static Place parseJSONObject(JSONObject obj) {		
		Place place = new Place();
		try {					
			place.id = obj.getString("id");
			place.created = sDateFormat.parse(obj.getString("created"));
			place.updated = sDateFormat.parse(obj.getString("updated"));
			place.liked = obj.getBoolean("liked");
			place.address = sGson.fromJson(obj.getString("address"), Address.class);
			place.title = obj.getString("title");
			place.closed = obj.getBoolean("closed");
			place.email = obj.getString("email");
			place.average_rating = obj.getDouble("average_rating");
			place.explicit_content = obj.getBoolean("explicit_content");
			place.phone = obj.getString("phone");
			place.owner_description_text = obj.getString("owner_description_text");
			
			place.links = sGson.fromJson(obj.getString("links"), new TypeToken<List<Link>>(){}.getType());
			place.opening_hours = obj.getString("opening_hours");
			Type collectionType = new TypeToken<List<Category>>(){}.getType();
			place.categories = sGson.fromJson(obj.getString("categories"), collectionType);
						
			place.url = obj.getString("url");
			// ...
			String[] point = obj.getString("point").split(",");
			place.latitude = Double.parseDouble(point[0]);
			place.longitude = Double.parseDouble(point[1]);
		}catch(Exception e) {			
			e.printStackTrace();
		}	
		Log.i(TAG, place.toString());		
		return place;
	}	
	
	public class Address {
		
		public String postcode;
		public String housenumber;
		public String country_code;
		public String comment;
		public String street;
		public String city;
		
		@Override public String toString() {
			return "Address [postcode=" + postcode + ", housenumber="
					+ housenumber + ", country_code=" + country_code
					+ ", comment=" + comment + ", street=" + street + ", city="
					+ city + "]";
		}
	}
	
	public class Category extends Resource {
		public Title title;
		public Title full_title;
		
		public class Title {
			String lang, value;

			@Override
			public String toString() {
				return "Title [lang=" + lang + ", value=" + value + "]";
			}
			
		}
		@Override
		public String toString() {
			return "Category [title=" + title + ", full_title=" + full_title
					+ "]";
		}
		
	}
	
	@Override
	public String toString() {
		return "Place [address=" + address + ", title=" + title + ", closed="
				+ closed + ", email=" + email + ", average_rating="
				+ average_rating + ", explicit_content=" + explicit_content
				+ ", phone=" + phone + ", owner_description_text="
				+ owner_description_text + ", links=" + links
				+ ", opening_hours=" + opening_hours + ", categories="
				+ categories + ", url=" + url + ", liked=" + liked
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
