package miniproject.config;

import miniproject.video.VideoAnalyser;
import miniproject.video.VideoWriter;

public class Configuration {
	// System

	String videoStorageLocation;
	String imageStorageLocation;
	boolean uploadEnabled;
	boolean alertEnabled;

	// Alert Subsystem

	boolean emailEnabled;
	boolean smsEnabled;
	int alertIntervalMilliSecs;
	int nextSmsAlertIntervalMins;

	// Alert Subsystem - Email

	private String emailTo;
	String emailfrom;
	String hostName;
	String port;
	String username;
	String password;
	String customAlertText;

	// Alert Subsystem - SMS

	String toPhoneNumber;
	String messageServiceCenterNumber;
	String modemPort;
	String modemPIN;
	int modemBitRate;
	String modemName;
	String smsCustomAlertText;

	// Video Upload

	int uploadIntervalMins;
	String appKey;
	String appSecret;
	String accessToken;

	// Video Analysis

	int motionDetectionRate;
	int thresholdMethod;
	boolean highLightMovingObjects;
	int movingObjectHighlightBoxWidth;
	String imageStorageFormat;
	int contourDetectionMethod;
	int adaptiveThresholdRectSizeX;
	int adaptiveThresholdRectSizeY;

	// Video Recording
	
	int recordingType;
	int recordTimeContSecs;
	int recordTimeDetectionSecs;
	int recordingWidth;
	int recordingHeight;
	int recordingFrameRate;
	
	public Configuration() {
		/* This is the default configuration for the system */
		this.videoStorageLocation = "F:\\MiniProject\\storage\\videos";
		this.imageStorageLocation = "F:\\MiniProject\\storage\\images";
		this.uploadEnabled = false;
		this.alertEnabled = false;

		// Alert Subsystem

		this.emailEnabled = true;
		this.smsEnabled = false;
		this.alertIntervalMilliSecs = 10000;
		this.nextSmsAlertIntervalMins = 10;

		// Alert Subsystem - Email

		this.emailTo = new String("abhilashb1289@gmail.com");
		this.emailfrom = "motiondetectiontest@gmail.com";
		this.hostName = "smtp.gmail.com";
		this.port = "587";
		this.username = "motiondetectiontest";
		this.password = "asusmon565#";
		this.customAlertText = "Intrusion Detected";

		// Alert Subsystem - SMS

		this.toPhoneNumber = "+919747053717";
		this.messageServiceCenterNumber = "+919440099997";
		this.modemPort = "COM5";
		this.modemPIN = "0000";
		this.modemBitRate = 921600;
		this.modemName = "Huawei";
		this.smsCustomAlertText = "Intrusion Detected! kokachi555#";

		// Video Upload

		this.uploadIntervalMins = 1;
		this.appKey = "qooibrztj9echhy";
		this.appSecret = "puqm0o8om5sj2ny";
		this.accessToken = "pHKslOPetuAAAAAAAAAAE8lvEm1ozHAuJOe5JPD3ge6oB-3XXmdPYP-LEWfOWCNJ";

		// Video Analysis

		this.motionDetectionRate = 50;
		this.thresholdMethod = VideoAnalyser.THRESHOLD_TYPE_GUASSIAN;
		this.highLightMovingObjects = true;
		this.movingObjectHighlightBoxWidth = 1;
		this.imageStorageFormat = ".jpg";
		this.contourDetectionMethod = VideoAnalyser.CONTOUR_DETECTION_TYPE_SIMPLE;
		this.adaptiveThresholdRectSizeX = 5;
		this.adaptiveThresholdRectSizeY = 2;
		
		// Video Recording
		
		this.recordingType = VideoWriter.RECORD_TYPE_DETECTION;
		this.recordTimeContSecs = 120;
		this.recordTimeDetectionSecs = 60;
		this.recordingWidth = 640;
		this.recordingHeight = 480;
		this.recordingFrameRate = 50;
		
		
	}

	public String getVideoStorageLocation() {
		return videoStorageLocation;
	}

	public void setVideoStorageLocation(String videoStorageLocation) {
		this.videoStorageLocation = videoStorageLocation;
	}

	public String getImageStorageLocation() {
		return imageStorageLocation;
	}

	public void setImageStorageLocation(String imageStorageLocation) {
		this.imageStorageLocation = imageStorageLocation;
	}

	public boolean isUploadEnabled() {
		return uploadEnabled;
	}

	public void setUploadEnabled(boolean uploadEnabled) {
		this.uploadEnabled = uploadEnabled;
	}

	public boolean isAlertEnabled() {
		return alertEnabled;
	}

	public void setAlertEnabled(boolean alertEnabled) {
		this.alertEnabled = alertEnabled;
	}

	public boolean isEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public boolean isSmsEnabled() {
		return smsEnabled;
	}

	public void setSmsEnabled(boolean smsEnabled) {
		this.smsEnabled = smsEnabled;
	}

	public int getAlertIntervalMilliSecs() {
		return alertIntervalMilliSecs;
	}

	public void setAlertIntervalMilliSecs(int alertIntervalMilliSecs) {
		this.alertIntervalMilliSecs = alertIntervalMilliSecs;
	}

	public int getNextSmsAlertIntervalMins() {
		return nextSmsAlertIntervalMins;
	}

