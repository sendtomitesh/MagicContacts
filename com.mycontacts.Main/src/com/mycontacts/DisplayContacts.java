package com.mycontacts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

public class DisplayContacts extends Activity {
	private EditText filterText = null;
	public ArrayList<Boolean> itemChecked = null;
	private final Context appContext = this;

	SQLHelper dbHelp;

	ArrayAdapter<String> myAdapterInstance;
	SimpleCursorAdapter adapter;
	String[] projection = new String[] { Phone._ID, Phone.DISPLAY_NAME,
			Phone.NUMBER };

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);

		dbHelp = new SQLHelper(appContext);
		Cursor c=null;
		if (!dbHelp.isDataExist()) {

			// Find the random number between 1 to 5
			Random r = new Random();
			int rndcnt = r.nextInt(5) + 1;
			c = getContentResolver().query(Phone.CONTENT_URI,
					projection, null, null, Phone.DISPLAY_NAME + " ASC");
			int size = c.getCount();
			for (int i = 0; i < rndcnt; i++) {
				Random r1 = new Random();
				int index = r1.nextInt(size);
				c.moveToPosition(index);
				dbHelp.insertNumber(c.getString(1), c.getString(2), 1);
				// Toast.makeText(appContext, c.getString(1),
				// Toast.LENGTH_LONG).show();
			}
			dbHelp.insertHistory();
			c.close();
		} 
		/*final ListView listView1 = (ListView) findViewById(R.id.listView1);
		adapter = new SimpleCursorAdapter(this, R.layout.contact_list_item,
					dbHelp.getAllActiveContacts(), new String[] { "_id", "c_name",
							"c_no" }, new int[] { R.id.checkBox1,
							R.id.txt_name, R.id.txt_number });
		listView1.setAdapter(adapter);*/
		
		Cursor temp=dbHelp.getHistoryDate();
		if (temp!=null)
		{
			
		if (temp.getCount()>0)
			{
			temp.moveToFirst();
			try
			{
				
				Calendar thatDay = Calendar.getInstance();
				thatDay.set(Calendar.DAY_OF_MONTH,temp.getInt(1));//change this value to 1 for getting date difference 10.
				thatDay.set(Calendar.MONTH,temp.getInt(2)); // 0-11 so 1 less
				thatDay.set(Calendar.YEAR, temp.getInt(3));
				 	 Calendar today = Calendar.getInstance();
				 	 long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); 
				 	 long days = diff / (24 * 60 * 60 * 1000);
			 	  
				 	 if(days >= 10)
				 	 {
				 	 Log.d("Days Diffe",""+ days);
				 	 //make inactive all the contacts
				 	 
				 	 //choose last one contact to display
					 //int id;
			 		 //String contactno;
			 		 //String contactname;
					 	 Log.d("Else Days Diffe",""+ days);
					 	 Cursor updateCursor=dbHelp.getAllContacts();
					 	 updateCursor.moveToFirst();
					 	 
					 	 do
						 {
						 	 //id=;
						 	 //contactname=updateCursor.getString(1);
						 	 //contactno=updateCursor.getString(2);
						 	 dbHelp.disableContact(updateCursor.getInt(0));
						 }while(updateCursor.moveToNext());
						 updateCursor.moveToLast();
						 dbHelp.activeContact(updateCursor.getInt(0));
						 
						 //fetch one random contact from the phone and insert into db.
						 
						 c = getContentResolver().query(Phone.CONTENT_URI,
									projection, null, null, Phone.DISPLAY_NAME + " ASC");
							int size = c.getCount();
							boolean numFound=false;
							while(!numFound)
							{
								Random r1 = new Random();
								int index = r1.nextInt(size);
								c.moveToPosition(index);
								//check into database
								updateCursor.moveToFirst();
								do
								{
									if(updateCursor.getString(2)!=c.getString(2))
									{
										dbHelp.insertNumber(c.getString(1), c.getString(2), 1);
										numFound=true;
										break;
									}									
								}while(updateCursor.moveToNext());							
							}
							dbHelp.updateHistory();
							c.close(); 
					 }
			}
			catch(Exception e)
			{
				Log.e("error",""+"Danger Error");
			}
			final ListView listView1 = (ListView) findViewById(R.id.listView1);
			adapter = new SimpleCursorAdapter(this, R.layout.contact_list_item,
						dbHelp.getAllActiveContacts(), new String[] { "_id", "c_name",
								"c_no" }, new int[] { R.id.checkBox1,
								R.id.txt_name, R.id.txt_number });
			listView1.setAdapter(adapter);
			}

		}
	}

	// Log.e("hi","c1 close");
}
	//


