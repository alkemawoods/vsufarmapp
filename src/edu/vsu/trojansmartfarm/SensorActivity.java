package edu.vsu.trojansmartfarm;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
 
public class SensorActivity extends ListActivity {
 
    private final String LOG_TAG = getClass().getName();

	/** Called when the activity is first created. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensordata);
 
        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.fetchHTService()));
    }
 
    public ArrayList<String> fetchHTService()
    {
        ArrayList<String> listItems = new ArrayList<String>();
 
        try {
            URL htservice = new URL(
                    "http://turing.mathcs.vsu.edu/~kdamevski/HTService/amfphp/json.php/HTService.getLatestData");
            URLConnection tc = htservice.openConnection();
            BufferedReader in = 
            	new BufferedReader(new InputStreamReader(tc.getInputStream()));
 
            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
 
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    listItems.add(jo.getString("result_time") + "\nhumid="+jo.getString("humid") + " humtemp=" + jo.getString("humtemp"));
                }
            }
        } catch (Exception e) {
        	Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        } 
        return listItems;
    }
}