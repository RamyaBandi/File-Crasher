package com.rakesh.appcrasheradv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;
public class Databases {
	
	
	public static final String key_password = "passwordvalue";
	
    
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "password";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table password (passwordvalue text not null);";
    
   
   
    
    
    
    
  
       
    
   
    
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    
     
    

    public Databases(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {

        	try {
        		db.execSQL(DATABASE_CREATE);
        		ContentValues initialValues = new ContentValues();
        		initialValues.put(key_password,"password");
        		db.insert(DATABASE_TABLE,null,initialValues);
        		
        		Log.e("dtata table 1 created","Creararted");
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	
        	
        	
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            //Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                 //   + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS details");
            onCreate(db);
        }
}
    //---opens the database---
    public Databases open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }


	



	public void changePassword(String x) {
		// TODO Auto-generated method stub
		
		ContentValues inValues=new ContentValues();
		inValues.put(key_password,x);
		db.update(DATABASE_TABLE, inValues,null, null);
		
	}

	public Cursor getPassword() {
		// TODO Auto-generated method stub
		Cursor c=db.rawQuery("Select * from password",null);
		
		return c;
	}


   
}    