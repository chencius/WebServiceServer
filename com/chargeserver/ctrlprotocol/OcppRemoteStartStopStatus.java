package com.chargeserver.ctrlprotocol;

public class OcppRemoteStartStopStatus {
	public enum Status {
		Accepted(1),
		Rejected(2);
		
		private int value = 0;
		
		private Status(int value) {
			this.value = value;
		}
		
		public static Status valueOf(int value) {
			switch(value) {
			case 1:
				return Accepted;
			case 2:
				return Rejected;
			default:
				return null;
			}
		}
		
		public int value() {
			return this.value;
		}
	}
}
