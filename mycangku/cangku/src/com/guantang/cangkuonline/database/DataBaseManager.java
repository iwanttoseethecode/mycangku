package com.guantang.cangkuonline.database;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	private AtomicInteger databaseCount = new AtomicInteger(0);
	private DataBaseImport dataBaseImport;
	private SQLiteDatabase db;
	
	private static volatile DataBaseManager dataBaseManager;
	
	public static DataBaseManager getInstance(Context context){
		if(dataBaseManager == null){
			synchronized (DataBaseManager.class) {
				if(dataBaseManager == null){
					dataBaseManager = new DataBaseManager(context.getApplicationContext());
				}
			}
		}
		return dataBaseManager;
	}
	
	private DataBaseManager(Context context){
        	dataBaseImport = DataBaseImport.getInstance(context);
	}
	
	public synchronized SQLiteDatabase openDataBase(){
		if(databaseCount.incrementAndGet()==1){
			db = dataBaseImport.getReadableDatabase();
		}
		return db;
	}
	
	public synchronized void closeDataBase(){
		if(databaseCount.decrementAndGet() == 0){
			db.close();
		}
	}
	
}
