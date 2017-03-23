package com.chargeserver.encodingprotocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.chargeserver.ctrlprotocol.IdTagInfo;
import com.chargeserver.ctrlprotocol.OcppBootNotfReq;
import com.chargeserver.ctrlprotocol.OcppBootNotfResp;
import com.chargeserver.ctrlprotocol.OcppRemoteStartTransactionReq;
import com.chargeserver.ctrlprotocol.OcppRemoteStartTransactionResp;
import com.chargeserver.ctrlprotocol.OcppRemoteStopTransactionReq;
import com.chargeserver.ctrlprotocol.OcppRemoteStopTransactionResp;
import com.chargeserver.ctrlprotocol.OcppStartTransactionReq;
import com.chargeserver.ctrlprotocol.OcppStartTransactionResp;
import com.chargeserver.ctrlprotocol.OcppStopTransactionReq;
import com.chargeserver.ctrlprotocol.OcppStopTransactionResp;
import com.chargeserver.ctrlprotocol.OcppRemoteStartStopStatus;
//import com.chargeserver.ctrlprotocol.IdTagInfo.Status;
import com.chargeserver.ocpp.Contents.Ocpp;
import com.chargeserver.ocpp.Envelope.Env;
import com.google.protobuf.InvalidProtocolBufferException;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

class ControlData {
	public final static int CONNECTORNUM = 4;
	public int transactionId;
	public int status;
	public int []transactionIdPerConnector;
};

public class ProtoBuf {
//	public enum Command {
//		REMOTESTARTTRANSACTION(0),
//		REMOTESTOPTRANSACTION(1),
//		UNKNOWCOMMAND(2);
//		
//		private int value = -1;
//		
//		private Command(int value) {
//			this.value = value;
//		}
//		
//		public static Command valueOf(int value) {
//			switch(value) {
//			case 0:
//				return REMOTESTARTTRANSACTION;
//			case 1:
//				return REMOTESTOPTRANSACTION;
//			default:
//				return null;
//			}
//		}
//		
//		public int value() {
//			return this.value;
//		}
//	}
	
	public Map<String, Chat> chargePointToChat = new HashMap<>();
	public Map<Chat, String> chatToChargepoint = new HashMap<>();
	private final int TIMEOUT = 60000;  // 30Seconds
	private Map<String, ControlData> chargepointCtrl = new HashMap<>();
	
	public Chat chat;
	public String handle;
	
	public Ocpp.IdTagInfo.Status idtaginfoStatusToEncodingProtocol(IdTagInfo.Status status) {
		switch (status) {
		case Accepted:
			return Ocpp.IdTagInfo.Status.Accepted;
		case Blocked:
			return Ocpp.IdTagInfo.Status.Blocked;
		case Expired:
			return Ocpp.IdTagInfo.Status.Expired;
		case Invalid:
			return Ocpp.IdTagInfo.Status.Invalid;
		case ConcurrentTx:
			return Ocpp.IdTagInfo.Status.ConcurrentTx;
		case ServerUnreachable:
			return Ocpp.IdTagInfo.Status.ServerUnreachable;
		default:
			return Ocpp.IdTagInfo.Status.ServerUnreachable;
		}
	}
	
	public Ocpp.BootNotificationResponse.Status bootNotfRespStatusToEncodingProtocol(
							OcppBootNotfResp.RegistrationStatus status) {
		switch(status) {
		case Accepted:
			return Ocpp.BootNotificationResponse.Status.Accepted;
		case Rejected:
			return Ocpp.BootNotificationResponse.Status.Rejected;
		default:
			return Ocpp.BootNotificationResponse.Status.Rejected;
		}
	}
	
	public Ocpp encodeMsg(OcppBootNotfResp resp) {
		Ocpp.Builder ocBuilder = Ocpp.newBuilder();
		ocBuilder.setType(Ocpp.Type.T_BOOT_NOTIFICATION_RESPONSE);
		if (handle != null) {
			ocBuilder.setHandle(handle);
		}
		
		Ocpp.BootNotificationResponse.Builder respBuilder = Ocpp.BootNotificationResponse.newBuilder();
		respBuilder.setCurrentTime(resp.currentTime);
		respBuilder.setHeartbeatInterval(resp.interval);
		respBuilder.setStatus(bootNotfRespStatusToEncodingProtocol(resp.status));
		
		Ocpp.BootNotificationResponse r = respBuilder.build();
		ocBuilder.setBootNotificationResponse(r);
		
		Ocpp oc = ocBuilder.build();
		return oc;
		//return oc.toByteArray();
	}
	
