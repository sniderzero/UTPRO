package com.appsmarttech.utpro; 

public class Program {

//private variables
int _id, iTimesCompleted;
String sName;
Boolean bEditable;

// Empty constructor
public Program(){

}
// constructor
public Program(int id, String sName, Boolean bEditable, int iTimesCompleted){
this._id = id;
this.iTimesCompleted = iTimesCompleted;
this.sName = sName;
this.bEditable = bEditable;
}

//constructor
public Program(String sName, Boolean bEditable, int iTimesCompleted){
this.iTimesCompleted = iTimesCompleted;
this.sName = sName;
this.bEditable = bEditable;
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

// getting times completed
public int getTimesCompleted(){
return this.iTimesCompleted;
}

// setting setting times completed
public void setTimesCompleted(int iTimesCompleted){
this.iTimesCompleted = iTimesCompleted;
}

//getting editable boolean
public boolean getEditable(){
return this.bEditable;
}

//setting setting times completed
public void setEditable(boolean bEditable){
this.bEditable = bEditable;
}

}