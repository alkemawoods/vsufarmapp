package edu.vsu.trojansmartfarm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.j256.ormlite.dao.Dao;

public class ViewDataActivity extends ListActivity {
	private final String TAG = getClass().getSimpleName();
	public static boolean GPS_LOC = false;

	static IDTag tagScanned;
	ArrayList<Date> listOfDates = new ArrayList<Date>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dataview);

		if (!GPS_LOC)
			IntentIntegrator.initiateScan(this);
		else
			setup();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup() {
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, this.fetchTagData()));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		GrowthDataSet growthData = null;
		InsectDataSet insectData = null;
		DiseaseDataSet diseaseData = null;
		MaintenanceDataSet maintenanceData = null;

		try {
			Date timestamp = listOfDates.get(position);
			DBHelper dataGet = new DBHelper(this);
			growthData = dataGet.getGrowthDao().queryForId(timestamp);
			insectData = dataGet.getInsectDao().queryForId(timestamp);
			diseaseData = dataGet.getDiseaseDao().queryForId(timestamp);
			maintenanceData = dataGet.getMaintenanceDao().queryForId(timestamp);
			Log.i(TAG, "Retrieved data for " + timestamp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", "ERRORRRRRRRR");
		}
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.datadetailview,
				(ViewGroup) findViewById(R.id.data_scroll));
		builder.setView(layout);

		TextView growthText = (TextView) layout
				.findViewById(R.id.growth_data_txt);
		ImageView growthImage = (ImageView) layout
				.findViewById(R.id.growth_image);

		TextView insectText = (TextView) layout
				.findViewById(R.id.insect_data_txt);
		ImageView insectImage = (ImageView) layout
				.findViewById(R.id.insect_image);

		TextView diseaseText = (TextView) layout
				.findViewById(R.id.disease_data_txt);
		ImageView diseaseImage = (ImageView) layout
				.findViewById(R.id.disease_image);

		TextView maintenanceText = (TextView) layout
				.findViewById(R.id.maintenance_data_txt);
		ImageView maintenanceImage = (ImageView) layout
				.findViewById(R.id.maintenance_image);

		if (growthData != null) {
			StringBuilder output = new StringBuilder();
			// get growth data and set view
			output.append("GROWTH DATA:");
			output.append("\nLeaf Count: " + growthData.getNumOfLeaves());
			output.append("\nBerry Count: " + growthData.getNumOfBerries());
			output.append("\nHeight (cm): " + growthData.getHeightOfPlant());
			output.append("\nNotes: " + growthData.getNotes());
			growthText.setText(output.toString());
			try {
				byte[] bitmapdata = growthData.getPhoto();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
						bitmapdata.length);
				growthImage.setImageBitmap(bitmap);
			} catch (NullPointerException npe) {
			}

			// get insect data and set view
			output = new StringBuilder();
			output.append("INSECT DATA:");
			output.append("\nInsects Present: "
					+ insectData.getInsectsPresent());
			output.append("\nNotes: " + insectData.getNotes());
			insectText.setText(output.toString());
			output = new StringBuilder();
			try {
				byte[] bitmapdata = insectData.getPhoto();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
						bitmapdata.length);
				insectImage.setImageBitmap(bitmap);
			} catch (NullPointerException npe) {
			}

			// get disease data and set view
			output = new StringBuilder();
			output.append("DISEASE DATA:");
			output.append("\nDisease Present: "
					+ diseaseData.getDiseasePresent());
			output.append("\nNotes: " + insectData.getNotes());
			diseaseText.setText(output.toString());
			output = new StringBuilder();
			try {
				byte[] bitmapdata = diseaseData.getPhoto();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
						bitmapdata.length);
				diseaseImage.setImageBitmap(bitmap);
			} catch (NullPointerException npe) {
			}

			// get maintenance data and set view
			output = new StringBuilder();
			output.append("MAINTENANCE DATA:");
			output.append("\nShocked?: " + maintenanceData.getShockTreated());
			output.append("\nPruned?: " + maintenanceData.getPruned());
			output.append("\nNotes: " + insectData.getNotes());
			maintenanceText.setText(output.toString());
			output = new StringBuilder();
			try {
				byte[] bitmapdata = diseaseData.getPhoto();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
						bitmapdata.length);
				maintenanceImage.setImageBitmap(bitmap);
			} catch (NullPointerException npe) {
			}
		} else {
			builder = new AlertDialog.Builder(this);
			builder.setTitle("No data.");
			builder.setMessage("No data was found.");
		}

		AlertDialog alert = builder.create();
		alert.show();
	}

	public ArrayList<Date> fetchTagData() {
		ArrayList<Date> listItems = new ArrayList<Date>();

		try {

			List<GrowthDataSet> gdsList = new DBHelper(this).getGrowthDao()
					.queryForEq("tag_id", tagScanned.getUID());
			InsectDataSet ids;
			DiseaseDataSet dds;
			MaintenanceDataSet mds;

			if (tagScanned != null)
				((TextView) findViewById(R.id.metatext)).setText("Control?: "
						+ tagScanned.getIsControl() + " Variety: "
						+ tagScanned.getPlantVariety());

			StringBuilder sb;
			Date date = new Date();
			for (GrowthDataSet item : gdsList) {
				sb = new StringBuilder();
				date = item.getTimestamp();
				listOfDates.add(date);
				sb.append(date);
				sb.append("\n\n\nGROWTH DATA\nHeight: "
						+ item.getHeightOfPlant() + "cm");
				sb.append("\nNumber of Leaves: " + item.getNumOfLeaves());
				sb.append("\n#Berries: " + item.getNumOfBerries());
				sb.append("\nNotes: " + item.getNotes());

				// TextView growth = (TextView)
				// findViewById(R.id.data_scroll).findViewById(R.id.growth_data_txt);
				// growth.setText(sb.toString());

				ids = new DBHelper(this).getInsectDao().queryForId(date);
				if (ids != null) {
					sb.append("\n\nINSECT DATA\nInsects present?: "
							+ ids.getInsectsPresent());
					sb.append("\nNotes: " + ids.getNotes());
				}

				dds = new DBHelper(this).getDiseaseDao().queryForId(date);
				if (dds != null) {
					sb.append("\n\nDISEASE DATA\nDisease?: "
							+ dds.getDiseasePresent());
					sb.append("\nNotes: " + dds.getNotes());
				}

				mds = new DBHelper(this).getMaintenanceDao().queryForId(date);
				if (mds != null) {
					sb.append("\n\nMAINTENANCE DATA\nPruned?: "
							+ mds.getPruned());
					sb.append("\nShock Treated?: " + mds.getShockTreated());
					sb.append("\nNotes: " + mds.getNotes());
				}

				listItems.add(date);
			}

			return listItems;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE: {
			if (resultCode != RESULT_CANCELED) {
				IntentResult scanResult = IntentIntegrator.parseActivityResult(
						requestCode, resultCode, data);
				if (scanResult != null) {
					DBHelper dbh = new DBHelper(this);
					try {
						Dao<IDTag, String> idTagDao = dbh.getIDTagDao();
						tagScanned = idTagDao.queryForId(scanResult
								.getContents());
						setup();
					} catch (SQLException sqlex) {
						Toast.makeText(this, "SQL error occurred!",
								Toast.LENGTH_LONG);
						// Log.e(LOG_TAG, "SQLException was thrown here.");
					}
				}
			}
			break;
		}
		}
	}
}
