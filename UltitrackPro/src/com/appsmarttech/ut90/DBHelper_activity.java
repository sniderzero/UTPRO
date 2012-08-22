package com.appsmarttech.ut90;

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
    private static final String TABLE_DAYORDER = "DayOrder";
    
    //program table field names
    private static final String KEY_ID = "_id";
    private static final String KEY_PROG_NAME = "programName";
    private static final String KEY_EDITABLE = "isEditable";
    private static final String KEY_TIMES_COMPLETED = "timesCompleted";
    private static final String KEY_PROG_ID = "programID";
    private static final String KEY_DAY_ID = "dayID";
    private static final String KEY_WEEK_NUM = "weekNumber";
    private static final String KEY_DAY_NUM = "dayNumber";
    private static final String KEY_DAY_COMPLETED = "dayCompleted";
    private static final String KEY_DAY_NAME = "dayName";
 
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
 
    // Getting All Programs
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
    public void deleteProgram(Program Program) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROGRAMS, KEY_ID + " = ?",
                new String[] { String.valueOf(Program.getID()) });
        db.close();
    }
 
    // Getting programs Count
    public int getProgramsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROGRAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
	/**
     * All CRUD(Create, Read, Update, Delete) Operations for Days
     */
 
 
    // Getting a single Day from the Day Order Table joined on the Day Key table
    Day getDay(int dayID) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.rawQuery("SELECT DayOrder._id, DayOrder.programID, DayOrder.dayID, DayOrder.dayCompleted, " +
        		"DayKey.dayName, DayOrder.dayNumber, DayOrder.weekNumber, DayKey.type FROM DayOrder JOIN DayKey ON " +
        		"DayOrder.dayID=DayKey._id WHERE dayID = " + dayID, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Day Day = new Day(Integer.parseInt(cursor.getString(0)), cursor.getString(4),cursor.getInt(7), cursor.getInt(3), 
        cursor.getInt(2), cursor.getInt(6), cursor.getInt(5));
        
        cursor.close();
        db.close();
        // return Day
        return Day;
    }
 
    // Getting All Days for a certain Program
    public List<Day> getAllProgramDays(int programID) {
        List<Day> DayList = new ArrayList<Day>();
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DayOrder._id, DayOrder.programID, DayOrder.dayID, DayOrder.dayCompleted, " +
        		"DayKey.dayName, DayOrder.dayNumber, DayOrder.weekNumber, DayKey.type FROM DayOrder JOIN DayKey ON " +
        		"DayOrder.dayID=DayKey._id WHERE programID = " + programID, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Day Day = new Day();
                Day.setID(Integer.parseInt(cursor.getString(0)));
                Day.setName(cursor.getString(4));
                Day.setType(cursor.getInt(7));
                Day.setCompleted(cursor.getInt(3));
                Day.setDayID(cursor.getInt(2));
                Day.setWeekNumber(cursor.getInt(6));
                Day.setDayNumber(cursor.getInt(5));
                // Adding Day to list
                DayList.add(Day);
            } while (cursor.moveToNext());
        }
 		
 		cursor.close();
 		db.close();
        // return Day list
        return DayList;
    }
 
    // Mark a Day Complete or skipped
    public void dayCompleteSkipped(int iStatus, int _id) { //iStatus is 1 for complete 2 for skipped, _id is the _id in the dayOrder table
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE dayOrder SET dayCompleted=" + iStatus + " WHERE _id=" + _id);
        
        db.close();
    }
 
    // Clear Program Progress
    public void progClearFlags(int programID) { 
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE dayOrder SET dayCompleted= 0  WHERE programID=" + programID);
        
        db.close();
    }
    
    public int[] getDaysCount(int iProgramID){
    	int[] aryCount;
    	aryCount = new int[2];
    	SQLiteDatabase db = this.getReadableDatabase();
    	//grabbing total count
    	Cursor count = db.rawQuery("SELECT COUNT(*) FROM DayOrder WHERE programID = " + iProgramID, null);
    	//moving to first record
    	count.moveToFirst();
    	//assigning total count to an int
    	int iTotal = count.getInt(0);
    	//closing cursor
    	count.close();
    	//grabbing completed count
    	count = db.rawQuery("SELECT COUNT(*) FROM DayOrder WHERE programID = " + iProgramID + " AND dayCompleted != 0", null);
    	//moving to first
    	count.moveToFirst();
    	//assigning completed to an int
    	int iCompleted = count.getInt(0);

    	aryCount[0] = iCompleted;
    	aryCount[1] = iTotal;

    	return aryCount;
    	}
    
 // Getting All Exercises for a certain day
    public List<Exercise> getAllDayExercises(int dayID) {
        List<Exercise> ExerciseList = new ArrayList<Exercise>();
 
        SQLiteDatabase db = this.getWritableDatabase();

	Cursor cursor = db.rawQuery("SELECT ExerOrder._id, ExerOrder.dayID, ExerOrder.exerOrder, ExerOrder.exerID, ExerOrder.exerCompleted, " +
	"ExerKey.exerName, ExerKey.exerType FROM ExerOrder JOIN ExerKey ON ExerKey.exerID = ExerOrder.exerID WHERE ExerOrder.dayID = " + dayID, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercise Exercise = new Exercise();
                Exercise.setID(Integer.parseInt(cursor.getString(0)));
                Exercise.setName(cursor.getString(5));
                Exercise.setType(cursor.getInt(6));
                Exercise.setExerCompleted(cursor.getInt(4));
                Exercise.setExerOrder(cursor.getInt(2));
                Exercise.setExerID(cursor.getInt(3));

                // Adding Day to list
                ExerciseList.add(Exercise);
            } while (cursor.moveToNext());
        }
 
  cursor.close();
  db.close();
        // return Exercise list
        return ExerciseList;
    }

    // Mark an Exercise Complete or skipped
    public void exerciseCompleteSkipped(int iStatus, int _id) { //iStatus is 1 for complete 2 for skipped, _id is the _id in the exerciseOrder table
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE ExerOrder SET exerCompleted=" + iStatus + " WHERE _id=" + _id);
        
        db.close();
    }

    // Clear Day Progress
    public void dayClearFlags(int dayID) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE ExerOrder SET exerCompleted= 0 WHERE exerCompleted != null && dayID=" + dayID);
        
        db.close();
    }
    
 // Getting All Stats for a certain exerciseID
    public List<Stat> getExerciseStats(int iExerID) {
        List<Stat> StatList = new ArrayList<Stat>();
 
        SQLiteDatabase db = this.getWritableDatabase();

	Cursor cursor = db.rawQuery("SELECT UserStats._id, UserStats.userID, UserStats.exerID, UserStats.weight, UserStats.reps, " +
	"UserStats.bandID, UserStats.time, UserStats.date, UserStats.notes, ExerKey.exerType FROM UserStats JOIN ExerKey ON ExerKey.exerID = UserStats.exerID" + 
	" WHERE UserStats.exerID = " + iExerID + " ORDER BY date DESC, UserStats._id DESC", null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stat Stat = new Stat();
                Stat.setID(cursor.getInt(0));
                Stat.setUserID(cursor.getInt(1));
                Stat.setExerciseID(cursor.getInt(2));
                Stat.setWeight(cursor.getInt(3));
                Stat.setReps(cursor.getInt(4));
                Stat.setBandID(cursor.getInt(5));
                Stat.setTime(cursor.getString(6));
                Stat.setDate(cursor.getString(7));
                Stat.setNotes(cursor.getString(8));
                Stat.setType(cursor.getInt(9));

                // Adding Day to list
                StatList.add(Stat);
            } while (cursor.moveToNext());
        }
 
  	cursor.close();
  	db.close();
        // return Stat list
        return StatList;
    }

  // Getting All Stats for the last day
    public List<Stat> getDayStats() {
        List<Stat> StatList = new ArrayList<Stat>();
 
        SQLiteDatabase db = this.getWritableDatabase();
	
	//building cursor to determin last date
	Cursor dcursor = db.rawQuery("SELECT _id, date FROM UserStats ORDER BY date DESC",null);
	
	dcursor.moveToFirst();
	
	String dLast = dcursor.getString(1);
	
	//building cursor based on last date
	Cursor cursor = db.rawQuery("SELECT UserStats._id, UserStats.userID, UserStats.exerID, UserStats.weight, UserStats.reps, " +
	"UserStats.bandID, UserStats.time, UserStats.date, UserStats.notes, ExerKey.exerType FROM UserStats JOIN ExerKey ON ExerKey.exerID = UserStats.exerID" + 
	" WHERE UserStats.date = " + dLast, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stat Stat = new Stat();
                Stat.setID(cursor.getInt(0));
                Stat.setUserID(cursor.getInt(1));
                Stat.setExerciseID(cursor.getInt(2));
                Stat.setWeight(cursor.getInt(3));
                Stat.setReps(cursor.getInt(4));
                Stat.setBandID(cursor.getInt(5));
                Stat.setTime(cursor.getString(6));
                Stat.setDate(cursor.getString(7));
                Stat.setNotes(cursor.getString(8));
                Stat.setType(cursor.getInt(9));

                // Adding Day to list
                StatList.add(Stat);
            } while (cursor.moveToNext());
        }
 
  	cursor.close();
  	db.close();
        // return Stat list
        return StatList;
    }

    // Update a Stat - when the user makes a change to a history item
    public void updateUserStat(int iWeight, int iReps, int iBandID, int sTime, String sDate, String sNotes, int _id) { 
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE userStats SET weight=" + iWeight + ", reps= " +  iReps + ", bandID= " + iBandID + ",time= " + 
	sTime + ",date= " + "'" + sDate + "'" + ",notes=" + "'" + sNotes + "'" + " WHERE _id=" + _id);
        
        db.close();
    }

