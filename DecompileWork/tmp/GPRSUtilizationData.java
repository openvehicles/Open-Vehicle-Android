package com.openvehicles.OVMS;

import java.io.Serializable;
import java.util.Date;

public class GPRSUtilizationData implements Serializable {
	private static final long serialVersionUID = 1139602903176777992L;
	public long App_rx;
	public long App_tx;
	public long Car_rx;
	public long Car_tx;
	public Date DataDate;

	public GPRSUtilizationData() {
		this.DataDate = null;
		this.Car_tx = 0L;
		this.Car_rx = 0L;
		this.App_tx = 0L;
		this.App_rx = 0L;
	}

	public GPRSUtilizationData(Date paramDate, long paramLong1,
			long paramLong2, long paramLong3, long paramLong4) {
		this.DataDate = paramDate;
		this.Car_tx = paramLong2;
		this.Car_rx = paramLong1;
		this.App_tx = paramLong4;
		this.App_rx = paramLong3;
	}
}