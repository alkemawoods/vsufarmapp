package edu.vsu.trojansmartfarm;

import java.util.Date;
import com.j256.ormlite.field.DatabaseField;

public class GrowthDataSet extends DataSet {
	@DatabaseField
	private int numOfLeaves;
	@DatabaseField
	private int numOfBerries;
	@DatabaseField
	private int heightOfPlant;
	
	// empty constructor required by ORMLite
	GrowthDataSet() {}
	
	GrowthDataSet(IDTag tag, Date timestamp, byte[] photo, String notes, 
			int numOfLeaves, int numOfBerries, int heightOfPlant) {
		super(tag, timestamp, photo, notes);
		this.numOfLeaves = numOfLeaves;
		this.numOfBerries = numOfBerries;
		this.heightOfPlant = heightOfPlant;
	}
	
	int getNumOfLeaves() { return numOfLeaves; }
	int getNumOfBerries() { return numOfBerries; }
	int getHeightOfPlant() { return heightOfPlant; }
}
