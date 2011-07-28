package edu.vsu.trojansmartfarm;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class DiseaseDataSet extends DataSet {
	@DatabaseField
	private boolean diseasePresent;
	
	DiseaseDataSet() {}
	
	DiseaseDataSet(IDTag tag, Date timestamp, byte[] photo, String notes, 
			boolean diseasePresent) {
		super(tag, timestamp, photo, notes);
		this.diseasePresent = diseasePresent;
	}
	
	boolean getDiseasePresent() { return diseasePresent; }
}