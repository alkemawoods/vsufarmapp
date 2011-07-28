package edu.vsu.trojansmartfarm;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ViewDataActivity extends ListActivity {

	
	/** Called when the activity is first created. */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensordata);
		
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, this.fetchTagData()));
	}

	public ArrayList<String> fetchTagData() {
		ArrayList<String> listItems = new ArrayList<String>();
		try {
		List<InsectDataSet> gdsList =  new DBHelper(this).getInsectDao()
			.queryForEq("upcCode", TrojanSmartFarmActivity.currentTag.getUpcCode());
		for(InsectDataSet item : gdsList) {
			listItems.add(item.getInsectsPresent() + ":" + item.getTimestamp());
		}
		
		return listItems;
		}
		catch(Exception e) {
			return null;
		}
	}
}
