package edu.vsu.trojansmartfarm;

import java.util.Date;
import com.j256.ormlite.field.DatabaseField;

public class GrowthDataSet extends DataSet {
	@DatabaseField
	private Integer numOfLeaves;
	@DatabaseField
	private Integer numOfBerries;
	@DatabaseField
	private Integer heightOfPlant;
	
	// empty constructor required by ORMLite
	GrowthDataSet() {}
	
	GrowthDataSet(IDTag tag, Date timestamp, byte[] photo, String notes, 
			int numOfLeaves, int numOfBerries, int heightOfPlant) {
		super(tag, timestamp, photo, notes);
		this.numOfLeaves = numOfLeaves;
		this.numOfBerries = numOfBerries;
		this.heightOfPlant = heightOfPlant;
	}
	
	Integer getNumOfLeaves() { return numOfLeaves; }
	Integer getNumOfBerries() { return numOfBerries; }
	Integer getHeightOfPlant() { return heightOfPlant; }
}
