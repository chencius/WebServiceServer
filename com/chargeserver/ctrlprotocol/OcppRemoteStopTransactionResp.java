package com.chargeserver.ctrlprotocol;

import com.chargeserver.ctrlprotocol.OcppRemoteStartStopStatus.Status;
import com.chargeserver.ocpp.Contents.Ocpp;

public class OcppRemoteStopTransactionResp {
	public Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public OcppRemoteStopTransactionResp(Ocpp.RemoteStopTransactionResponse resp) {
		if (resp.hasStatus()) {
			status = Status.valueOf(resp.getStatus().getNumber());
		} else {
			status = Status.Rejected;
		}
	}
}
