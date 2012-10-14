package com.openvehicles.OVMS;

public class RC4 {
	private byte[] state = new byte[256];
	private int x;
	private int y;

	public RC4(String paramString) throws NullPointerException {
		this(paramString.getBytes());
	}

	public RC4(byte[] paramArrayOfByte) throws NullPointerException {
		int j;
		int k;
		for (int i = 0;; i++) {
			if (i >= 256) {
				this.x = 0;
				this.y = 0;
				j = 0;
				k = 0;
				if ((paramArrayOfByte != null)
						&& (paramArrayOfByte.length != 0))
					break;
				throw new NullPointerException();
			}
			this.state[i] = (byte) i;
		}
		for (int m = 0;; m++) {
			if (m >= 256)
				return;
			k = 0xFF & k
					+ ((0xFF & paramArrayOfByte[j]) + (0xFF & this.state[m]));
			int n = this.state[m];
			this.state[m] = this.state[k];
			this.state[k] = n;
			j = (j + 1) % paramArrayOfByte.length;
		}
	}

	public byte[] rc4(String paramString) {
		byte[] arrayOfByte;
		if (paramString == null)
			arrayOfByte = null;
		while (true) {
			return arrayOfByte;
			arrayOfByte = paramString.getBytes();
			rc4(arrayOfByte);
		}
	}

	public byte[] rc4(byte[] paramArrayOfByte) {
		byte[] arrayOfByte;
		if (paramArrayOfByte == null)
			arrayOfByte = null;
		while (true) {
			return arrayOfByte;
			arrayOfByte = new byte[paramArrayOfByte.length];
			for (int i = 0; i < paramArrayOfByte.length; i++) {
				this.x = (0xFF & 1 + this.x);
				this.y = (0xFF & (0xFF & this.state[this.x]) + this.y);
				int j = this.state[this.x];
				this.state[this.x] = this.state[this.y];
				this.state[this.y] = j;
				int k = 0xFF & (0xFF & this.state[this.x])
						+ (0xFF & this.state[this.y]);
				arrayOfByte[i] = (byte) (paramArrayOfByte[i] ^ this.state[k]);
			}
		}
	}
}