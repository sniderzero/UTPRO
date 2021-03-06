package com.appsmarttech.ut90; 

public class Band {

//private variables
int _id, iWeight, iBandID, iColorID;
String sColor;
Boolean bEditable;

// Empty constructor
public Band(){

}
// constructor
public Band(int id, String sColor, Boolean bEditable, int iBandID, int iWeight, int iColorID){
this._id = id;
this.iBandID = iBandID;
this.sColor = sColor;
this.bEditable = bEditable;
this.iWeight = iWeight;
this.iColorID = iColorID;
}

//constructor
public Band(String sColor, Boolean bEditable, int iBandID, int iWeight, int iColorID){
this.iBandID = iBandID;
this.sColor = sColor;
this.bEditable = bEditable;
this.iWeight = iWeight;
this.iColorID = iColorID;
}

// getting ID
public int getID(){
return this._id;
}

// setting id
public void setID(int _id){
this._id = _id;
}

//getting ColorID
public int getColorID(){
return this.iColorID;
}

//setting ColorID
public void setColorID(int iColorID){
this.iColorID = iColorID;
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
public int getBandID(){
return this.iBandID;
}

// setting setting band ID
public void setBandID(int iBandID){
this.iBandID = iBandID;
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