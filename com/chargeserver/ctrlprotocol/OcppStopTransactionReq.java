package com.chargeserver.ctrlprotocol;

import com.chargeserver.ocpp.Contents.Ocpp;

public class OcppStopTransactionReq {
	public String idTag;
	public int meterStop;
	public int timestamp;
	public int transactionId;
	public int ratePerMeter;
	
	public final int oneDay = 60*60*24;
	
	public OcppStopTransactionReq(Ocpp.StopTransactionRequest req) {
		if (req.hasIdTag()) {
			idTag = req.getIdTag();
		}
		if (req.hasMeterStop()) {
			meterStop = req.getMeterStop();
		}
		if (req.hasTimestamp()) {
			timestamp = req.getTimestamp();
		}
		if (req.hasTransactionId()) {
			transactionId = req.getTransactionId();
		}
		if (req.hasRatePerMeter()) {
			ratePerMeter = req.getRatePerMeter();
		} else {
			ratePerMeter = 0;
		}
	}
	
	public OcppStopTransactionResp handleMsg() {
		OcppStopTransactionResp resp = new OcppStopTransactionResp();
		IdTagInfo idTagInfo = new IdTagInfo();
		
		idTagInfo.setStatus(IdTagInfo.Status.Accepted);
		idTagInfo.setParentIdTag(this.idTag);
		resp.setIdTagInfo(idTagInfo);
		resp.setIdTag(idTag);

		return resp;
	}
	
	public String toString() {
		return String.format("OcppStopTransactionReq: [idTag = %s, meterStop = %d, timestamp = %d, transactionId = %d]",
				idTag, meterStop, timestamp, transactionId);
	}
}
