package com.rakesh.appcrasheradv;







import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.intel.sdp.DataEncryption.R;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

	private final int REQUEST_CODE_PICK_DIR = 1;
	private final int REQUEST_CODE_PICK_FILE = 2;
	EditText edt1;
	Button b;
	String s ;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle ssss) {
		super.onCreate(ssss);
		setContentView(R.layout.login);
		
		final Activity activityForButton = this;
		
		edt1 =(EditText)findViewById(R.id.editText1);
		b = (Button)findViewById(R.id.button1);
		
	sp = getSharedPreferences("my_pref", MODE_PRIVATE);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String s2 = edt1.getText().toString();


				Databases data=new Databases(getApplicationContext());
				data.open(); 
				 Cursor c2 = data.getPassword();
			     if (c2.moveToFirst()) 
			     {
			        s=c2.getString(0);
			         c2.close();
			     }
			     else
			         Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
			     data.close();
				
				
				
				if(s.equals(s2)){
				
					SharedPreferences.Editor editer = sp.edit();
					editer.putString("Stringpref", s2);
					editer.commit();
					
					/* Intent fileExploreIntent = new Intent(
							    FileBrowserActivity.INTENT_ACTION_SELECT_DIR,
							    null,
							        activityForButton,
							        FileBrowserActivity.class
							    );
							//  fileExploreIntent.putExtra(
//							      ua.com.vassiliev.androidfilebrowser.FileBrowserActivity.startDirectoryParameter, 
//							      "/sdcard"
							//  );//Here you can add optional start directory parameter, and file browser will start from that directory.
							    startActivityForResult(
							        fileExploreIntent,
							        REQUEST_CODE_PICK_DIR
							    );*/
					
					Intent i=new Intent(getApplicationContext(), DataEncryptionActivity.class);
					startActivity(i);


				}else{
					Toast.makeText(getApplicationContext(), "Password Not matched", Toast.LENGTH_SHORT).show();
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
