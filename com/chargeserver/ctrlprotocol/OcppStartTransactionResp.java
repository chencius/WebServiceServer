package com.chargeserver.ctrlprotocol;

public class OcppStartTransactionResp {
	public IdTagInfo idTagInfo;
	public int transactionId;
	public String idTag;
	
	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public IdTagInfo getIdTagInfo() {
		return idTagInfo;
	}

	public void setIdTagInfo(IdTagInfo idTagInfo) {
		this.idTagInfo = idTagInfo;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public OcppStartTransactionResp() {
		
	}
	
	public String toString() {
		return String.format("OcppStartTransactionResp: [parentIdTag = %s, status = %d, transactionId = %d, idTag = %s]",
							idTagInfo.parentIdTag, idTagInfo.status.value(), transactionId, idTag);
	}
}
