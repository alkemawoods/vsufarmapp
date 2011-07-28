package edu.vsu.trojansmartfarm;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Date;

import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DiseaseDataActivity extends TrojanSmartFarmActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	byte[] photo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.diseasedata);
	  }    
	
	@Override
	protected void scanCallback(String upc) {
		
	}

	@Override
	void usePhoto(Bitmap bmp) {
		ImageView image = (ImageView) findViewById(R.id.diseasePhoto);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		bmp.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
		byte[] bitmapdata = bos.toByteArray();
		photo = bitmapdata;
		findViewById(R.id.diseasePhotoButton).setVisibility(android.view.View.INVISIBLE);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
		image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 50, 75, false));
	}
	
	public void saveDiseaseData(View v) throws SQLException {
		String notes = ((EditText) findViewById(R.id.diseaseNotesEdit)).getText().toString();
		boolean diseasePresent = ((CheckBox) findViewById(R.id.diseaseCheck)).isChecked();
		IDTag tag = TrojanSmartFarmActivity.currentTag;
		DiseaseDataSet dds = new DiseaseDataSet(tag, new Date(), photo, notes, diseasePresent);
		Dao<DiseaseDataSet, Date> diseaseDao = new DBHelper(this).getDiseaseDao();
		diseaseDao.create(dds);
		Toast.makeText(this, "Data was saved.", Toast.LENGTH_LONG).show();
	}
	
	public void startMaintenanceDataActivity(View v) {
		Intent intent = new Intent(this, MaintenanceDataActivity.class);
		startActivity(intent);
	}
	
}