	public Ocpp encodeMsg(OcppStartTransactionResp resp) {
		Ocpp.Builder ocBuilder = Ocpp.newBuilder();
		ocBuilder.setType(Ocpp.Type.T_START_TRANSACTION_RESPONSE);
		if (handle != null) {
			ocBuilder.setHandle(handle);
		}
		
		Ocpp.IdTagInfo.Builder idTagBuilder = Ocpp.IdTagInfo.newBuilder();
		idTagBuilder.setStatus(idtaginfoStatusToEncodingProtocol(resp.getIdTagInfo().getStatus()));
		idTagBuilder.setParentIdTag(resp.getIdTagInfo().getParentIdTag());
		Ocpp.IdTagInfo idTagInfo = idTagBuilder.build();
		
		Ocpp.StartTransactionResponse.Builder respBuilder = Ocpp.StartTransactionResponse.newBuilder();
		respBuilder.setIdTagInfo(idTagInfo);
		respBuilder.setTransactionId(resp.getTransactionId());
		respBuilder.setIdTag(resp.getIdTag());
		
		Ocpp.StartTransactionResponse r = respBuilder.build();
		ocBuilder.setStartTransactionResponse(r);

		Ocpp oc = ocBuilder.build();
		return oc;
		//return oc.toByteArray();
	}
	
	public Ocpp encodeMsg(OcppStopTransactionResp resp) {
		Ocpp.Builder ocBuilder = Ocpp.newBuilder();
		ocBuilder.setType(Ocpp.Type.T_STOP_TRANSACTION_RESPONSE);
		if (handle != null) {
			ocBuilder.setHandle(handle);
		}
		
		Ocpp.IdTagInfo.Builder idTagBuilder = Ocpp.IdTagInfo.newBuilder();
		idTagBuilder.setStatus(idtaginfoStatusToEncodingProtocol(resp.getIdTagInfo().getStatus()));
		idTagBuilder.setParentIdTag(resp.getIdTagInfo().getParentIdTag());
		Ocpp.IdTagInfo idTagInfo = idTagBuilder.build();
		
		Ocpp.StopTransactionResponse.Builder respBuilder = Ocpp.StopTransactionResponse.newBuilder();
		respBuilder.setIdTagInfo(idTagInfo);
		respBuilder.setIdTag(resp.getIdTag());
		
		Ocpp.StopTransactionResponse r = respBuilder.build();
		ocBuilder.setStopTransactionResponse(r);
		
		Ocpp oc = ocBuilder.build();
		return oc;
		//return oc.toByteArray();
	}
	
	public void setConnect(Chat chat) {
		this.chat = chat;
	}
	
	//This is purely for test
	public static String toHex(byte b) {  
        String result = Integer.toHexString(b & 0xFF);  
        if (result.length() == 1) {  
            result = '0' + result;  
        }
    
        return result;  
    }  
	
