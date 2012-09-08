package com.appsmarttech.ut90; 

public class Day {

//private variables
int _id, iCompleted, iDayNumber, iDayID, iDayOrder;
String sName;

// Empty constructor
public Day(){

}
// constructor
public Day(int id, String sName, int iCompleted, int iDayID, int iDayNumber, int iDayOrder){
this._id = id;
this.iCompleted = iCompleted;
this.sName = sName;
this.iDayID = iDayID;
this.iDayNumber = iDayNumber;
}

//constructor
public Day(String sName, int iCompleted, int iDayID, int iDayNumber, int iDayOrder){
this.iCompleted = iCompleted;
this.sName = sName;
this.iDayID = iDayID;
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

//getting DayID
public int getDayID(){
return this.iDayID;
}

//setting DayID
public void setDayID(int iDayID){
this.iDayID = iDayID;
}


//getting day number
public int getDayNumber(){
return this.iDayNumber;
}

//setting day number
public void setDayNumber(int iDayNumber){
this.iDayNumber = iDayNumber;
}

//getting day order
public int getDayOrder(){
return this.iDayOrder;
}

//setting day order
public void setDayOrder(int iDayOrder){
this.iDayOrder = iDayOrder;
}

}