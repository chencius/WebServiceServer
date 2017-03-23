package com.chargeserver.server;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.chargeserver.encodingprotocol.ProtoBuf;

public class Server implements Runnable {
	public static Server server = null;
	private XMPPConnection connection;
	public ProtoBuf encProtocol;
	private String serverIp;
	private String username;
	private String password;
	
	public synchronized static Server getInstance() {
		if (server == null) {
			server = new Server();
			new Thread(server).start();
		}
		return server;
	}
	
	public Server() {
		this.serverIp = "120.26.141.247";
		this.username = "billing";
		this.password = "Billing";
	
		try {
			connection = new XMPPConnection(serverIp);
			connection.connect();
			connection.login(username, password);
			System.out.println("Server started");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encProtocol = new ProtoBuf();
		MessageListener msgListener = new MessageListener() {

			@Override
			public void processMessage(Chat chat, Message message) {
				String msg = message.getBody();
				if (msg != null) {				
					encProtocol.decodeMsg(chat, msg);
				}
			}
			
		};
		
		ChatManagerListener chatListener = new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				chat.addMessageListener(msgListener);
			}
			
		};
		
		connection.getChatManager().addChatListener(chatListener);
		//while(true) {
			
		//}
	}
	public void run() {
		while (true) {
			
		}
	}
}
