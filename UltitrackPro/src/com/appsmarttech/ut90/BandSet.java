package com.appsmarttech.ut90; 

public class BandSet {

//private variables
int _id, isID;
String sSetName;
Boolean bEditable;

// Empty constructor
public BandSet(){

}
// constructor
public BandSet(int id, String sSetName, Boolean bEditable, int isID){
this._id = id;
this.isID = isID;
this.sSetName = sSetName;
this.bEditable = bEditable;
}

//constructor
public BandSet(String sSetName, Boolean bEditable, int isID){
this.isID = isID;
this.sSetName = sSetName;
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

// getting band set name
public String getSetName(){
return this.sSetName;
}

// setting band set name
public void setSetName(String sSetName){
this.sSetName = sSetName;
}

// getting band set ID
public int getSetID(){
return this.isID;
}

// setting band set ID
public void setSetID(int isID){
this.isID = isID;
}

//getting editable boolean
public boolean getEditable(){
return this.bEditable;
}

//setting editable boolean
public void setEditable(boolean bEditable){
this.bEditable = bEditable;
}

}