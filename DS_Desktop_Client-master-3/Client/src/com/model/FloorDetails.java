package com.model;

public class FloorDetails {

	private int id;
	private String status;
	private int floorNo;
	private String roomNo;
	private int smokeLevel;
	private int Co2Level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public int getSmokeLevel() {
		return smokeLevel;
	}
	public void setSmokeLevel(int smokeLevel) {
		this.smokeLevel = smokeLevel;
	}
	public int getCo2Level() {
		return Co2Level;
	}
	public void setCo2Level(int co2Level) {
		Co2Level = co2Level;
	}
}
