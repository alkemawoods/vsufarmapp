package edu.vsu.trojansmartfarm;

import com.j256.ormlite.field.DatabaseField;

public class IDTag {
	@DatabaseField (id = true)
	private String upcCode;
	@DatabaseField 
	private boolean isControl;
	@DatabaseField
	private String plantVariety;
	
	// empty default constructor required by ORMLite
	public IDTag() {}
	
	public IDTag(String upcCode, boolean isControl, String plantVariety) {
		this.upcCode = upcCode;
		this.isControl = isControl;
		this.plantVariety = plantVariety;
	}
	
	public String getUpcCode() { return upcCode; }
	public boolean getIsControl() { return isControl; }
	public String getPlantVariety() { return plantVariety; }
}
