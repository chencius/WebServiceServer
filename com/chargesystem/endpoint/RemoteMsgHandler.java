package com.chargesystem.endpoint;

import com.chargeserver.ctrlprotocol.OcppRemoteStartTransactionResp;
import com.chargeserver.ctrlprotocol.OcppRemoteStartStopStatus.Status;
import com.chargeserver.server.Server;

public class RemoteMsgHandler {
	// Need to further check for invalid characters in the string
	private String checkString(String str) {
		if (str == null) {
			return null;
		}
		String res = str.trim();
		return res;
	}
	
	public Status handleRemoteStartTransaction(
						String chargePoint, String chargeBox, 
						int connectorId, String idTag) {
		chargePoint = checkString(chargePoint);
		chargeBox = checkString(chargeBox);
		idTag = checkString(idTag);
		
		String chargeId = chargePoint + chargeBox;
		System.out.println("Receive RemoteStartTransaction with chargeId="+chargeId+" connectorId="+connectorId+" idTag="+idTag);
		if (!Server.getInstance().encProtocol.chargePointToChat.containsKey(chargeId)) {
			System.out.println("No such chargeId="+chargeId+" from RemoteStartTransaction");
			return Status.Rejected;
		} else {
			return Server.getInstance().encProtocol.handleRemoteStartTransaction(
					Server.getInstance().encProtocol.chargePointToChat.get(chargeId), connectorId, idTag);
		}
	}
	
	public Status handleRemoteStopTransaction(String chargePoint, String chargeBox, int connectorId) {
		chargePoint = checkString(chargePoint);
		chargeBox = checkString(chargeBox);
		
		String chargeId = chargePoint + chargeBox;
		System.out.println("Receive RemoteStopTransaction with chargeId="+chargeId+" connectorId="+connectorId);
		if (!Server.getInstance().encProtocol.chargePointToChat.containsKey(chargeId)) {
			System.out.println("No such chargeId="+chargeId+" from RemoteStopTransaction");
			return Status.Rejected;
		} else {
			return Server.getInstance().encProtocol.handleRemoteStopTransaction(
					Server.getInstance().encProtocol.chargePointToChat.get(chargeId), connectorId);
		}
	}
}
