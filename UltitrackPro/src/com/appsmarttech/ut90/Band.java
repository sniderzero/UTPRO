package com.appsmarttech.ut90; 

public class Band {

//private variables
int _id, iWeight, ibID;
String sColor;
Boolean bEditable;

// Empty constructor
public Band(){

}
// constructor
public Band(int id, String sColor, Boolean bEditable, int ibID, int iWeight){
this._id = id;
this.ibID = ibID;
this.sColor = sColor;
this.bEditable = bEditable;
this.iWeight = iWeight;
}

//constructor
public Band(String sColor, Boolean bEditable, int ibID, int iWeight){
this.ibID = ibID;
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

// getting band Color
public String getColor(){
return this.sColor;
}

// setting band color
public void setColor(String sColor){
this.sColor = sColor;
}

// getting band ID
public int getbID(){
return this.ibID;
}

// setting setting band ID
public void setbID(int ibID){
this.ibID = ibID;
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