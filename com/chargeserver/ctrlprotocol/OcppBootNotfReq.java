package com.chargeserver.ctrlprotocol;

import java.util.Date;

import com.chargeserver.ocpp.Contents.Ocpp;

public class OcppBootNotfReq {
	public String chargeBoxSerialNumber;
	public String chargePointModel;
	public String chargePointSerialNumber;
	public String chargePointVendor;
	public String firmwareVersion;
	public String iccid;
	public String imsi;
	public String meterSerialNumber;
	public String meterType;
	
	public OcppBootNotfReq(Ocpp.BootNotificationRequest req) {
		if (req.hasChargeBoxSerialNumber()) {
			chargeBoxSerialNumber = req.getChargeBoxSerialNumber();
		}
		if (req.hasChargePointModel()) {
			chargePointModel = req.getChargePointModel();
		}
		if (req.hasChargePointSerialNumber()) {
			chargePointSerialNumber = req.getChargePointSerialNumber();
		}
		if (req.hasChargePointVendor()) {
			chargePointVendor = req.getChargePointVendor();
		}
		if (req.hasFirmwareVersion()) {
			firmwareVersion = req.getFirmwareVersion();
		}
		if (req.hasIccid()) {
			iccid = req.getIccid();
		}
		if (req.hasImsi()) {
			imsi = req.getImsi();
		}
		if (req.hasMeterSerialNumber()) {
			meterSerialNumber = req.getMeterSerialNumber();
		}
		if (req.hasMeterType()) {
			meterType = req.getMeterType();
		}
	}
	
	public OcppBootNotfResp handleMsg() {
		OcppBootNotfResp resp = new OcppBootNotfResp();
		long currDate = new Date().getTime();
		int currTime = new Long(currDate).intValue();
		resp.setCurrentTime(currTime);
		resp.setInterval(30);
		resp.setStatus(OcppBootNotfResp.RegistrationStatus.Accepted);
		
		return resp;
	}
	
	public String toString() {
		return String.format("OcppBootNotfReq: [chargeBoxSerialNumber = %s, chargePointModel = %s, chargePointSerialNumber = %s, chargePointVendor = %s, firmwareVersion = %s, iccid = %s, imsi = %s, meterSerialNumber = %s, meterType = %s]",
				chargeBoxSerialNumber, chargePointModel, chargePointSerialNumber, chargePointVendor, firmwareVersion, iccid, imsi, meterSerialNumber, meterType);
	}
}
