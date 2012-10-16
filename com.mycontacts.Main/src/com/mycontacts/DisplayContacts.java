package com.mycontacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
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
		final ListView listView1 = (ListView) findViewById(R.id.listView1);
		adapter = new SimpleCursorAdapter(this, R.layout.contact_list_item,
					dbHelp.getAllContacts(), new String[] { "_id", "c_name",
							"c_no" }, new int[] { R.id.checkBox1,
							R.id.txt_name, R.id.txt_number });
		listView1.setAdapter(adapter);
		Cursor temp=dbHelp.getHistoryDate();
		if (temp!=null)
		{
			
		if (temp.getCount()>0)
			{
			temp.moveToFirst();
			try
			{
			Log.e("error",""+temp.getInt(2));
			}
			catch(Exception e)
			{
				Log.e("error",""+"Danger Error");
			}
			}
		}
	}

	// Log.e("hi","c1 close");
	// }
	//

}
