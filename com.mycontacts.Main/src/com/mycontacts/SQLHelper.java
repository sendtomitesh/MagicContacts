package com.mycontacts;

import java.sql.Date;
import java.util.Calendar;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper{
	static final String dbName="dbContacts";
	static final String TableMyContacts="MyContacts";
	static final String col_ID="id";//id int autoincrement,c_no , c_name, status
	static final String col_c_no="c_no";
	static final String col_c_name="c_name";
	static final String col_status="status";
	
	static final String TableHistory="ScanHistory";
	static final String col_Hid="_id";//id int autoincrement,c_no , c_name, status
	static final String col_lsd="lastScanedDay";
	static final String col_lsm="lastScanedMonth";
	static final String col_lsy="lastScanedYear";
	public SQLHelper(Context ctx)
	{
		super(ctx,dbName,null,1);
		this.getReadableDatabase();
	}
	
			@Override
		public void onCreate(SQLiteDatabase db) {			
		try{
			
            
			String str1="CREATE TABLE IF NOT EXISTS "+TableMyContacts+
					"("+col_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+col_c_name+" VARCHAR,"
					+col_c_no+" VARCHAR,"
					+col_status+" INTEGER);";
			
			String str2="CREATE TABLE IF NOT EXISTS "+TableHistory+
					"("+col_Hid+" INTEGER,"
					+col_lsd+" INTEGER,"
					+col_lsm+" INTEGER, "
					+col_lsy+" INTEGER);";
			
			
			
			String str[]=new String[]{str1,str2};
			for(String sql : str)
			{
				db.execSQL(sql);
			}

			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE if Exists "+TableMyContacts);
		//	db.execSQL("DROP TABLE if Exists "+TableHistory);
			onCreate(db);
		}

	public long insertNumber(String c_name,String c_no,int Status)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues initialvalues= new ContentValues();				
		initialvalues.put(col_c_name,c_name);
		initialvalues.put(col_c_no,c_no);
		initialvalues.put(col_status,Status);
		return db.insert(TableMyContacts, null, initialvalues);
	}
	
	public long insertHistory()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Calendar calendar=Calendar.getInstance();
		ContentValues initialvalues= new ContentValues();				
		initialvalues.put(col_Hid,1);
		
		initialvalues.put(col_lsd,calendar.get(Calendar.DAY_OF_MONTH));
		initialvalues.put(col_lsm,calendar.get(Calendar.MONTH));
		initialvalues.put(col_lsy,calendar.get(Calendar.YEAR));
		
		return db.insert(TableHistory, null, initialvalues);		
	}
	
	public boolean activeContact(int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues initialvalues=new ContentValues();
		initialvalues.put(col_status,1);
		return db.update(TableMyContacts,initialvalues,col_ID+"="+id,null)>0;
	}
	public boolean disableContact(int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues initialvalues=new ContentValues();
		initialvalues.put(col_status,0);
		return db.update(TableMyContacts,initialvalues,col_ID+"="+id,null)>0;
	}
	public boolean updateHistory()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Calendar calendar=Calendar.getInstance();
		ContentValues initialvalues= new ContentValues();				
		initialvalues.put(col_lsd,calendar.get(Calendar.DAY_OF_MONTH));
		initialvalues.put(col_lsm,calendar.get(Calendar.MONTH));
		initialvalues.put(col_lsy,calendar.get(Calendar.YEAR));	
				
		return db.update(TableHistory,initialvalues,col_Hid+"="+1,null)>0;
	}
	public boolean deleteHistory(long id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		return db.delete(TableHistory, col_Hid, null)>0;
	}
	public Cursor getAllContacts()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		//return db.rawQuery("select "+col_ID+" _id,"+col_c_name+","+col_c_no+" from " +TableMyContacts,null);
		return db.rawQuery("select id _id, c_name, c_no from MyContacts", null);
	}
	public Cursor getAllActiveContacts()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		//return db.rawQuery("select "+col_ID+" _id,"+col_c_name+","+col_c_no+" from " +TableMyContacts,null);
		return db.rawQuery("select id _id, c_name, c_no from MyContacts where status=1", null);
	}
	public boolean isDataExist()
	{
		
		if (getAllContacts().getCount()>0)return true;
		else return false;
		
		
	}
	public Cursor getRandomContact(int Status)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		return db.rawQuery("select "+col_ID+" _id,"+col_c_name+","+col_c_no+","+col_status+" from " +TableMyContacts +"WHERE "+col_ID+"="+"(SELECT MAX("+col_ID+") FROM "+TableMyContacts+")",null);
	}
	public Cursor getHistoryDate()
	{
		Cursor c;
		try
		{
		SQLiteDatabase db=this.getWritableDatabase();
		c= db.rawQuery("select * from " +TableHistory,null);
		}
		catch(Exception e)
		{
			Log.e("error","error in history");
			c= null;
		}
		return c;
	}
	
}
