package edu.vsu.trojansmartfarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

public class TagActivity extends TrojanSmartFarmActivity  {
	
	private final String LOG_TAG = getClass().getSimpleName();
	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.tag);
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		 dialog = ProgressDialog.show(TagActivity.this, "", 
                "Obtaining location...", true);
		
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		       useLocation(location);
		       dialog.dismiss();
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}
		    public void onProviderEnabled(String provider) {}
		    public void onProviderDisabled(String provider) {}
		  };
		 
		 // Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
	}
	
	double lat = 0, lon = 0;
	
	protected void useLocation(Location location) {
		EditText et = (EditText) findViewById(R.id.gps_edit);
		lat = location.getLatitude();
		lon = location.getLongitude();
		et.setText(lat + ", " + lon);
	}
	
	
	final protected void scanCallback(String upc) {
		EditText upcText = (EditText) findViewById(R.id.upcText);
		upcText.setText(upc);
	}
	
	public void createTag(View v) throws java.sql.SQLException {
		Dao<IDTag, String> dao = new DBHelper(this).getIDTagDao();
		String upcCode, variety;
		boolean isControl;
		
		EditText et = (EditText) findViewById(R.id.upcText);
		upcCode =  et.getText().toString();
		et = (EditText) findViewById(R.id.varietyText);
		variety =  et.getText().toString();
		RadioButton rb = (RadioButton) findViewById(R.id.controlRadio);
		isControl = rb.isChecked();
		IDTag tag = new IDTag(upcCode, isControl, variety, lat, lon);
		dao.createOrUpdate(tag);
		ControllerActivity.newlyCreatedTag = tag;
		Toast.makeText(this, "Tag was created/updated successfully.", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, GrowthDataActivity.class));	
	}

	@Override
	void usePhoto(Bitmap bmp) {
		// TODO Auto-generated method stub
		
	}

}
