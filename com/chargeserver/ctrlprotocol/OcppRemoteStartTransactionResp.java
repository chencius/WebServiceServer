package com.chargeserver.ctrlprotocol;

import com.chargeserver.ctrlprotocol.OcppRemoteStartStopStatus.Status;
import com.chargeserver.ocpp.Contents.Ocpp;

public class OcppRemoteStartTransactionResp {
	public Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public OcppRemoteStartTransactionResp(Ocpp.RemoteStartTransactionResponse resp) {
		if (resp.hasStatus()) {
			status = Status.valueOf(resp.getStatus().getNumber());
		} else {
			status = Status.Rejected;
		}
	}
}
