package com.openvehicles.OVMS.entities;

/**
 * OCM charge point
 *
 */
public class ChargePoint {

	public String ID;

	static public class OperatorInfo {
		public String Title;
	}
	public OperatorInfo OperatorInfo;

	static public class UsageType {
		public String Title;
	}
	public UsageType UsageType;
	public String UsageCost;

	static public class AddressInfo {
		public String
				Title,
				AddressLine1,
				Latitude,
				Longitude,
				AccessComments,
				RelatedURL;
	}
	public AddressInfo AddressInfo;

	public String NumberOfPoints;
	public String GeneralComments;

	static public class StatusType {
		public String Title;
	}
	public StatusType StatusType;

	static public class Connection {
		static public class ConnectionType {
			public String ID;
			public String Title;
		}
		public ConnectionType ConnectionType;

		static public class Level {
			public String Title;
		}
		public Level Level;
	}
	public Connection[] Connections;


	public ChargePoint() {
		// create sub class members:
		OperatorInfo = new OperatorInfo();
		UsageType = new UsageType();
		AddressInfo = new AddressInfo();
		StatusType = new StatusType();
		Connections = new Connection[0];
	}

}
