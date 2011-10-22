package edu.vsu.trojansmartfarm;

import java.util.Date;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ControllerActivity extends TrojanSmartFarmActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	protected static Date timestamp;
	protected static IDTag newlyCreatedTag;
	protected static IDTag activeTag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		setContentView(R.layout.controllerview);
	}
	
	public void startWeatherActivity(View v) {
		startWeatherActivity();
	}

	@Override
	protected void scanCallback(String upc) {}

	@Override
	void usePhoto(Bitmap bmp) {}
}
