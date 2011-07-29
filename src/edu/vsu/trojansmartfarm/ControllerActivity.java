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
		doSampleDatabaseStuff("onCreate", tv);
		setContentView(tv);
	}
	
	/**
	 * Do our sample database stuff.
	 */
	private void doSampleDatabaseStuff(String action, TextView tv) {
		try {
			// get our dao
			Dao<IDTag, String> simpleDao = getHelper().getSimpleDataDao();
			// query for all of the data objects in the database
			List<IDTag> list = simpleDao.queryForAll();
			// our string builder for building the content-view
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
			//for (IDTag simple : list) {
			//int ret = simpleDao.delete(simple);
			//	sb.append("deleted id " + simple.getUpcCode() + " returned ").append(ret).append("\n");
			//	Log.i(LOG_TAG, "deleting simple(" + simple.getUpcCode() + ") returned " + ret);
			//	simpleC++;
			//}

			int createNum;
			do {
				createNum = new Random().nextInt(3) + 1;
			} while (createNum == list.size());
			for (int i = 0; i < createNum; i++) {
				// create a new simple object
				//String millis = String.valueOf(System.currentTimeMillis());
				//IDTag simple = new IDTag(millis, true, "Test");
				// store it in the database
				//simpleDao.create(simple);
				//Log.i(LOG_TAG, "created simple(" + millis + ")");
				// output it
				//sb.append("------------------------------------------\n");
				//sb.append("created new entry #").append(i + 1).append(":\n");
				//sb.append(simple).append("\n");
				//try {
				//	Thread.sleep(5);
				//} catch (InterruptedException e) {
					// ignore
				//}
			}

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
