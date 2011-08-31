package edu.vsu.trojansmartfarm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

public class TagActivity extends TrojanSmartFarmActivity {
	
	private final String LOG_TAG = getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.tag);
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
		IDTag tag = new IDTag(upcCode, isControl, variety);
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
