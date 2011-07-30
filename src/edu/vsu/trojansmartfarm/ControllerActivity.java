package edu.vsu.trojansmartfarm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

public class ControllerActivity extends TrojanSmartFarmActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	protected static Date timestamp;
	protected static IDTag lastIDTagScanned;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "creating " + getClass());
		TextView tv = new TextView(this);
		initialSetup("onCreate", tv);
		setContentView(tv);
	}
	
	
	private void initialSetup(String action, TextView tv) {
		try {
			Dao<IDTag, String> simpleDao = getHelper().getSimpleDataDao();
			// query for all of the data objects in the database
			List<IDTag> list = simpleDao.queryForAll();
			// our string builder for building the content view
			StringBuilder sb = new StringBuilder();
			sb.append("Found ").append(list.size()).append(" tags in database.\n");

			// if we already have items in the database
			int simpleC = 0;
			for (IDTag simple : list) {
				sb.append("------------------------------------------\n");
				sb.append("[" + simpleC + "] = ").append(simple).append("\n");
				simpleC++;
			}
			sb.append("------------------------------------------\n");
			sb.append("Press MENU key for options.\n");

			tv.setText(sb.toString());
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Database exception", e);
			tv.setText("Database exeption: " + e.getMessage());
			return;
		}
	}

	@Override
	protected void scanCallback(String upc) {}

	@Override
	void usePhoto(Bitmap bmp) {
		// TODO Auto-generated method stub
		
	}
}
