package com.mycontacts;

import java.util.ArrayList;
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
import android.database.Cursor;
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


    ArrayAdapter<String> myAdapterInstance;
    SimpleCursorAdapter adapter;
    String[] projection = new String[] {
            Phone._ID,
            Phone.DISPLAY_NAME,
            Phone.NUMBER
    };
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaycontacts);
        
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
        for(int i=0;i<rndcnt;i++)
        {
        	Random r1=new Random();
        	int index=r1.nextInt(size);
        	c.moveToPosition(index);
        	Log.e("hi",index + "in for loop");
        	id[i]=c.getInt(0);
        	name[i]=c.getString(1); //8 for name,12 for name
        	number[i]=c.getString(2);
        	Log.e("hi", id+" "+name[i]+" "+number[i]);
        	//insert this records into database;
        }
        
        
        
        final Cursor cursor = managedQuery(Phone.CONTENT_URI, projection, null, null,Phone.DISPLAY_NAME + " ASC");
        
    	final ListView listView1= (ListView)findViewById(R.id.listView1);
    	
    	adapter = new SimpleCursorAdapter(
                this,	//Context
                R.layout.contactslist,	//xml definintion of each listView item
                cursor,		//Cursor
                new String[] {"display_name",Phone.NUMBER,"_id"},  //Columns to select From
                new int[] {R.id.textView11,R.id.textView12,R.id.checkBox1}   //Object to bind to
                );
    	listView1.setAdapter(adapter);        
	}
       

}
