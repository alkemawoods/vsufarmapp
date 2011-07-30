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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

public class InsectDataActivity extends TrojanSmartFarmActivity {

	private final String LOG_TAG = getClass().getSimpleName();
	byte[] photo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.insectdata);
	  }    
	
	@Override
	protected void scanCallback(String upc) {
		
	}

	@Override
	void usePhoto(Bitmap bmp) {
		ImageView image = (ImageView) findViewById(R.id.insectPhoto);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		bmp.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
		byte[] bitmapdata = bos.toByteArray();
		photo = bitmapdata;
		findViewById(R.id.insectPhotoButton).setVisibility(android.view.View.INVISIBLE);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
		image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 50, 75, false));
	}
	
	public void saveInsectData(View v) throws SQLException {
		String notes = ((EditText) findViewById(R.id.insectNotesEdit)).getText().toString();
		boolean insectsPresent = ((CheckBox) findViewById(R.id.insectsCheck)).isChecked();
		IDTag tag = TrojanSmartFarmActivity.currentTag;
		InsectDataSet ids = new InsectDataSet(tag, TrojanSmartFarmActivity.timestamp, photo, notes, insectsPresent);
		Dao<InsectDataSet, Date> insectDao = new DBHelper(this).getInsectDao();
		insectDao.create(ids);
		Toast.makeText(this, "Data was saved.", Toast.LENGTH_LONG).show();
	}
	
	public void startDiseaseDataActivity(View v) {
		Intent intent = new Intent(this, DiseaseDataActivity.class);
		startActivity(intent);
	}
}
