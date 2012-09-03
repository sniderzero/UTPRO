package com.appsmarttech.ut90;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper_activity extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.appsmarttech.ut90/databases/";
 
    private static String DB_NAME = "UT90";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
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
 
    // Getting All Programs
    public List<Program> getAllPrograms() {
        List<Program> programList = new ArrayList<Program>();
        
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Programs", null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Program Program = new Program();
                Program.setProgramID(cursor.getInt(1));
                Program.setName(cursor.getString(2));
                Program.setTimesCompleted(cursor.getInt(4));
                // Adding program to list
                programList.add(Program);
            } while (cursor.moveToNext());
        }
 
        // return program list
        return programList;
    }
    
    // Getting a single program
    Program getProgram(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Programs WHERE ProgramID = " + id, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Program Program = new Program();
        Program.setProgramID(cursor.getInt(1));
        Program.setName(cursor.getString(2));
        Program.setTimesCompleted(cursor.getInt(4));
        db.close();
        // return program
        return Program;
    }
    
    //updating the completions of a single program
    public void updateProgramCompletion(int iProgramID){
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT _id, ProgramCompleted FROM Programs WHERE ProgramID = " + iProgramID, null);
    	cursor.moveToFirst();
    	int iCompletions = cursor.getInt(1);
    	iCompletions = iCompletions + 1;
    	Log.d("COMPLETES:", String.valueOf(iCompletions));
    	db.execSQL("UPDATE Programs SET ProgramCompleted = " + iCompletions + " WHERE ProgramID = " + iProgramID);
    	db.close();
    }

 
	/**
     * All CRUD(Create, Read, Update, Delete) Operations for Days
     */
 
 
    // Getting a single Day from the Day Order Table joined on the Day Key table
    Day getDay(int dayID) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.rawQuery("SELECT DayOrderDetailsDetails._id, DayOrderDetailsDetails.DayID, DayOrderDetailsDetails.DayCompleted, " +
        		"Days.DayName, DayOrderDetailsDetails.DayNumber FROM DayOrderDetailsDetails JOIN Days ON " +
        		"DayOrderDetailsDetails.DayID=Days.DayID WHERE Days.DayID = " + dayID, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Day Day = new Day();
        Day.setID(cursor.getInt(0));
        Day.setDayID(cursor.getInt(1));
        Day.setCompleted(cursor.getInt(2));
        Day.setName(cursor.getString(3));
        Day.setDayNumber(cursor.getInt(5));
        cursor.close();
        db.close();
        // return Day
        return Day;
    }
 
    // Getting All Days for a certain Program
    public List<Day> getAllProgramDays(int programID) {
        List<Day> DayList = new ArrayList<Day>();
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DayOrderDetails._id, DayOrderDetails.DayID, DayOrderDetails.DayCompleted, " +
        		"Days.DayName, DayOrderDetails.DayNumber FROM DayOrderDetails JOIN Days ON " +
        		"DayOrderDetails.DayID=Days.DayID WHERE ProgramID = " + programID, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Day Day = new Day();
                Day.setID(cursor.getInt(0));
                Day.setDayID(cursor.getInt(1));
                Day.setCompleted(cursor.getInt(2));
                Day.setName(cursor.getString(3));
                Day.setDayNumber(cursor.getInt(4));
                // Adding Day to list
                DayList.add(Day);
            } while (cursor.moveToNext());
        }
 		
 		cursor.close();
 		db.close();
        // return Day list
        return DayList;
    }
    
    public int getNextDay(int programID){
		int iPOS;
		SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DayOrderDetails._id, DayOrderDetails.DayID, DayOrderDetails.DayCompleted, " +
        		"Days.DayName, DayOrderDetails.DayNumber FROM DayOrderDetails JOIN Days ON " +
        		"DayOrderDetails.DayID=Days.DayID WHERE ProgramID = " + programID + " AND DayOrderDetails.DayCompleted = 0 LIMIT 1", null);
        //checking if cursor is empty
        if(cursor.moveToFirst()){
        cursor.moveToFirst();
        	iPOS = cursor.getInt(0);  //if not empty set the position to the next day
        }
        else{
        	iPOS = 2;  // if empty set it to the first exercise.
        }
    	return iPOS;
    	
    }
 
    // Mark a Day Complete or skipped
    public void dayCompleteSkipped(int iStatus, int _id) { //iStatus is 1 for complete 2 for skipped, _id is the _id in the DayOrderDetails table
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE DayOrderDetails SET dayCompleted=" + iStatus + " WHERE _id=" + _id);
        
        db.close();
    }
 
    // Clear Program Progress
    public void progClearFlags(int programID) { 
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE DayOrderDetails SET dayCompleted= 0  WHERE programID=" + programID);
        
        db.close();
    }
    
    public int[] getDaysCount(int iProgramID){
    	int[] aryCount;
    	aryCount = new int[2];
    	SQLiteDatabase db = this.getReadableDatabase();
    	//grabbing total count
    	Cursor count = db.rawQuery("SELECT COUNT(*) FROM DayOrderDetails WHERE ProgramID = " + iProgramID, null);
    	//moving to first record
    	count.moveToFirst();
    	//assigning total count to an int
    	int iTotal = count.getInt(0);
    	//closing cursor
    	count.close();
    	//grabbing completed count
    	count = db.rawQuery("SELECT COUNT(*) FROM DayOrderDetails WHERE ProgramID = " + iProgramID + " AND DayCompleted != 0", null);
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

	Cursor cursor = db.rawQuery("SELECT ExerciseOrderDetails._id, ExerciseOrderDetails.ExerciseOrder, ExerciseOrderDetails.ExerciseID, ExerciseOrderDetails.ExerciseCompleted, " +
	"Exercises.ExerciseName, Exercises.ExerciseType FROM ExerciseOrderDetails JOIN Exercises ON Exercises.ExerciseID = ExerciseOrderDetails.ExerciseID WHERE ExerciseOrderDetails.DayID = " + dayID, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercise Exercise = new Exercise();
                Exercise.setID(cursor.getInt(0));
                Exercise.setExerOrder(cursor.getInt(1));
                Exercise.setExerID(cursor.getInt(2));
                Exercise.setExerCompleted(cursor.getInt(3));
                Exercise.setName(cursor.getString(4));
                Exercise.setType(cursor.getInt(5));
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
 
        db.execSQL("UPDATE ExerciseOrderDetails SET ExerciseCompleted=" + iStatus + " WHERE _id=" + _id);
        
        db.close();
    }

    // Clear Day Progress
    public void dayClearFlags(int dayID) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.execSQL("UPDATE ExerciseOrderDetails SET ExerciseCompleted= 0 WHERE ExerciseCompleted != null && DayID=" + dayID);
        
        db.close();
    }
    
 // Getting All Stats for a certain exerciseID
    public List<Stat> getExerciseStats(int iExerID) {
        List<Stat> StatList = new ArrayList<Stat>();
 
        SQLiteDatabase db = this.getWritableDatabase();

	Cursor cursor = db.rawQuery("SELECT UserStats._id, UserStats.ExerciseID, UserStats.StatWeight, UserStats.StatReps, " +
	" UserStats.StatTime, UserStats.StatDate, UserStats.StatNotes, UserStats.BandID, Exercises.ExerciseType, BandColors.BandColorName FROM UserStats JOIN Exercises ON Exercises.ExerciseID = UserStats.ExerciseID" + 
	" JOIN BandColors ON UserStats.BandColorID = BandColors.BandColorID WHERE UserStats.ExerciseID = " + iExerID + " ORDER BY StatDate DESC, UserStats._id DESC", null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stat Stat = new Stat();
                Stat.setID(cursor.getInt(0));
                Stat.setExerciseID(cursor.getInt(1));
                Stat.setWeight(cursor.getInt(2));
                Stat.setReps(cursor.getInt(3));
                Stat.setTime(cursor.getString(4));
                Stat.setDate(cursor.getString(5));
                Stat.setNotes(cursor.getString(6));
                Stat.setBandID(cursor.getInt(7));
                Stat.setType(cursor.getInt(8));
                Stat.setColor(cursor.getString(9));

                // Adding Day to list
                StatList.add(Stat);
            } while (cursor.moveToNext());
        }
 
  	cursor.close();
  	db.close();
        // return Stat list
        return StatList;
    }
    
 // Getting All Last Stat for a certain exerciseID
    public List<Stat> getExerciseLastStat(int iExerID) {
        List<Stat> StatList = new ArrayList<Stat>();
 
        SQLiteDatabase db = this.getWritableDatabase();

    	Cursor cursor = db.rawQuery("SELECT UserStats._id, UserStats.ExerciseID, UserStats.StatWeight, UserStats.StatReps, " +
    			" UserStats.StatTime, UserStats.StatDate, UserStats.StatNotes, UserStats.BandID, Exercises.ExerciseType, BandColors.BandColorName FROM UserStats JOIN Exercises ON Exercises.ExerciseID = UserStats.ExerciseID" + 
    			" JOIN BandColors ON UserStats.BandColorID = BandColors.BandColorID WHERE UserStats.ExerciseID = " + iExerID + " ORDER BY StatDate DESC, UserStats._id DESC LIMIT 1", null);
    		 
    		        // looping through all rows and adding to list
    		        if (cursor.moveToFirst()) {
    		            do {
    		                Stat Stat = new Stat();
    		                Stat.setID(cursor.getInt(0));
    		                Stat.setExerciseID(cursor.getInt(1));
    		                Stat.setWeight(cursor.getInt(2));
    		                Stat.setReps(cursor.getInt(3));
    		                Stat.setTime(cursor.getString(4));
    		                Stat.setDate(cursor.getString(5));
    		                Stat.setNotes(cursor.getString(6));
    		                Stat.setBandID(cursor.getInt(7));
    		                Stat.setType(cursor.getInt(8));
    		                Stat.setColor(cursor.getString(9));

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
public void saveStat(int iUserID, int iExerID, int iWeight, int iReps, int iBandID, String sTime, String sDate, String sNotes, int bandColorID){
        SQLiteDatabase db = this.getWritableDatabase();

	db.execSQL("INSERT INTO UserStats (UserID, ExerciseID, StatWeight, StatReps, BandID, StatTime, StatDate, StatNotes, BandColorID) VALUES(" + 
	iUserID + "," + iExerID + "," + iWeight + "," + iReps + "," + iBandID + "," + "'" +sTime+"'" + "," + "'" + sDate + "'" + "," + "'" + sNotes + "'" +","+ bandColorID + ")");
}

// Getting All Band Sets
public List<BandSet> getAllBandSets() {
    List<BandSet> BandSetList = new ArrayList<BandSet>();
    // Select All Query
    String selectQuery = "SELECT * FROM BandSets";

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            BandSet BandSet = new BandSet();
            BandSet.setSetID(cursor.getInt(1));
            BandSet.setSetName(cursor.getString(2));
            BandSet.setEditable(cursor.getInt(3));
            // Adding BandSet to list
            BandSetList.add(BandSet);
        } while (cursor.moveToNext());
    }

    // return BandSet list
    return BandSetList;
}
// Getting All Bands for a certain set a bands
public List<Band> getAllSetBands(int iSetID) {
    List<Band> BandList = new ArrayList<Band>();

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT BandDetails._id, BandDetails.BandID, BandDetails.BandWeight, BandDetails.BandColorID, BandColors.BandColorName " +
    		"FROM BandDetails JOIN BandColors ON BandDetails.BandColorID = BandColors.BandColorID WHERE BandSetID =" + iSetID, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Band Band = new Band();
            Band.setBandID(cursor.getInt(1));
            Band.setWeight(cursor.getInt(2));
            Band.setColorID(cursor.getInt(3));
            Band.setColor(cursor.getString(4));
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
