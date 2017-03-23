package com.chargesystem.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import com.chargeserver.ctrlprotocol.OcppRemoteStartTransactionResp;
import com.chargeserver.ctrlprotocol.OcppRemoteStartStopStatus.Status;
import com.chargeserver.server.Server;

class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String sex;
	public int age;
};

public class WebServiceInterface {
	private static RemoteMsgHandler handler = null;
	private static Server server = null;
	
	static {
		Server.getInstance();
		handler = new RemoteMsgHandler();
	}
	
//	public enum Status {
//		Accepted(1),
//		Rejected(2);
//		
//		private int value = 0;
//		
//		private Status(int value) {
//			this.value = value;
//		}
//		
//		public static Status valueOf(int value) {
//			switch(value) {
//			case 1:
//				return Accepted;
//			case 2:
//				return Rejected;
//			default:
//				return null;
//			}
//		}
//		
//		public int value() {
//			return this.value;
//		}
//	}
	
	public WebServiceInterface() {
	}
	/*
	public Status remoteStartTransaction(String chargePoint, String chargeBox, 
											int connectorId, String idTag) {
		Status status = handler.handleRemoteStartTransaction(chargePoint, chargeBox, connectorId, idTag);
		return status;
	}
	
	public Status remoteStopTransaction(String chargePoint, String chargeBox, int connectorId) {
		Status status = handler.handleRemoteStopTransaction(chargePoint, chargeBox, connectorId);
		return status;
	}
	*/
	
	public String remoteStartCharging(String chargePoint, String chargeBox, 
				int connectorId, String idTag) {
		Date d = new Date();
		String res = String.format("Current time=%s\nFunction=%s\nchargeId=%s\nConnector Id=%d\nidTag=%s\n", 
				d.toString(), "remoteStartCharging", chargePoint+chargeBox, connectorId, idTag);
		
		return res;
	}

	public String remoteStopCharging(String chargePoint, String chargeBox, int connectorId) {
		Date d = new Date();
		String res = String.format("Current time=%s\nFunction=%s\nchargeId=%s\nConnector Id=%d\n", 
				d.toString(), "remoteStopCharging", chargePoint+chargeBox, connectorId);
		
		return res;
	}
	
	public byte[] answer() {
		Response r = new Response();
		r.name = "Addams";
		r.sex = "Male";
		r.age = 40;
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(os);
			oos.writeObject(r);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return os.toByteArray();
	}
}
