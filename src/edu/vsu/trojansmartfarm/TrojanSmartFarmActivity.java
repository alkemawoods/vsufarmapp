package edu.vsu.trojansmartfarm;

import java.sql.SQLException;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

abstract public class TrojanSmartFarmActivity extends
		OrmLiteBaseActivity<DBHelper> {
	private final String LOG_TAG = getClass().getSimpleName();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.app_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.tag_menitem:
			startTagActivity();
			return true;
		case R.id.location_menitem:
			startLocationActivity();
			return true;
		case R.id.newdata_menitem:
			ControllerActivity.timestamp = new Date();
			startGrowthDataActivity();
			return true;
		case R.id.sensordata_menitem:
			startSensorActivity();
			return true;
		case R.id.scan_menitem:
			startViewDataActivity();
			return true;
		case R.id.exit_menitem:
			System.exit(0);
			Intent intent = new Intent(this, ControllerActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void startTagActivity() {
		Intent intent = new Intent(this, TagActivity.class);
		startActivity(intent);
	}

	public void startSensorActivity() {
		Intent intent = new Intent(this, SensorActivity.class);
		startActivity(intent);
	}

	public void startViewDataActivity() {
		Intent intent = new Intent(this, ViewDataActivity.class);
		startActivity(intent);
	}

	public void startWeatherActivity() {
		Intent intent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("http://forecast.weather.gov/MapClick.php?CityName=Petersburg&state=VA&site=AKQ&textField1=37.2043&textField2=-77.3926"));
		// Intent intent = new Intent(this, WeatherActivity.class);
		if (intent != null)
			startActivity(intent);
	}

	public void startLocationActivity() {
		Intent intent = new Intent(this, LocationActivity.class);
		startActivity(intent);
	}

	public void startGrowthDataActivity() {
		Intent intent = new Intent(this, GrowthDataActivity.class);
		startActivity(intent);
	}

	// opens barcode scanner app when tag id button is clicked
	public void startScan(View v) {
		IntentIntegrator.initiateScan(this);
	}

	final int PHOTO_RESULT = 7462;
	protected int photoBtnViewId = 0;

	public void takePhoto(View v) {
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(camera, PHOTO_RESULT);
		photoBtnViewId = v.getId();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			switch (requestCode) {
			case IntentIntegrator.REQUEST_CODE: {
				if (resultCode != RESULT_CANCELED) {
					IntentResult scanResult = IntentIntegrator
							.parseActivityResult(requestCode, resultCode, data);
					if (scanResult != null) {
						scanCallback(scanResult.getContents());
					}
				}
				break;
			}
			case PHOTO_RESULT: {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				usePhoto(thumbnail);
			}
			}
		} catch (NullPointerException npe) {
		}
	}

	public IDTag getIDTag(Context context, String upc) {
		DBHelper dbh = new DBHelper(context);
		try {
			Dao<IDTag, String> idTagDao = dbh.getIDTagDao();
			return idTagDao.queryForId(upc);
		} catch (SQLException sqlex) {
			Toast.makeText(this, "SQL error occured!", Toast.LENGTH_LONG);
			Log.e(LOG_TAG, "SQLException was thrown here.");
			return null;
		}
	}

	// does something with UPC code from scanner
	abstract protected void scanCallback(String upc);

	// does something with photo
	abstract void usePhoto(Bitmap bmp);
}
