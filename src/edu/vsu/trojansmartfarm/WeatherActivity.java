// This class is experimental and does not currently work as implemented
package edu.vsu.trojansmartfarm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.resting.Resting;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.BasicRequestParams;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.component.impl.xml.XMLAlias;
//import com.google.resting.component.impl.ServiceResponse;

public class WeatherActivity extends Activity {
	private final String LOG_TAG = getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RequestParams params = new BasicRequestParams(); 

		params.add("lat", "39.0000");
		params.add("lon", "77.0000");
		params.add("product", "time-series");
		params.add("begin", "20011-04-27T12:00");
		params.add("end", "2004-04-28T12:00");
		ServiceResponse response=Resting.get("http://local.yahooapis.com/MapsService/V1/geocode",80,params); 

		@SuppressWarnings("rawtypes")
		XMLAlias alias=new XMLAlias().add("Result", Result.class).add("ResultSet", ResultSet.class); 

		ResultSet resultset=Resting.getByXML("http://www.weather.gov/forecasts/xml/sample_products/browser_interface/ndfdXMLclient.php", 80,params, ResultSet.class, alias);
		
		Log.i(LOG_TAG, "creating " + getClass());
		ScrollView sv = new ScrollView(this);
		TextView tv = new TextView(this);
		sv.addView(tv);
		Result result = resultset.Result;
		tv.setText(result.toString());
		setContentView(sv);
	}
}

class ResultSet {
	 Result Result;
	}

class Result {
	 private String precision;
	 private String Latitude;
	 private String Longitude;
	 private String Address;
	 private String City;
	 private String State;
	 private String Zip;
	 private String Country;
	 
	 @Override
	 public String toString() {
		 return precision+Latitude+Longitude+Address + City + State + Zip + Country;
	 }
	}
