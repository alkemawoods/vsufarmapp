package edu.vsu.trojansmartfarm;

import java.sql.SQLException;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "trojanSmartFarm.db";
	private static final int DATABASE_VERSION = 1;
	private final String LOG_TAG = getClass().getName();


	Dao<IDTag, String> idTagDao = null;
	Dao<DiseaseDataSet, Date> diseaseDao = null;
	Dao<GrowthDataSet, Date> growthDao = null;
	Dao<InsectDataSet, Date> insectDao = null;
	Dao<MaintenanceDataSet, Date> maintenanceDao = null;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DBHelper.class.getName(), "onCreate");
			TableUtils.createTableIfNotExists(connectionSource, IDTag.class);
			TableUtils.createTableIfNotExists(connectionSource, GrowthDataSet.class);
			TableUtils.createTableIfNotExists(connectionSource, InsectDataSet.class);
			TableUtils.createTableIfNotExists(connectionSource, DiseaseDataSet.class);
			TableUtils.createTableIfNotExists(connectionSource, MaintenanceDataSet.class);
			Log.i(DBHelper.class.getName(), "DBHelper created.");
		}
		catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} 
	}
	
	public Dao<IDTag, String> getIDTagDao() {
		if(idTagDao == null) 
		try {
			idTagDao = getDao(IDTag.class);
		}
		catch(SQLException e) {
			Log.e(LOG_TAG, "SQL error on getIDTagDao().", e);
			throw new RuntimeException(e);
		}
		return idTagDao;
	}
	
	public Dao<InsectDataSet, Date> getInsectDao() {
		if(insectDao == null) 
		try {
			insectDao = getDao(InsectDataSet.class);
		}
		catch(SQLException e) {
			Log.e(LOG_TAG, "SQL error on getInsectDao().", e);
			throw new RuntimeException(e);
		}
		return insectDao;
	}
	
	public Dao<DiseaseDataSet, Date> getDiseaseDao() {
		if(diseaseDao == null) 
		try {
			diseaseDao = getDao(DiseaseDataSet.class);
		}
		catch(SQLException e) {
			Log.e(LOG_TAG, "SQL error on getDiseaseDao().", e);
			throw new RuntimeException(e);
		}
		return diseaseDao;
	}
	
	public Dao<GrowthDataSet, Date> getGrowthDao() {
		if(growthDao == null) 
			try {
				growthDao = getDao(GrowthDataSet.class);
			}
			catch(SQLException e) {
				Log.e(LOG_TAG, "SQL error on getGrowthDao().", e);
				throw new RuntimeException(e);
			}
		return growthDao;
	}
	
	public Dao<MaintenanceDataSet, Date> getMaintenanceDao() {
		if(maintenanceDao == null) 
			try {
				maintenanceDao = getDao(MaintenanceDataSet.class);
			}
			catch(SQLException e) {
				Log.e(LOG_TAG, "SQL error on getMaintenanceDao().", e);
				throw new RuntimeException(e);
			}
		return maintenanceDao;
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, 
			int newVersion) {
		try {
			Log.i(DBHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, IDTag.class, true);
			TableUtils.dropTable(connectionSource, GrowthDataSet.class, true);
			TableUtils.dropTable(connectionSource, InsectDataSet.class, true);
			TableUtils.dropTable(connectionSource, DiseaseDataSet.class, true);
			TableUtils.dropTable(connectionSource, MaintenanceDataSet.class, true);
			
			// after we drop the old database, we create the new one
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		idTagDao = null;
		growthDao = null;
		insectDao = null;
		diseaseDao = null;
		maintenanceDao = null;
	}

}
