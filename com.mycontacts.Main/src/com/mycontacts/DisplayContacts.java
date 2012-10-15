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
    
    final boolean tempvar=false;
    int k=2;
   String d=null;
    ArrayAdapter<String> myAdapterInstance;
    SimpleCursorAdapter adapter;
    String[] projection = new String[] {
            Phone._ID,
            Phone.DISPLAY_NAME,
            Phone.NUMBER
    };
	
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        try{
        	String destpath = "/data/data/" + getPackageName()
					+ "/databases/dbContacts";
			File f = new File(destpath);
			if (!f.exists()) {
				copyDB(getBaseContext().getAssets().open("dbContacts"),
						new FileOutputStream(destpath));
			}
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SQLHelper con= new SQLHelper(this);
		
        //Find the random  number between 1 to 5
        Random r= new Random();
        int rndcnt=r.nextInt(5)+1;
        Log.e("hi", rndcnt+"");
        People people;
        
        //Find rndcnt number of random contact from the list
        Cursor c = getContentResolver().query(Phone.CONTENT_URI,projection, null, null,Phone.DISPLAY_NAME + " ASC");
        Log.e("hi","after cursor");
        int size=c.getCount();
        int id[]=new int[size];
        String name[]=new String[size];
        String number[]=new String[size];
        Log.e("hi","before for loop");
        //Here find out if application run for first time or not.
        //If not first time then find the last scanned date of application.
        
        
        /*SharedPreferences appSettings=getSharedPreferences("RunAppPreferences", MODE_PRIVATE);
        String d=appSettings.getString("Counter",null);
        SharedPreferences.Editor editor=appSettings.edit();
        
        String d1=d1=appSettings.getString("runDate", null);
        if(d1==null)
        {
        //d=DateFormat.getDateFormat(getApplicationContext()).toString();
        //Toast.makeText(this, d+"hi",Toast.LENGTH_LONG);
         */ 
        //if(tempvar==false)
        if(k==0)
        {
        	for(int i=0;i<rndcnt;i++)
        	{
	        	Random r1=new Random();
	        	int index=r1.nextInt(size);
	        	c.moveToPosition(index);
	        	Log.e("hi",index + "in for loop");
	        	id[i]=c.getInt(0);
	        	name[i]=c.getString(1);
	        	number[i]=c.getString(2);
	        	Log.e("hi", id[i]+" "+name[i]+" "+number[i]);
	        	//insert this records into database;
	        	Log.e("hi", "after log e");
	        	con.insertNumber(name[i],number[i], 1);	        	
        	} 
        	c.close();
        	//editor.putString("rundate",d);        	
        	//editor.commit();
        }
        else if(k==1)
        {
        	//if App run after 10 days
        	
        	for(int i=0;i<rndcnt;i++)
        	{
	        	Random r1=new Random();
	        	int index=r1.nextInt(size);
	        	c.moveToPosition(index);
	        	Log.e("hi",index + "in for loop");
	        	id[i]=c.getInt(0);
	        	name[i]=c.getString(1);
	        	number[i]=c.getString(2);
	        	Log.e("hi", id+" "+name[i]+" "+number[i]);
	        	//insert this records into database;
	        	Log.e("hi", "after log e");
	        	con.insertNumber(name[i],number[i], 1);	        	
        	}
        }
        else if(k==2)
        {
        	//if app run before 10 days then just display the active contacts(status=1)
        	Log.e("hi",k+" in k");
        }
        	
        Cursor c1=con.getAllContacts();
        Log.e("hi",c1.getCount()+"");
        
        final ListView listView1= (ListView)findViewById(R.id.listView1);
        adapter = new SimpleCursorAdapter(this,
        		R.layout.contact_list_item,
        		c1,
        		new String[] {"_id","c_name","c_no"},
        		new int[] {R.id.checkBox1,R.id.txt_name,R.id.txt_number}
        );        
        listView1.setAdapter(adapter);
        c1.close();
        Log.e("hi","c1 close");
	}
    	
        private void copyDB(InputStream inputStream, OutputStream outputStream)
    			throws IOException {
    		byte[] buffer = new byte[1024];
    		int length;
    		while ((length = inputStream.read(buffer)) > 0) {
    			outputStream.write(buffer, 0, length);
    		}
    		inputStream.close();
    		outputStream.close();
    	}
 }
