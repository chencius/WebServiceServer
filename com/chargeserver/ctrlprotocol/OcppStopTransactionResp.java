package com.chargeserver.ctrlprotocol;

public class OcppStopTransactionResp {
	public IdTagInfo idTagInfo;
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

	public OcppStopTransactionResp() {
		
	}
	
	public String toString() {
		return String.format("OcppStopTransactionResp: [[parentIdTag = %s, status = %d, idTag = %s]",
							idTagInfo.parentIdTag, idTagInfo.status.value(), idTag);
	}
}
