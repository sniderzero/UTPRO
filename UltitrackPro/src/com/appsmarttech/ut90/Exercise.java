package com.appsmarttech.ut90;

public class Exercise {

	//private variables
	int _id, iDayID, iExerOrder, iExerID, iExerCompleted, iType;
	String sName;

	// Empty constructor
	public Exercise(){

	}
	// constructor with id
	public Exercise(int id, String sName, int iType, int iExerCompleted, int iExerID, int iDayID, int iExerOrder){
	this._id = id;
	this.iExerCompleted = iExerCompleted;
	this.sName = sName;
	this.iType = iType;
	this.iDayID = iDayID;
	this.iExerOrder = iExerOrder;
	this.iExerID = iExerID;
	}

	// constructor without id
	public Exercise(String sName, int iType, int iExerCompleted, int iExerID, int iDayID, int iExerOrder){
	this._id = -1;
	this.iExerCompleted = iExerCompleted;
	this.sName = sName;
	this.iType = iType;
	this.iDayID = iDayID;
	this.iExerOrder = iExerOrder;
	this.iExerID= iExerID;
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

	// getting if completed
	public int getExerCompleted(){
	return this.iExerCompleted;
	}

	// setting completed
	public void setExerCompleted(int iExerCompleted){
	this.iExerCompleted = iExerCompleted;
	}

	//getting Type
	public int getType(){
	return this.iType;
	}

	//setting setting type
	public void setType(int iType){
	this.iType = iType;
	}

	//getting DayID
	public int getDayID(){
	return this.iDayID;
	}

	//setting DayID
	public void setDayID(int iDayID){
	this.iDayID = iDayID;
	}

	//getting Exercise ID
	public int getExerID(){
	return this.iExerID;
	}

	//setting Exercise ID
	public void setExerID(int iExerID){
	this.iExerID = iExerID;
	}

	//getting Exercise order
	public int getExerOrder(){
	return this.iExerOrder;
	}

	//setting Exercise order
	public void setExerOrder(int iExerOrder){
	this.iExerOrder = iExerOrder;
	}

	
	}
