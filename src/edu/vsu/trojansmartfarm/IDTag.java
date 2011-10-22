package edu.vsu.trojansmartfarm;

import com.j256.ormlite.field.DatabaseField;

public class IDTag {
	@DatabaseField (id = true)
	private String UID;
	@DatabaseField 
	private boolean isControl;
	@DatabaseField
	private String plantVariety;
	@DatabaseField
	private double latitude;
	@DatabaseField
	private double longitude;
	
	
	// empty default constructor required by ORMLite
	public IDTag() {}
	
	public IDTag(String UID, boolean isControl, String plantVariety) {
		this.UID = UID;
		this.isControl = isControl;
		this.plantVariety = plantVariety;
	}
	
	public IDTag(String UID, boolean isControl, String plantVariety, double latitude, double longitude) {
		this.UID = UID;
		this.isControl = isControl;
		this.plantVariety = plantVariety;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getUID() { return UID; }
	public boolean getIsControl() { return isControl; }
	public String getPlantVariety() { return plantVariety; }
	public double getLatitude() { return latitude; }
	public double getLongitude() { return longitude; }
	@Override
	public String toString() {
		return getUID() + "\\" + getPlantVariety();
	}
}
