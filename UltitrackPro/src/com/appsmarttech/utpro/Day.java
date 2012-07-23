package com.appsmarttech.utpro; 

public class Day {

//private variables
int _id, iCompleted, iDayNumber, iProgramID, iDayID, iWeekNumber, iType;
String sName;

// Empty constructor
public Day(){

}
// constructor
public Day(int id, String sName, int iType, int iCompleted, int iDayID, int iWeekNumber, int iDayNumber){
this._id = id;
this.iCompleted = iCompleted;
this.sName = sName;
this.iType = iType;
this.iDayID = iDayID;
this.iWeekNumber = iWeekNumber;
this.iDayNumber = iDayNumber;
}

//constructor
public Day(String sName, int iType, int iCompleted, int iDayID, int iWeekNumber, int iDayNumber){
this.iCompleted = iCompleted;
this.sName = sName;
this.iType = iType;
this.iDayID = iDayID;
this.iWeekNumber = iWeekNumber;
this.iDayNumber = iDayNumber;
}

// getting ID
public int getID(){
return this._id;
}

// setting id
public void setID(int _id){
this._id = _id;
}

// getting name
public String getName(){
return this.sName;
}

// setting name
public void setName(String sName){
this.sName = sName;
}

// getting if  completed
public int getCompleted(){
return this.iCompleted;
}

// setting completed
public void setCompleted(int iCompleted){
this.iCompleted = iCompleted;
}

//getting Type 
public int getType(){
return this.iType;
}

//setting setting type
public void setType(int iType){
this.iType = iType;
}

//getting DayID
public int getDayID(){
return this.iDayID;
}

//setting DayID
public void setDayID(int iDayID){
this.iDayID = iDayID;
}

//getting week number
public int getWeekNumber(){
return this.iWeekNumber;
}

//setting week number
public void setWeekNumber(int iWeekNumber){
this.iWeekNumber = iWeekNumber;
}

//getting day number
public int getDayNumber(){
return this.iDayNumber;
}

//setting day number
public void setDayNumber(int iDayNumber){
this.iDayNumber = iDayNumber;
}

}