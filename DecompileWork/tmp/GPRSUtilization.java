package com.openvehicles.OVMS;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class GPRSUtilization implements Serializable {
	public static final transient int FLAG_APP_RX = 4;
	public static final transient int FLAG_APP_TX = 8;
	public static final transient int FLAG_CAR_RX = 1;
	public static final transient int FLAG_CAR_TX = 2;
	private static final long serialVersionUID = 3347602214911458385L;
	public Date LastDataRefresh = null;
	public ArrayList<GPRSUtilizationData> Utilizations;
	private transient Context mContext;
	private final String settingsFileName = "OVMSSavedGPRSUtilization.obj";

	public GPRSUtilization(Context paramContext) {
		this.mContext = paramContext;
		try {
			Log.d("OVMS",
					"Loading saved GPRS utilizations from internal storage file: OVMSSavedGPRSUtilization.obj");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					paramContext.openFileInput("OVMSSavedGPRSUtilization.obj"));
			this.Utilizations = ((ArrayList) localObjectInputStream
					.readObject());
			localObjectInputStream.close();
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = Integer.valueOf(this.Utilizations.size());
			Log.d("OVMS", String.format("Loaded %s saved utilizations.",
					arrayOfObject));
			return;
		} catch (Exception localException) {
			while (true) {
				Log.d("ERR", localException.getMessage());
				Log.d("OVMS", "Initializing with utilization data.");
				this.Utilizations = new ArrayList();
				Save();
			}
		}
	}

	public void AddData(GPRSUtilizationData paramGPRSUtilizationData) {
		this.Utilizations.add(paramGPRSUtilizationData);
	}

	public void AddData(Date paramDate, long paramLong1, long paramLong2,
			long paramLong3, long paramLong4) {
		AddData(new GPRSUtilizationData(paramDate, paramLong1, paramLong2,
				paramLong3, paramLong4));
	}

	public void Clear() {
		this.Utilizations = new ArrayList();
	}

	public long GetUtilizationBytes(Date paramDate, int paramInt) {
		long l = 0L;
		for (int i = 0;; i++) {
			if (i >= this.Utilizations.size())
				return l;
			GPRSUtilizationData localGPRSUtilizationData = (GPRSUtilizationData) this.Utilizations
					.get(i);
			if ((localGPRSUtilizationData.DataDate.after(paramDate))
					|| (localGPRSUtilizationData.DataDate.equals(paramDate))) {
				if ((paramInt & 0x1) == 1)
					l += localGPRSUtilizationData.Car_rx;
				if ((paramInt & 0x2) == 2)
					l += localGPRSUtilizationData.Car_tx;
				if ((paramInt & 0x4) == 4)
					l += localGPRSUtilizationData.App_rx;
				if ((paramInt & 0x8) == 8)
					l += localGPRSUtilizationData.App_tx;
			}
		}
	}

	public void Save() {
		try {
			Log.d("OVMS", "Saving GPRS utilizations data to interal storage...");
			if (this.mContext == null) {
				Log.d("OVMS", "Context == null. Saving aborted.");
			} else {
				ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
						this.mContext.openFileOutput(
								"OVMSSavedGPRSUtilization.obj", 0));
				localObjectOutputStream.writeObject(this.Utilizations);
				localObjectOutputStream.close();
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = Integer.valueOf(this.Utilizations.size());
				Log.d("OVMS", String.format("Saved %s records.", arrayOfObject));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			Log.d("ERR", localException.getMessage());
		}
	}

	public void Save(Context paramContext) {
		this.mContext = paramContext;
		Save();
	}
}