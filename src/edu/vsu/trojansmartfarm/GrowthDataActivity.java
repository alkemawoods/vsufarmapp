package edu.vsu.trojansmartfarm;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.j256.ormlite.dao.Dao;

public class GrowthDataActivity extends TrojanSmartFarmActivity {

	private final String LOG_TAG = getClass().getSimpleName();
	byte[] photo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.growthdata);
		
		if(ControllerActivity.newlyCreatedTag == null) 
			IntentIntegrator.initiateScan(this);
		else {
			ControllerActivity.activeTag = getIDTag(this, ControllerActivity.newlyCreatedTag.getUID());
			ControllerActivity.newlyCreatedTag = null;
		}
	  }    
	
	@Override
	protected void scanCallback(String upc) {
		ControllerActivity.activeTag = getIDTag(this, upc);
		if(ControllerActivity.activeTag != null)
			Toast.makeText(this, "Found " + ControllerActivity.activeTag.getUID(), Toast.LENGTH_LONG).show();
		else {
			Toast.makeText(this, "Tag was not found in database.", Toast.LENGTH_LONG).show();
			startTagActivity();
		}
	}

	@Override
	void usePhoto(Bitmap bmp) {
		ImageView image = (ImageView) findViewById(R.id.growthPhoto);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		bmp.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
		byte[] bitmapdata = bos.toByteArray();
		photo = bitmapdata;
		findViewById(R.id.growthPhotoButton).setVisibility(android.view.View.INVISIBLE);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
		image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 150, false));
	}
	
	public void saveGrowthData(View v) throws SQLException {
		String notes = ((EditText) findViewById(R.id.notesEdit)).getText().toString();
		int numOfLeaves = Integer.parseInt(((EditText) findViewById(R.id.numLeavesEdit)).getText().toString());
		int numOfBerries = Integer.parseInt(((EditText) findViewById(R.id.numBerriesEdit)).getText().toString());
		int heightOfPlant = Integer.parseInt(((EditText) findViewById(R.id.heightEdit)).getText().toString());
		IDTag tag = ControllerActivity.activeTag;
		ControllerActivity.timestamp = new Date();
		GrowthDataSet gds = new GrowthDataSet(tag, ControllerActivity.timestamp, photo, notes, 
			numOfLeaves, numOfBerries, heightOfPlant);
		Dao<GrowthDataSet, Date> growthDao = new DBHelper(this).getGrowthDao();
		growthDao.create(gds);
		Toast.makeText(this, "Growth data saved.", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, InsectDataActivity.class);
		startActivity(intent);
	}
}
