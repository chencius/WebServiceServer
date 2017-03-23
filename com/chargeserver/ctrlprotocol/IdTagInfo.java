package com.chargeserver.ctrlprotocol;

public class IdTagInfo {
	public enum Status {
		Accepted(1),
		Blocked(2),
		Expired(3),
		Invalid(4),
		ConcurrentTx(5),
		ServerUnreachable(6);
		
		private int value = 0;
		
		private Status(int value) {
			this.value = value;
		}
		
		public static Status valueOf(int value) {
			switch(value) {
			case 1:
				return Accepted;
			case 2:
				return Blocked;
			case 3:
				return Expired;
			case 4:
				return Invalid;
			case 5:
				return ConcurrentTx;
			case 6:
				return ServerUnreachable;
			default:
				return null;
			}
		}
		
		public int value() {
			return this.value;
		}
	}
	public Status status;
	public int expireDate;
	public String parentIdTag;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(int expireDate) {
		this.expireDate = expireDate;
	}
	public String getParentIdTag() {
		return parentIdTag;
	}
	public void setParentIdTag(String parentIdTag) {
		this.parentIdTag = parentIdTag;
	}
}
