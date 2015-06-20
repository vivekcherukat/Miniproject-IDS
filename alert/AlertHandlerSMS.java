package miniproject.alert;

import miniproject.SystemManager;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
//import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

//import SMS.OutboundNotification;

public class AlertHandlerSMS implements Runnable {
	
//	TODO Load from configuration
	
	String smsAlertPhoneNumber = SystemManager.configuration.getToPhoneNumber();
	String modemPort = SystemManager.configuration.getModemPort();;
	String modemPIN = SystemManager.configuration.getModemPIN();;
	int modemBitRate = SystemManager.configuration.getModemBitRate();;
	String modemName = SystemManager.configuration.getModemName();
	String smsServiceCenterNum = SystemManager.configuration.getMessageServiceCenterNumber();
	String smsAlertText = SystemManager.configuration.getSmsCustomeAlertText();
	
	public void sendTextMessage() throws Exception {
//		OutboundNotification outboundNotification = new OutboundNotification();
//		System.out.println("Example: Send message from a serial gsm modem.");
//		System.out.println(Library.getLibraryDescription());
//		System.out.println("Version: " + Library.getLibraryVersion());
		SerialModemGateway gateway = new SerialModemGateway("Modem",
				modemPort, modemBitRate, modemName, "");
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin(modemPIN);
		
		gateway.setSmscNumber(smsServiceCenterNum);
		/*Service.getInstance().setOutboundMessageNotification(
				outboundNotification);*/
		Service.getInstance().addGateway(gateway);
		Service.getInstance().startService();
		System.out.println("\nAlert Handler SMS:");
		System.out.println("Modem Information:");
		System.out.println("  Manufacturer: " + gateway.getManufacturer());
		System.out.println("  Model: " + gateway.getModel());
		System.out.println("  Serial No: " + gateway.getSerialNo());
		System.out.println("  SIM IMSI: " + gateway.getImsi());
		System.out.println("  Signal Level: " + gateway.getSignalLevel()
				+ " dBm");
		System.out.println("  Battery Level: " + gateway.getBatteryLevel()
				+ "%");
		System.out.println();
		// Send a message synchronously.
		OutboundMessage msg = new OutboundMessage(smsAlertPhoneNumber,
				smsAlertText);
		Service.getInstance().sendMessage(msg);
		System.out.println(msg);
		
//		System.out.println("Now Sleeping - Hit <enter> to terminate.");
//		System.in.read();
		Service.getInstance().stopService();
	}

	public class OutboundNotification implements IOutboundMessageNotification {
		public void process(AGateway gateway, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: "
					+ gateway.getGatewayId());
			System.out.println(msg);
		}
	}

	public void run() {
		try {
			sendTextMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Alert Handler SMS: SMS alert sent");

	}

}
