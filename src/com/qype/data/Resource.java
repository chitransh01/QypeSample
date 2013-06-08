package com.qype.data;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**source: http://apidocs.qype.com/resource_reference */
public class Resource {
	
	public static final String TAG = Resource.class.getSimpleName();
	
	public static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
	public static final Gson sGson;// = new Gson();
	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				try {
	                return sDateFormat.parse(json.getAsString());
	            } catch (ParseException e) {
	                return null;
	            }
			}
		});
	    sGson = gsonBuilder.create();
	}
	
	public String id;
	public Date created;
	public Date updated;
	public List<Link> links;
	
	public class Link {
		public int count;
		public String href;
		public String title;
		public String rel;
		@Override public String toString() {
			return "Link [count=" + count + ", href=" + href + ", title=" + title + ", rel=" + rel + "]";
		}		
	}
	
	@Override
	public String toString() {
		return "Resource [id=" + id + ", created=" + created + ", updated="+ updated + "]";
	}
}
