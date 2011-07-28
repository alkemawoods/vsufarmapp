package edu.vsu.trojansmartfarm;

import java.util.Date;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;


public class DataSet {
	
	@DatabaseField (canBeNull = false, foreign = true)
	private IDTag tag;
	@DatabaseField (id = true)
	private Date timestamp;
	@DatabaseField (dataType=DataType.BYTE_ARRAY)
	private byte[] photo;
	@DatabaseField
	private String notes;
	
	// empty default constructor required by ORMLite
	public DataSet() {}

	public DataSet(IDTag tag, Date timestamp, byte[] photo, String notes) {
		this.tag = tag;
		this.timestamp = timestamp;
		this.photo = photo;
		this.notes = notes;
	}
	
	public IDTag getIDTag() { return tag; }
	public Date getTimestamp() { return timestamp; }
	public byte[] getPhoto() { return photo; }
	public String getNotes() { return notes; }
}
