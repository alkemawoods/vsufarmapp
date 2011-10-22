package edu.vsu.trojansmartfarm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocationActivity extends ListActivity {
//	private final String TAG = getClass().getSimpleName();
	ArrayList<IDTag> listOfTags = new ArrayList<IDTag>();
	
	ProgressDialog dialog;
	boolean timesUp = false;
	double lat = 0, lon = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dataview);
		
		final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		 dialog = ProgressDialog.show(LocationActivity.this, "", 
               "Obtaining location...", true);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timesUp = true;
			} }, (long) 1000 * 20);
		
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		       if(timesUp) {
		    	   lat = location.getLatitude();
		    	   lon = location.getLongitude();
		    	   dialog.dismiss();
		    	   locationManager.removeUpdates(this);
		    	   setup();
		       }
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}
		    public void onProviderEnabled(String provider) {}
		    public void onProviderDisabled(String provider) {}
		  };
		 
		 // Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup() {
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, getNearbyTags()));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, ViewDataActivity.class);
		ViewDataActivity.GPS_LOC = true;
		ViewDataActivity.tagScanned = listOfTags.get(position);
		startActivity(intent);
	}
	
	public ArrayList<IDTag> getNearbyTags()  {
		DBHelper dataGet = new DBHelper(this);
		ArrayList<IDTag> tagList = null;
		try {
			tagList = (ArrayList<IDTag>) dataGet.getIDTagDao().queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(IDTag tag : tagList) {
			if(Math.abs(tag.getLatitude() - lat) > 0.0005 && Math.abs(tag.getLongitude() - lon) > 0.0005) {
				tagList.remove(tag);
			}
		}
		listOfTags = tagList;
		return tagList;
	}

}
