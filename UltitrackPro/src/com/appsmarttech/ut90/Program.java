package com.appsmarttech.ut90; 

public class Program {

//private variables
int _id, iTimesCompleted, iProgramID, iEditable;
String sName;

// Empty constructor
public Program(){

}
// constructor
public Program(int id, String sName, int iEditable, int iTimesCompleted){
this._id = id;
this.iTimesCompleted = iTimesCompleted;
this.sName = sName;
this.iEditable = iEditable;
}

//constructor
public Program(String sName, int iEditable, int iTimesCompleted){
this.iTimesCompleted = iTimesCompleted;
this.sName = sName;
this.iEditable = iEditable;
}

// getting ID
public int getID(){
return this._id;
}

// setting id
public void setID(int _id){
this._id = _id;
}

//getting ProgramID
public int getProgramID(){
return this.iProgramID;
}

//setting id
public void setProgramID(int iProgramID){
this.iProgramID = iProgramID;
}

// getting name
public String getName(){
return this.sName;
}

// setting name
public void setName(String sName){
this.sName = sName;
}

// getting times completed
public int getTimesCompleted(){
return this.iTimesCompleted;
}

// setting setting times completed
public void setTimesCompleted(int iTimesCompleted){
this.iTimesCompleted = iTimesCompleted;
}

//getting editable int
public int getEditable(){
return this.iEditable;
}

//setting setting times completed
public void setEditable(int iEditable){
this.iEditable = iEditable;
}

}