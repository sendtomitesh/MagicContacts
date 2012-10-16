package com.mycontacts;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splashscreen);
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setMax(50);
		pb.setProgress(0);
		Thread splashThread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
						int waited = 0;
						while (waited < 5000) 
						{
							//Five second timer
							sleep(100);
							waited += 100;
							pb.setProgress(waited/100);
						}
				}catch(InterruptedException e)
				{ //Any errors that might occur
					// do nothing
				}
				finally
				{
					finish();
					Intent myIntent= new Intent(getApplicationContext(),DisplayContacts.class);
			    	startActivity(myIntent);
					
				}
			}
		};
		splashThread.start();
	}
}
