package com.appsmarttech.utpro; 

public class Band {

//private variables
int _id, iSetID, iWeight;
String sColor;
Boolean bEditable;

// Empty constructor
public Band(){

}
// constructor
public Band(int id, String sColor, Boolean bEditable, int iSetID, int iWeight){
this._id = id;
this.iSetID = iSetID;
this.sColor = sColor;
this.bEditable = bEditable;
this.iWeight = iWeight;
}

//constructor
public Band(String sColor, Boolean bEditable, int iSetID, int iWeight){
this.iSetID = iSetID;
this.sColor = sColor;
this.bEditable = bEditable;
this.iWeight = iWeight;
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
public String getColor(){
return this.sColor;
}

// setting name
public void setColor(String sColor){
this.sColor = sColor;
}

// getting times completed
public int getSetID(){
return this.iSetID;
}

// setting setting times completed
public void setSetID(int iSetID){
this.iSetID = iSetID;
}

//getting band weight
public int getWeight(){
return this.iWeight;
}

//setting setting band weight
public void setWeight(int iWeight){
this.iWeight = iWeight;
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