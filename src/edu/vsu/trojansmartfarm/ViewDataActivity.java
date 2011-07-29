package edu.vsu.trojansmartfarm;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.j256.ormlite.dao.Dao;

public class ViewDataActivity extends ListActivity {
	
	IDTag tagScanned;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensordata);
		
		IntentIntegrator.initiateScan(this);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup() {
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, this.fetchTagData()));
	}

	public ArrayList<String> fetchTagData() {
		ArrayList<String> listItems = new ArrayList<String>();
		
		try {
			
		List<GrowthDataSet> gdsList =  new DBHelper(this).getGrowthDao()
			.queryForEq("tag_id", tagScanned.getUpcCode());

		if(tagScanned != null)
		((TextView) findViewById(R.id.metatext))
			.setText("Control?: " + tagScanned.getIsControl() 
					+" Variety: " + tagScanned.getPlantVariety());
		
		for(GrowthDataSet item : gdsList) {
			listItems.add(item.getTimestamp().toString());
		}
		
		return listItems;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		switch(requestCode) { 
			case IntentIntegrator.REQUEST_CODE: { 
				if (resultCode != RESULT_CANCELED) { 
					IntentResult scanResult = 
						IntentIntegrator.parseActivityResult(requestCode, resultCode, data); 
			        if (scanResult != null) { 
			        	DBHelper dbh = new DBHelper(this);
			    		try {
			    			Dao<IDTag, String> idTagDao = dbh.getIDTagDao();
			    			tagScanned =
			    				idTagDao.queryForId(scanResult.getContents());
			    			setup();
			    		} 
			    		catch(SQLException sqlex) {
			    			Toast.makeText(this, "SQL error occurred!", Toast.LENGTH_LONG);
			    			//Log.e(LOG_TAG, "SQLException was thrown here.");
			    		}
			        } 
			    } 
			    break;
			}
		}
	} 
}