//Save a stat - when user presses save/next
public void saveStat(int iUserID, int iExerID, int iWeight, int iReps, int iBandID, String sTime, String sDate, String sNotes){
        SQLiteDatabase db = this.getWritableDatabase();

	db.execSQL("INSERT INTO userStats (userID, exerID, weight, reps, bandID, time, date, notes) VALUES(" + 
	iUserID + "," + iExerID + "," + iWeight + "," + iReps + "," + iBandID + "," + "'" +sTime+"'" + "," + "'" + sDate + "'" + "," + "'" + sNotes + "'" + ")");
}

// Getting All Band Sets
public List<BandSet> getAllBandSets() {
    List<BandSet> BandSetList = new ArrayList<BandSet>();
    // Select All Query
    String selectQuery = "SELECT * FROM bandKey";

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            BandSet BandSet = new BandSet();
            BandSet.setID(cursor.getInt(0));
            BandSet.setSetName(cursor.getString(1));
            BandSet.setEditable(Boolean.valueOf(cursor.getString(2)));
            BandSet.setSetID(cursor.getInt(3));
            // Adding BandSet to list
            BandSetList.add(BandSet);
        } while (cursor.moveToNext());
    }

    // return BandSet list
    return BandSetList;
}
// Getting All Bands for a certain Program
public List<Band> getAllSetBands(int iSetID) {
    List<Band> BandList = new ArrayList<Band>();

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM bandOrder WHERE setID =" +  iSetID, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Band Band = new Band();
            Band.setID(cursor.getInt(0));
            Band.setColor(cursor.getString(1));
            Band.setSetID(cursor.getInt(2));
            Band.setWeight(cursor.getInt(3));
            Band.setEditable(Boolean.valueOf(cursor.getString(4)));
            
            // Adding Band to list
            BandList.add(Band);
        } while (cursor.moveToNext());
    }
		
		cursor.close();
		db.close();
    // return Band list
    return BandList;
}
 
}
