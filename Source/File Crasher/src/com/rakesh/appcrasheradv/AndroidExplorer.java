package com.rakesh.appcrasheradv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.intel.sdp.DataEncryption.R;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidExplorer extends ListActivity {

	ProgressDialog progDialog;
	private long mTimeTook = 0;
	private DataEncryption mEncrypter;
	private Boolean mDoneEncryption = false;
	private List<String> item = null;
	private List<String> path = null;
	private String root = "/sdcard";
	private TextView myPath;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explorer);
		myPath = (TextView) findViewById(R.id.path);
		getDir(root);
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
	}

	private void getDir(String dirPath) {
		myPath.setText("Location: " + dirPath);

		item = new ArrayList<String>();
		path = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (!dirPath.equals(root)) {

			item.add(root);
			path.add(root);

			item.add("../");
			path.add(f.getParent());

		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			path.add(file.getPath());
			if (file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				R.layout.explorer_row, item);
		setListAdapter(fileList);
	}

	File file;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		file = new File(path.get(position));

		if (file.isDirectory()) {
			if (file.canRead())
				getDir(path.get(position));
			else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle(
								"[" + file.getName()
										+ "] folder can't be read!")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		} else {
			new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("Select")
					.setMessage("Select " + file.getName() + "to Encrypt or Decrypt ?")
					.setPositiveButton("Encrypt",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								//	Toast.makeText(
										//	AndroidExplorer.this,
										//	"" + file.getAbsolutePath()
										//			+ " iss selected ", 300)
										//	.show();
									dialog.dismiss();
									progDialog = new ProgressDialog(getApplicationContext());
									progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
									progDialog.setMessage("encrypting media file...");
									
									
									String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
									String filename = file.getAbsolutePath()+"";
									//Toast.makeText(getApplicationContext(), filePath, 1).show();
									int pos = filename.lastIndexOf('.');
									//String filenameDec = filename.substring(0,pos) + "-dec." + filename.substring(pos+1);
									String filenameEnc = filename.substring(0)+".jpeg";
									
									SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
									String implementationPref = sharedPref.getString("implementation", "");
									
									if (implementationPref.equals(Preferences.IMPLEMENTATION_CRYPTO)) {
										mEncrypter = new DataEncryptionCrypto();
									} 
									
									// set blocksize
									String blocksizePref = sharedPref.getString("blocksize", "");
									int blocksize = 128;
									if (blocksizePref.equals( Preferences.BLOCKSIZE_16 )) {
										blocksize = 16;
									} else if  (blocksizePref.equals(Preferences.BLOCKSIZE_64 )) {
										blocksize = 64;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_256 )) {
										blocksize = 256;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_1024 )) {
										blocksize = 1024;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_8192 )) {
										blocksize = 8192;
									} else {
										throw new IllegalArgumentException("unexpected selection");
									}
									
									mEncrypter.setBlocksize(blocksize);
									
									// encrypt and decrypt the file. time how long it takes
									long seconds = mEncrypter.encryptFile(filenameEnc, filename);
								//	seconds += mEncrypter.decryptFile(filenameDec, filenameEnc);
									mTimeTook = seconds;
									mDoneEncryption = true;
									progDialog.dismiss();
									Intent i=getIntent();
									finish();
									startActivity(i);
									
								}
							})
					.setNegativeButton("Decrypt",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									progDialog = new ProgressDialog(getApplicationContext());
									progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
									progDialog.setMessage("decrypting media file...");
									//progDialog.show();
									
									String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
									String filename = file.getAbsolutePath()+"";
									//Toast.makeText(getApplicationContext(), filePath, 1).show();
									int pos = filename.lastIndexOf('.');
									String filenameDec = filename.substring(0,pos) +"";
									String filenameEnc = filename.substring(0);
									
									SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
									String implementationPref = sharedPref.getString("implementation", "");
									
									if (implementationPref.equals(Preferences.IMPLEMENTATION_CRYPTO)) {
										mEncrypter = new DataEncryptionCrypto();
									} 
									
									// set blocksize
									String blocksizePref = sharedPref.getString("blocksize", "");
									int blocksize = 128;
									if (blocksizePref.equals( Preferences.BLOCKSIZE_16 )) {
										blocksize = 16;
									} else if  (blocksizePref.equals(Preferences.BLOCKSIZE_64 )) {
										blocksize = 64;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_256 )) {
										blocksize = 256;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_1024 )) {
										blocksize = 1024;
									} else if (blocksizePref.equals(Preferences.BLOCKSIZE_8192 )) {
										blocksize = 8192;
									} else {
										throw new IllegalArgumentException("unexpected selection");
									}
									
									mEncrypter.setBlocksize(blocksize);
									
									// encrypt and decrypt the file. time how long it takes
									//long seconds = mEncrypter.encryptFile(filenameEnc, filename);
								long seconds = mEncrypter.decryptFile(filenameDec, filenameEnc);
									mTimeTook = seconds;
									mDoneEncryption = true;
									
									progDialog.dismiss();
									
									
									Intent i=getIntent();
									finish();
									startActivity(i);
								}
							}).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								
									
									dialog.dismiss();
								}
							}).show();
		}
	}
}