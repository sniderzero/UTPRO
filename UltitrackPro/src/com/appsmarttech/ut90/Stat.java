package com.appsmarttech.ut90;

import java.util.Date;

public class Stat {

//need to look into the necessity of bandID and how to handle time (eff)


//private variables
int _id, iUserID, iExerID, iWeight, iReps, iBandID, iExerType;
String sName, sNotes, sTime;
String sDate;

// Empty constructor
public Stat(){

}

// constructor with id for saving
public Stat(int _id, int iUserID, int iExerID, int iWeight, int iReps, int iBandID, String sNotes, String sDate, String sTime){
this._id = _id;
this.iUserID = iUserID;
this.iExerID = iExerID;
this.iWeight = iWeight;
this.iReps = iReps;
this.iBandID = iBandID;
this.sNotes = sNotes;
this.sDate = sDate;
this.sTime = sTime;
}

// constructor without id for saving
public Stat(int iUserID, int iExerID, int iWeight, int iReps, int iBandID, String sNotes, String sDate, String sTime){
this._id = -1;
this.iUserID = iUserID;
this.iExerID = iExerID;
this.iWeight = iWeight;
this.iReps = iReps;
this.iBandID = iBandID;
this.sNotes = sNotes;
this.sDate = sDate;
this.sTime = sTime;
}

// constructor with id for retrieving - includes the name from the exercisekey and exertype
public Stat(int _id, int iUserID, int iExerID, int iWeight, int iReps, int iBandID, String sNotes, String sName, String sDate, String sTime, int iExerType){
this._id = _id;
this.iUserID = iUserID;
this.iExerID = iExerID;
this.iWeight = iWeight;
this.iReps = iReps;
this.iBandID = iBandID;
this.sNotes = sNotes;
this.sName = sName;
this.sDate = sDate;
this.sTime = sTime;
this.iExerType = iExerType;
}

// getting ID
public int getID(){
return this._id;
}

// setting id
public void setID(int _id){
this._id = _id;
}

// getting userID
public int getUserID(){
return this.iUserID;
}

// setting UserID
public void setUserID(int iUserID){
this.iUserID = iUserID;
}

// getting Exercise ID
public int getExerciseID(){
return this.iExerID;
}

// setting Exercise ID
public void setExerciseID(int iExerID){
this.iExerID = iExerID;
}

// getting Weight
public int getWeight(){
return this.iWeight;
}

// setting Weight
public void setWeight(int iWeight){
this.iWeight = iWeight;
}

// getting Reps
public int getReps(){
return this.iReps;
}

// setting Reps
public void setReps(int iReps){
this.iReps = iReps;
}

// getting Band ID
public int getBandID(){
return this.iBandID;
}

// setting Band ID
public void setBandID(int iBandID){
this.iBandID = iBandID;
}

// getting Time
public String getTime(){
return this.sTime;
}

// setting Time
public void setTime(String sTime){
this.sTime = sTime;
}

// getting Type
public int getType(){
return this.iExerType;
}

// setting Type
public void setType(int iExerType){
this.iExerType = iExerType;
}

// getting Notes
public String getNotes(){
return this.sNotes;
}

// setting Notes
public void setNotes(String sNotes){
this.sNotes = sNotes;
}

// getting Name
public String getName(){
return this.sName;
}

// setting Name
public void setName(String sName){
this.sName = sName;
}

// getting Date
public String getDate(){
return this.sDate;
}

// setting Date
public void setDate(String sDate){
this.sDate = sDate;
}

}