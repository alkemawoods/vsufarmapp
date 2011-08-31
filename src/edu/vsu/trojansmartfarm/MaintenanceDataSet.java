package edu.vsu.trojansmartfarm;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class MaintenanceDataSet extends DataSet {
	@DatabaseField
	private boolean shockTreated;
	@DatabaseField
	private boolean pruned;
	
	// empty constructor required by ORMLite
	MaintenanceDataSet() {}
	
	MaintenanceDataSet(IDTag tag, Date timestamp, byte[] photo, String notes,
			boolean shockTreated, boolean pruned) {
		super(tag, timestamp, photo, notes);
		this.pruned = pruned;
	}
	
	boolean getShockTreated() { return shockTreated; }
	boolean getPruned() { return pruned; }
}