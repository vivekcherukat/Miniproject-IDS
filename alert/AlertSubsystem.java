package miniproject.alert;

import miniproject.SystemManager;
import miniproject.video.VideoAnalyser;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class AlertSubsystem implements Runnable {

	public static final int ALERT_TYPE_EMAIL = 0;
	public static final int ALERT_TYPE_PUSH = 1;
	public static final int ALERT_TYPE_SMS = 2;

	public boolean exitStatus = false;
	boolean[] alertPrefs = new boolean[3];
	Calendar currentTime = null;
	Calendar nextAlertTime = Calendar.getInstance();
	int nextAlertIntervalmilliSecs = SystemManager.configuration.getAlertIntervalMilliSecs();
	int nextSMSAlertIntervalMins = SystemManager.configuration.getNextSmsAlertIntervalMins();
//	AlertItem alert = new AlertItem(0, null, null, null);
	// public static Queue<AlertItem> alertServiceList = new
	// LinkedList<AlertItem>();

	Queue<AlertItem> alertQueueClone = null;

	public void terminateThread() {
		exitStatus = true;
		System.out.println("Alert Subsystem: Terminating alert thread");
	}

	void loadAlertPrefs() {
//		Load these configurations from settings manager
		alertPrefs[ALERT_TYPE_EMAIL] = SystemManager.configuration.isEmailEnabled();
//		Push not implemented		
		alertPrefs[ALERT_TYPE_PUSH] = false;
		alertPrefs[ALERT_TYPE_SMS] = SystemManager.configuration.isSmsEnabled();
	}

	public void createAlert() {
		
		loadAlertPrefs();
		currentTime = Calendar.getInstance();

		// If enabled, try to send an email alert
		if (alertPrefs[ALERT_TYPE_EMAIL] == true) {
			try {

				AlertHandlerEmail.mailAlert(alertQueueClone);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (alertPrefs[ALERT_TYPE_PUSH] == true) {

		}
		if (alertPrefs[ALERT_TYPE_SMS] == true) {
			if (currentTime.equals(nextAlertTime)
					|| currentTime.after(nextAlertTime)) {
				
				AlertHandlerSMS smsAlert = new AlertHandlerSMS();
				Thread smsAlertThread = new Thread(smsAlert);
				smsAlertThread.start();

				// Add time interval to wait before sending the next SMS alert
				nextAlertTime.add(Calendar.MINUTE, nextSMSAlertIntervalMins);
			}

		}
		//Remove all items from the current queue
		VideoAnalyser.alertQueue.clear();

	}

	public void run() {
		System.out.println("Alert Subsystem: Initialising ...");
		while (exitStatus == false) {
			System.out.println("Alert Subsystem: Initialised, waiting for alert token...");
			try {
				SystemManager.alertCount.acquire(VideoAnalyser.semaphoreCount);
				VideoAnalyser.semaphoreCount = 1;
			} catch (Exception e) {
			}

			try {
				SystemManager.threadMutex.acquire();
			} catch (InterruptedException e1) {
			}
			alertQueueClone = new LinkedList<AlertItem>(
					VideoAnalyser.alertQueue);
			VideoAnalyser.alertQueue.clear();
			SystemManager.threadMutex.release();
			createAlert();
			// TODO Write alert to file

			System.out.println("Alert Subsystem: Sleeping");
			try {
				Thread.sleep(nextAlertIntervalmilliSecs);
			} catch (InterruptedException e) {

				// e.printStackTrace();
			}

		}
	}
}
