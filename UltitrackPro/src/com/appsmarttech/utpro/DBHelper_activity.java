package com.appsmarttech.utpro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_activity extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.appsmarttech.utpro/databases/";
 
    private static String DB_NAME = "UTPRO";
 
    private SQLiteDatabase myDataBase, db; 
 
    private final Context myContext;
    
    //table names
    private static final String TABLE_PROGRAMS = "ProgramKey";
    
    //program table field names
    private static final String KEY_ID = "_id";
    private static final String KEY_PROG_NAME = "programName";
    private static final String KEY_EDITABLE = "isEditable";
    private static final String KEY_TIMES_COMPLETED = "timesCompleted";
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DBHelper_activity(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
    
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * All CRUD(Create, Read, Update, Delete) Operations for Programs
     */
 
    // Adding new program - not necessary for UT90 and UT92
	// inserts a new program into the programkey table
    void addProgram(Program Program) {
        db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PROG_NAME, Program.getName()); // Program Name
        values.put(KEY_TIMES_COMPLETED, Program.getTimesCompleted()); // Program Times Completed
        values.put(KEY_EDITABLE, Program.getEditable()); // Program is editable
        
        
        // Inserting Row
        db.insert(TABLE_PROGRAMS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting a single program
    Program getProgram(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PROGRAMS, new String[] { KEY_ID,
                KEY_PROG_NAME, KEY_EDITABLE, KEY_TIMES_COMPLETED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Program Program = new Program(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Boolean.valueOf(cursor.getString(2)), cursor.getInt(3));
        // return contact
        return Program;
    }
 
    // Getting All Contacts
    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<Program>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAMS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Program Program = new Program();
                Program.setID(Integer.parseInt(cursor.getString(0)));
                Program.setName(cursor.getString(1));
                Program.setEditable(Boolean.valueOf(cursor.getString(2)));
                Program.setTimesCompleted(cursor.getInt(3));
                // Adding program to list
                programList.add(Program);
            } while (cursor.moveToNext());
        }
 
        // return program list
        return programList;
    }
 
    // Updating single program
    public int updateProgram(Program Program) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_PROG_NAME, Program.getName());
        values.put(KEY_EDITABLE, Program.getEditable());
 
        // updating row
        return db.update(TABLE_PROGRAMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(Program.getID()) });
    }
 
    // Deleting single program
    public void deleteContact(Program Program) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROGRAMS, KEY_ID + " = ?",
                new String[] { String.valueOf(Program.getID()) });
        db.close();
    }
 
    // Getting programs Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROGRAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 

 
}
