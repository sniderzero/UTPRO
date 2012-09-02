package com.appsmarttech.ut90; 

public class BandSet {

//private variables
int _id, iSetID, iEditable;
String sSetName;

// Empty constructor
public BandSet(){

}
// constructor
public BandSet(int id, String sSetName, int iEditable, int iSetID){
this._id = id;
this.iSetID = iSetID;
this.sSetName = sSetName;
this.iEditable = iEditable;
}

//constructor
public BandSet(String sSetName, int iEditable, int iSetID){
this.iSetID = iSetID;
this.sSetName = sSetName;
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
return this.iSetID;
}

// setting band set ID
public void setSetID(int iSetID){
this.iSetID = iSetID;
}

//getting editable int
public int getEditable(){
return this.iEditable;
}

//setting editable int
public void setEditable(int iEditable){
this.iEditable = iEditable;
}

}