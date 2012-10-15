package com.mycontacts;

import java.sql.Date;

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
	static final String col_Hid="id";//id int autoincrement,c_no , c_name, status
	static final String col_lastScanned_Date="lastScanned_Date";
	
	public SQLHelper(Context ctx)
	{
		super(ctx,dbName,null,1);						
	}
	
			@Override
		public void onCreate(SQLiteDatabase db) {			
		try{			
			db.execSQL("CREATE TABLE IF NOT EXISTS "+TableMyContacts+
					"("+col_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+col_c_name+" VARCHAR,"
					+col_c_no+" VARCHAR,"
					+col_status+" INTEGER);");
			//db.execSQL("CREATE TABLE"+TableHistory+
			//		"("+col_Hid+" INTEGER,"
			//		+col_lastScanned_Date+" TIMESTAMP);");
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
	
	public long insertHistory(int id,Date date)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues initialvalues= new ContentValues();
		initialvalues.put(col_Hid,id);		
		initialvalues.put(col_lastScanned_Date,date.toString());
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
	public boolean updateHistory(int id,Date date)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues initialvalues=new ContentValues();
		initialvalues.put(col_lastScanned_Date,date.toString());		
		return db.update(TableMyContacts,initialvalues,col_Hid+"="+id,null)>0;
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
	public Cursor getRandomContact(int Status)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		return db.rawQuery("select "+col_ID+" _id,"+col_c_name+","+col_c_no+","+col_status+" from " +TableMyContacts +"WHERE "+col_ID+"="+"(SELECT MAX("+col_ID+") FROM "+TableMyContacts+")",null);
	}
	public Cursor getHistoryDate()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		return db.rawQuery("select "+col_Hid+" _id,"+col_lastScanned_Date+" from " +TableHistory,null);	
	}
	/*public boolean updateRecord(long id,String classname,String subject)
	{
		ContentValues initialvalues= new ContentValues();		
		initialvalues.put(KEY_CLASS,classname);
		initialvalues.put(KEY_SUBJECT,subject);
		return db.update(DATABASE_TABLE,initialvalues,KEY_ROWID+"="+id,null)>0;
	}	
	public boolean deleteRecord(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID+"="+rowId,null)>0;		
	}
	
	public Cursor getAllRecords()
	{
		return db.rawQuery("select id _id, classname,subject from classes",null);		
	}
	
	public Cursor getRecord(long id)throws SQLException
	{
		Cursor mCursor=db.query(true,DATABASE_TABLE, new String[] {KEY_ROWID,KEY_CLASS,KEY_SUBJECT}, 	KEY_ROWID+"="+id, null, null, null, null,null);
		if(mCursor!=null)
		{
			mCursor.moveToFirst();
		}		
		return mCursor;
	}*/
	
}
