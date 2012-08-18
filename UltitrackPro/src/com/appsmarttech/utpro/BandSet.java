package com.appsmarttech.utpro; 

public class BandSet {

//private variables
int _id, iSetID;
String sSetName;
Boolean bEditable;

// Empty constructor
public BandSet(){

}
// constructor
public BandSet(int id, String sSetName, Boolean bEditable, int iSetID){
this._id = id;
this.iSetID = iSetID;
this.sSetName = sSetName;
this.bEditable = bEditable;
}

//constructor
public BandSet(String sSetName, Boolean bEditable, int iSetID){
this.iSetID = iSetID;
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

// getting name
public String getSetName(){
return this.sSetName;
}

// setting name
public void setSetName(String sSetName){
this.sSetName = sSetName;
}

// getting times completed
public int getSetID(){
return this.iSetID;
}

// setting setting times completed
public void setSetID(int iSetID){
this.iSetID = iSetID;
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