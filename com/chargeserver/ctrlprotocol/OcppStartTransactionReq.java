package com.chargeserver.ctrlprotocol;

import com.chargeserver.ocpp.Contents.Ocpp;

public class OcppStartTransactionReq {
	public int connectorId;
	public String idTag;
	public int meterStart;
	public int reservationId;
	public int timestamp;
	
	public int getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(int connectorId) {
		this.connectorId = connectorId;
	}
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public int getMeterStart() {
		return meterStart;
	}
	public void setMeterStart(int meterStart) {
		this.meterStart = meterStart;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public OcppStartTransactionReq(Ocpp.StartTransactionRequest req) {
		if (req.hasConnectorId()) {
			connectorId = req.getConnectorId();
		}
		if (req.hasIdTag()) {
			idTag = req.getIdTag();
		}
		if (req.hasMeterStart()) {
			meterStart = req.getMeterStart();
		}
		if (req.hasReservationId()) {
			reservationId = req.getReservationId();
		}
		if (req.hasTimestamp()) {
			timestamp = req.getTimestamp();
		}
	}
	
	public OcppStartTransactionResp handleMsg(int transactionId) {
		OcppStartTransactionResp resp = new OcppStartTransactionResp();
		IdTagInfo idTagInfo = new IdTagInfo();
		
		idTagInfo.setStatus(IdTagInfo.Status.Accepted);
		idTagInfo.setParentIdTag(idTag);
		resp.setIdTagInfo(idTagInfo);
		resp.setTransactionId(transactionId);
		resp.setIdTag(idTag);

		return resp;
	}
	
	public String toString() {
		return String.format("OcppStartTransactionReq: [tidTag = %s, connectorId = %d, meterStart = %d, reservationId = %d, timestamp = %d]",
				idTag, connectorId, meterStart, reservationId, timestamp);
	}
}
