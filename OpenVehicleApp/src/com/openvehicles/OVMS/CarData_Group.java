package com.openvehicles.OVMS;

import java.io.Serializable;

public class CarData_Group implements Serializable {
	private static final long serialVersionUID = -1922439075707427968L;
	public double Altitude = 0.0D;
	public double Direction = 0.0D;
	public boolean GPSDataStale = false;
	public boolean GPSLocked = false;
	public double Latitude = 0.0D;
	public double Longitude = 0.0D;
	public double SOC = 0.0D;
	public double Speed = 0.0D;
	public String VehicleID = "";
	public String VehicleImageDrawable = "car_roadster_arcticwhite";
}