	public void setNextSmsAlertIntervalMins(int nextSmsAlertIntervalMins) {
		this.nextSmsAlertIntervalMins = nextSmsAlertIntervalMins;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailfrom() {
		return emailfrom;
	}

	public void setEmailfrom(String emailfrom) {
		this.emailfrom = emailfrom;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustomAlertText() {
		return customAlertText;
	}

	public void setCustomAlertText(String customAlertText) {
		this.customAlertText = customAlertText;
	}

	public String getToPhoneNumber() {
		return toPhoneNumber;
	}

	public void setToPhoneNumber(String toPhoneNumber) {
		this.toPhoneNumber = toPhoneNumber;
	}

	public String getMessageServiceCenterNumber() {
		return messageServiceCenterNumber;
	}

	public void setMessageServiceCenterNumber(String messageServiceCenterNumber) {
		this.messageServiceCenterNumber = messageServiceCenterNumber;
	}

	public String getModemPort() {
		return modemPort;
	}

	public void setModemPort(String modemPort) {
		this.modemPort = modemPort;
	}

	public String getModemPIN() {
		return modemPIN;
	}

	public void setModemPIN(String modemPIN) {
		this.modemPIN = modemPIN;
	}

	public int getModemBitRate() {
		return modemBitRate;
	}

	public void setModemBitRate(int modemBitRate) {
		this.modemBitRate = modemBitRate;
	}

	public String getModemName() {
		return modemName;
	}

	public void setModemName(String modemName) {
		this.modemName = modemName;
	}

	public String getSmsCustomeAlertText() {
		return smsCustomAlertText;
	}

	public void setSmsCustomeAlertText(String customeAlertText) {
		this.smsCustomAlertText = customeAlertText;
	}

	public int getUploadIntervalMins() {
		return uploadIntervalMins;
	}

	public void setUploadIntervalMins(int uploadIntervalMins) {
		this.uploadIntervalMins = uploadIntervalMins;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getMotionDetectionRate() {
		return motionDetectionRate;
	}

	public void setMotionDetectionRate(int motionDetectionRate) {
		this.motionDetectionRate = motionDetectionRate;
	}

	public int getThresholdMethod() {
		return thresholdMethod;
	}

	public void setThresholdMethod(int thresholdMethod) {
		this.thresholdMethod = thresholdMethod;
	}

	public boolean isHighLightMovingObjects() {
		return highLightMovingObjects;
	}

	public void setHighLightMovingObjects(boolean highLightMovingObjects) {
		this.highLightMovingObjects = highLightMovingObjects;
	}

	public int getMovingObjectHighlightBoxWidth() {
		return movingObjectHighlightBoxWidth;
	}

	public void setMovingObjectHighlightBoxWidth(
			int movingObjectHighlightBoxWidth) {
		this.movingObjectHighlightBoxWidth = movingObjectHighlightBoxWidth;
	}

	public String getImageStorageFormat() {
		return imageStorageFormat;
	}

	public void setImageStorageFormat(String imageStorageFormat) {
		this.imageStorageFormat = imageStorageFormat;
	}

	public int getContourDetectionMethod() {
		return contourDetectionMethod;
	}

	public void setContourDetectionMethod(int contourDetectionMethod) {
		this.contourDetectionMethod = contourDetectionMethod;
	}

	public int getAdaptiveThresholdRectSizeX() {
		return adaptiveThresholdRectSizeX;
	}

	public void setAdaptiveThresholdRectSizeX(int adaptiveThresholdRectSizeX) {
		this.adaptiveThresholdRectSizeX = adaptiveThresholdRectSizeX;
	}

	public int getAdaptiveThresholdRectSizeY() {
		return adaptiveThresholdRectSizeY;
	}

	public void setAdaptiveThresholdRectSizeY(int adaptiveThresholdRectSizeY) {
		this.adaptiveThresholdRectSizeY = adaptiveThresholdRectSizeY;
	}

	public String getSmsCustomAlertText() {
		return smsCustomAlertText;
	}

	public void setSmsCustomAlertText(String smsCustomAlertText) {
		this.smsCustomAlertText = smsCustomAlertText;
	}

	public int getRecordingType() {
		return recordingType;
	}

	public void setRecordingType(int recordingType) {
		this.recordingType = recordingType;
	}

	public int getRecordTimeContSecs() {
		return recordTimeContSecs;
	}

	public void setRecordTimeContSecs(int recordTimeContSecs) {
		this.recordTimeContSecs = recordTimeContSecs;
	}

	public int getRecordTimeDetectionSecs() {
		return recordTimeDetectionSecs;
	}

	public void setRecordTimeDetectionSecs(int recordTimeDetectionSecs) {
		this.recordTimeDetectionSecs = recordTimeDetectionSecs;
	}

	public int getRecordingWidth() {
		return recordingWidth;
	}

	public void setRecordingWidth(int recordingWidth) {
		this.recordingWidth = recordingWidth;
	}

	public int getRecordingHeight() {
		return recordingHeight;
	}

	public void setRecordingHeight(int recordingHeight) {
		this.recordingHeight = recordingHeight;
	}

	public int getRecordingFrameRate() {
		return recordingFrameRate;
	}

	public void setRecordingFrameRate(int recordingFrameRate) {
		this.recordingFrameRate = recordingFrameRate;
	}

}
