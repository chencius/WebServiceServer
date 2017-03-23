package com.chargeserver.ctrlprotocol;

public class OcppBootNotfResp {
	public enum RegistrationStatus {
		Accepted(1),
		Rejected(2);
		
		private int value = 0;
		
		private RegistrationStatus(int value) {
			this.value = value;
		}
		
		public static RegistrationStatus valueOf(int value) {
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
	
	public int currentTime;
	public int interval;
	public RegistrationStatus status;

	public OcppBootNotfResp() {
		
	}
	
	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public RegistrationStatus getStatus() {
		return status;
	}

	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}
	
	public String toString() {
		return String.format("OcppBootNotfResp: [currentTime = %d, interval = %d, status = %d]",
				currentTime, interval, status.value());

	}
}
