package edu.vsu.trojansmartfarm;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
		setContentView(R.layout.dataview);
		
		IntentIntegrator.initiateScan(this);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup() {
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, this.fetchTagData()));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TextView tv = (TextView) v;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(tv.getText());
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	public ArrayList<String> fetchTagData() {
		ArrayList<String> listItems = new ArrayList<String>();
		
		try {
			
		List<GrowthDataSet> gdsList =  new DBHelper(this).getGrowthDao()
			.queryForEq("tag_id", tagScanned.getUpcCode());
		InsectDataSet ids;
		DiseaseDataSet dds;
		MaintenanceDataSet mds;

		if(tagScanned != null)
		((TextView) findViewById(R.id.metatext))
			.setText("Control?: " + tagScanned.getIsControl() 
					+" Variety: " + tagScanned.getPlantVariety());
		
		StringBuilder sb;
		Date date = new Date();
		for(GrowthDataSet item : gdsList) {
			sb = new StringBuilder();
			date = item.getTimestamp();
			sb.append(date);
			sb.append("\nheight: " + item.getHeightOfPlant());
			sb.append("\n#leaves: " + item.getNumOfLeaves());
			sb.append("\n#berries: " + item.getNumOfBerries());
			sb.append("\nnotes: " + item.getNotes());
			
			ids = new DBHelper(this).getInsectDao().queryForId(date);
			if(ids != null) {
				sb.append("\ninsects?: " + ids.getInsectsPresent());
				sb.append("\nnotes: " + ids.getNotes());
			}
			
			dds = new DBHelper(this).getDiseaseDao().queryForId(date);
			if(dds != null) {
				sb.append("\ndisease?: " + dds.getDiseasePresent());
				sb.append("\nnotes: " + dds.getNotes());
			}
			
			mds = new DBHelper(this).getMaintenanceDao().queryForId(date);
			if(mds != null) {
				sb.append("\npruned?: " + mds.getPruned());
				sb.append("\nshock treated?: " + mds.getShockTreated());
				sb.append("\nnotes: " + mds.getNotes());
			}
			
			listItems.add(sb.toString());
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
