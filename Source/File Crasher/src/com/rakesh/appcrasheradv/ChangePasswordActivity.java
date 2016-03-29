package com.rakesh.appcrasheradv;

import android.intel.sdp.DataEncryption.R;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePasswordActivity extends Activity {

	
	Button ok1;
	EditText passf;
	
	String x;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		ok1=(Button) findViewById(R.id.kkbt);
		passf=(EditText) findViewById(R.id.passfield);
		
		
		//Log.e("value value", x);
		ok1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					
					 x=passf.getText().toString();
				Databases data=new Databases(getApplicationContext());
				data.open(); 
				Log.e("value value", x);
				data.changePassword(x);
				data.close();
				Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(),"Enter Password", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
