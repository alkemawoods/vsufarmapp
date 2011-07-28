package edu.vsu.trojansmartfarm;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class InsectDataSet extends DataSet {
	@DatabaseField
	private boolean insectsPresent;
	
	InsectDataSet() {}
	
	InsectDataSet(IDTag tag, Date timestamp, byte[] photo, String notes,
			boolean insectsPresent) {
		super(tag, timestamp, photo, notes);
		this.insectsPresent = insectsPresent;
	}
	
	boolean getInsectsPresent() { return insectsPresent; }
}