	public void replyPing() {
		Env.Builder envBuilder = Env.newBuilder();
		envBuilder.setPing(false);
		
		String base64Str = new BASE64Encoder().encode(envBuilder.build().toByteArray());
		try {
			chat.sendMessage(base64Str);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decodeMsg(Chat chat, String str) {
		System.out.println("Get a Message from chat=" + chat);
		byte[] msg = null;
		try {
			msg = new BASE64Decoder().decodeBuffer(str);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("Message not decodable by base64");
			return;
		}
		/*
		System.out.println("This is including envelop:");
		for (int i=0;i<msg.length;i++) {
			System.out.print(toHex(msg[i])+" ");
		}
		System.out.println("\n");
		*/
		
		Env env=null;
		List<Env.Msg> msg_list = null;
		try {
			env = Env.parseFrom(msg);
			msg_list = env.getMessageList();
		} catch (InvalidProtocolBufferException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		if (env.hasTimeout()) {
			System.out.println("timeout="+env.getTimeout());
		}
		*/
		
		if (env.hasPing()) {
			System.out.println("Receive a ping message");
			if (env.getPing() == true) {
				replyPing();
				return;
			}
		}
		int msgNum = env.getMessageCount();
		for (int i = 0; i<msgNum; i++) {
			Env.Msg iMsg = msg_list.get(i);
			byte[] context = iMsg.getContents().toByteArray();
			/*
			System.out.println("Message in envelop");
			for (int j=0; j<context.length; j++)
				System.out.print(toHex(context[j])+" ");
			System.out.println("");
			*/
			try {
				Ocpp ocpp;
				ocpp = Ocpp.parseFrom(context);
				
				if (ocpp.hasHandle()) {
					handle = ocpp.getHandle();
				} else {
					handle = null;
				}
				if (ocpp.hasType() == true) {
					Ocpp ocppMsg = null;
					
					switch(ocpp.getType()) {
					case T_BOOT_NOTIFICATION_REQUEST:
					{
						System.out.println("Receive a boot notification request");
						Ocpp.BootNotificationRequest req = ocpp.getBootNotificationRequest();
						OcppBootNotfReq ocppProtocol = new OcppBootNotfReq(req);
						
						String chargeId = ocppProtocol.chargePointSerialNumber + ocppProtocol.chargeBoxSerialNumber;
						if (chargePointToChat.containsKey(chargeId)) {
							//System.out.println("Charge point " + chargeId + " already registered!");
						} else {
							System.out.println("Will register chargeId="+chargeId+" associated with chat="+chat);
							chargePointToChat.put(chargeId, chat);
							chatToChargepoint.put(chat, chargeId);
							
							ControlData ctrlData = new ControlData();
							ctrlData.transactionId = 0;
							ctrlData.transactionIdPerConnector = new int[ControlData.CONNECTORNUM];
							for (int j=0; j<ctrlData.transactionIdPerConnector.length; j++) {
								ctrlData.transactionIdPerConnector[j] = 0;
							}
							chargepointCtrl.put(chargeId, ctrlData);
						}
						OcppBootNotfResp resp = ocppProtocol.handleMsg();
						ocppMsg = this.encodeMsg(resp);
						/*
						String base64Str = new BASE64Encoder().encode(ocppMsg);
						try {
							chat.sendMessage(base64Str);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						break;
					}
					case T_START_TRANSACTION_REQUEST:
					{
						System.out.println("Receive T_START_TRANSACTION_REQUEST");
						Ocpp.StartTransactionRequest req = ocpp.getStartTransactionRequest();
						OcppStartTransactionReq ocppProtocol = new OcppStartTransactionReq(req);
						
						String chargeId = chatToChargepoint.get(chat);
						if (chargeId == null) {
							//Error
							System.out.println("No chargeId associated with chat="+chat);
							break;
						}
						ControlData ctrlData = chargepointCtrl.get(chargeId);
						if (ctrlData == null) {
							//Error
							System.out.println("No ctrlData associated with chargeId="+chargeId);
							break;
						}
						
						int transactionId = ++ctrlData.transactionId;
						for (int j=0; j<ctrlData.transactionIdPerConnector.length; j++) {
							if (ctrlData.transactionIdPerConnector[j] == -1) {
								System.out.println("Response with transactionId="+transactionId);
								ctrlData.transactionIdPerConnector[j] = ctrlData.transactionId;
							}
						}
						OcppStartTransactionResp resp = ocppProtocol.handleMsg(transactionId);
						ocppMsg = this.encodeMsg(resp);
						/*
						String base64Str = new BASE64Encoder().encode(ocppMsg);
						try {
							chat.sendMessage(base64Str);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						break;
					}
					case T_STOP_TRANSACTION_REQUEST:
					{
						System.out.println("Receive T_STOP_TRANSACTION_REQUEST");
						Ocpp.StopTransactionRequest req = ocpp.getStopTransactionRequest();
						OcppStopTransactionReq ocppProtocol = new OcppStopTransactionReq(req);
						
						OcppStopTransactionResp resp = ocppProtocol.handleMsg();
						ocppMsg = this.encodeMsg(resp);
						/*
						String base64Str = new BASE64Encoder().encode(ocppMsg);
						try {
							chat.sendMessage(base64Str);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						break;
					}	
					case T_REMOTE_STOP_TRANSACTION_RESPONSE:
					{
						System.out.println("Receive T_REMOTE_STOP_TRANSACTION_RESPONSE");
						Ocpp.RemoteStopTransactionResponse ocppResp = ocpp.getRemoteStopTransactionResponse();
						OcppRemoteStopTransactionResp resp = new OcppRemoteStopTransactionResp(ocppResp);
						
						String chargeId = chatToChargepoint.get(chat);
						if (chargeId == null) {
							// Error
							System.out.println("No chargeId associated with chat="+chat);
							break;
						}
						ControlData ctrlStatus = chargepointCtrl.get(chargeId);
						if (ctrlStatus == null) {
							// Error
							System.out.println("No ctrlStatus associated with chargeId="+chargeId);
							break;
						}
						synchronized (ctrlStatus) {
							ctrlStatus.status = resp.status.value();
							System.out.println("Notify remoteStopTransactionReq with status="+ctrlStatus.status);
							ctrlStatus.notify();
						}
						break;
					}
					case T_REMOTE_START_TRANSACTION_RESPONSE:
					{
						System.out.println("Receive T_REMOTE_START_TRANSACTION_RESPONSE");
						Ocpp.RemoteStartTransactionResponse ocppResp = ocpp.getRemoteStartTransactionResponse();
						OcppRemoteStartTransactionResp resp = new OcppRemoteStartTransactionResp(ocppResp);
						
						String chargeId = chatToChargepoint.get(chat);
						if (chargeId == null) {
							// Error
							System.out.println("No chargeId associated with chat="+chat);
							break;
						}
						ControlData ctrlStatus = chargepointCtrl.get(chargeId);
						if (ctrlStatus == null) {
							// Error
							System.out.println("No ControlData associated with chargeId="+chargeId);
							break;
						}
						synchronized (ctrlStatus) {
							ctrlStatus.status = resp.status.value();
							System.out.println("Notify remoteStartTransactionReq with status="+ctrlStatus.status);
							ctrlStatus.notify();
						}
						break;
					}
					case T_HEARTBEAT_REQUEST:
					case T_HEARTBEAT_RESPONSE:
					default:
						break;
					}
					
					if (ocppMsg == null) {
						return;
					}
					
					Env.Msg.Builder msgBuilder = Env.Msg.newBuilder();
					msgBuilder.setContents(ocppMsg.toByteString());
					Env.Builder envBuilder = Env.newBuilder();
					msgBuilder.setService(4);
					envBuilder.addMessage(msgBuilder.build());
					String base64Str = new BASE64Encoder().encode(envBuilder.build().toByteArray());
									/*
					for (int k =0;k<base64Str.length();k++)
						System.out.println(base64Str.charAt(k));
					*/
					
					try {
						System.out.println("Send base64 encoded string back to CP.");
						chat.sendMessage(base64Str);
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendMsg(Chat chat, Ocpp ocppMsg) {
		Env.Msg.Builder msgBuilder = Env.Msg.newBuilder();
		msgBuilder.setContents(ocppMsg.toByteString());
		Env.Builder envBuilder = Env.newBuilder();
		msgBuilder.setService(4);
		envBuilder.addMessage(msgBuilder.build());
		String base64Str = new BASE64Encoder().encode(envBuilder.build().toByteArray());
						/*
		for (int k =0;k<base64Str.length();k++)
			System.out.println(base64Str.charAt(k));
		*/
		
		try {
			chat.sendMessage(base64Str);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Send base64encoded string to CP");
	}
	
	public Ocpp encodeMsg(OcppRemoteStartTransactionReq req) {
		Ocpp.Builder ocBuilder = Ocpp.newBuilder();
		ocBuilder.setType(Ocpp.Type.T_REMOTE_START_TRANSACTION_REQUEST);
		
		Ocpp.RemoteStartTransactionRequest.Builder reqBuilder = Ocpp.RemoteStartTransactionRequest.newBuilder();
		reqBuilder.setConnectorId(req.getConnectorId());
		reqBuilder.setIdTag(req.getIdTag());
		Ocpp.RemoteStartTransactionRequest request = reqBuilder.build();
		ocBuilder.setRemoteStartTransactionRequest(request);
		
		Ocpp oc = ocBuilder.build();
		return oc;
		//return oc.toByteArray();
	}
	
	public OcppRemoteStartStopStatus.Status handleRemoteStartTransaction(
			Chat chat, int connectorId, String idTag) {
		System.out.println("handleRemoteStartTransaction chat="+chat);
		
		OcppRemoteStartTransactionReq req = new OcppRemoteStartTransactionReq();
		req.setConnectorId(connectorId);
		req.setIdTag(idTag);
		
		Ocpp ocppMsg = encodeMsg(req);
		sendMsg(chat, ocppMsg);
		System.out.println("Send base64encoded string to CP from handleRemoteStartTransaction");
		
		String chargeId = chatToChargepoint.get(chat);
		if (chargeId == null) {
			System.out.println("chargeId is null associated with chat="+chat+" in handleRemoteStartTransaction");
			// Error
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		ControlData ctrlData = chargepointCtrl.get(chargeId);
		if (ctrlData == null) {
			System.out.println("ctrlData is null associated with chargeId="+chargeId+" in handleRemoteStartTransaction");
			// Error
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		if (connectorId>=ControlData.CONNECTORNUM) {
			System.out.println("connectorId="+connectorId+" larger than "+ControlData.CONNECTORNUM);
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		// Wait for asynchronous response
		synchronized (ctrlData) {
			try {
				ctrlData.transactionIdPerConnector[connectorId] = -1;
				ctrlData.wait(TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("No response of RemoteStartTransaction for "+TIMEOUT/1000+" seconds");
				ctrlData.transactionIdPerConnector[connectorId] = 0;
				return OcppRemoteStartStopStatus.Status.Rejected;
			}
		}

		System.out.println("Return "+ctrlData.status+" from handleRemoteStartTransaction");
		return OcppRemoteStartStopStatus.Status.valueOf(ctrlData.status);
	}
	
	public Ocpp encodeMsg(OcppRemoteStopTransactionReq req) {
		Ocpp.Builder ocBuilder = Ocpp.newBuilder();
		ocBuilder.setType(Ocpp.Type.T_REMOTE_STOP_TRANSACTION_REQUEST);
		
		Ocpp.RemoteStopTransactionRequest.Builder reqBuilder = Ocpp.RemoteStopTransactionRequest.newBuilder();
		reqBuilder.setTransactionId(req.getTransactionId());
		Ocpp.RemoteStopTransactionRequest request = reqBuilder.build();
		ocBuilder.setRemoteStopTransactionRequest(request);
		
		Ocpp oc = ocBuilder.build();
		return oc;
		//return oc.toByteArray();
	}
	
	public OcppRemoteStartStopStatus.Status handleRemoteStopTransaction(
						Chat chat, int connectorId) {
		System.out.println("handleRemoteStopTransaction chat="+chat);
		String chargeId = chatToChargepoint.get(chat);
		if (chargeId == null) {
			System.out.println("No chargeId associated with chat="+chat);
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		
		ControlData ctrlData = chargepointCtrl.get(chargeId);
		if (ctrlData == null) {
			System.out.println("No ctrlData associated with chargeId="+chargeId);
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		
		int transactionId = ctrlData.transactionIdPerConnector[connectorId];
		if (transactionId<=0) {
			System.out.println("transactionId="+transactionId+" is not valid");
			return OcppRemoteStartStopStatus.Status.Rejected;
		}
		
		OcppRemoteStopTransactionReq req = new OcppRemoteStopTransactionReq();
		req.setTransactionId(transactionId);
		
		Ocpp ocppMsg = encodeMsg(req);
		sendMsg(chat, ocppMsg);
		System.out.println("Send RemoteStopTransaction to CP");
		
		// Wait for asynchronous response
		synchronized (ctrlData) {
			try {
				ctrlData.wait(TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("No response of RemoteStopTransaction for "+TIMEOUT/1000+" seconds");
				return OcppRemoteStartStopStatus.Status.Rejected;
			}
		}

		return OcppRemoteStartStopStatus.Status.valueOf(ctrlData.status);
	}
}
