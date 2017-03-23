package com.chargeserver.ctrlprotocol;

public class OcppRemoteStartTransactionReq {
	public String idTag;
	public int connectorId;
	
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public int getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}
}
