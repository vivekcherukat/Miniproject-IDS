package miniproject.gui.controller;

import miniproject.SystemManager;
import miniproject.video.VideoAnalyser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class ConfigurationWindowController {
	@FXML TextField videoStorageLocationField;
	@FXML TextField imageStorageLocationField;
	@FXML CheckBox alertsEnabledCheckBox;
	@FXML CheckBox uploadEnabledCheckBox;
	@FXML CheckBox emailEnabledCheckBox;
	@FXML CheckBox smsEnabledCheckBox;
	@FXML TextField alertIntervalField;
	@FXML TextField smsAlertIntervalField;

	@FXML Slider motionDetectionRateSlider;
	@FXML Label motionDetectionRateLabel;
	@FXML ChoiceBox<String> thresholdMethodChoiceBox;
	@FXML ChoiceBox<String> contourDetectionMethodChoiceBox;
	@FXML TextField adaptiveThresholdRectSizeXField;
	@FXML TextField adaptiveThresholdRectSizeYField;
	@FXML CheckBox highLightMovingObjectsChoiceBox;
	@FXML TextField movingObjectHighlightBoxWidthField;
	
	@FXML TextField emailToField;
	@FXML TextField emailFromField;
	@FXML TextField hostNameField;
	@FXML TextField portField;
	@FXML TextField userNameField;
	@FXML TextField passwordField;
	
	@FXML TextField toPhoneNumberField;
	@FXML TextField messageServiceCenterNumberField;
	@FXML TextField smsCustomAlertTextField;
	@FXML TextField modemNameField;
	@FXML TextField modemPortField;
	@FXML TextField modemPinField;
	@FXML TextField modemBitrateField;
	
	@FXML TextField uploadIntervalMinsField;
	@FXML TextField appKeyField;
	@FXML TextField appSecretField; 
	@FXML TextField accessTokenField;
	
	@FXML TextField recordTimeContSecsField;
	@FXML TextField recordTimeDetectionSecsField;
	@FXML TextField recordingWidthField;
	@FXML TextField recordingHeightField;
	@FXML TextField recordingFrameRateField;
	
	@FXML public void initialize(){
		videoStorageLocationField.setText(SystemManager.configuration.getVideoStorageLocation());
		imageStorageLocationField.setText(SystemManager.configuration.getImageStorageLocation());
		alertsEnabledCheckBox.setSelected(SystemManager.configuration.isAlertEnabled());
		uploadEnabledCheckBox.setSelected(SystemManager.configuration.isUploadEnabled());
		emailEnabledCheckBox.setSelected(SystemManager.configuration.isEmailEnabled());
		smsEnabledCheckBox.setSelected(SystemManager.configuration.isSmsEnabled());
		alertIntervalField.setText(String.valueOf(SystemManager.configuration.getAlertIntervalMilliSecs()));
		smsAlertIntervalField.setText(String.valueOf(SystemManager.configuration.getNextSmsAlertIntervalMins()));
		
		motionDetectionRateSlider.setMin(10);
		motionDetectionRateSlider.setMax(100);
		motionDetectionRateSlider.setValue(SystemManager.configuration.getMotionDetectionRate());
		motionDetectionRateSlider.setShowTickLabels(true);
		motionDetectionRateSlider.setShowTickMarks(true);
		motionDetectionRateSlider.setMajorTickUnit(50);
		motionDetectionRateSlider.setMinorTickCount(5);
		motionDetectionRateSlider.setBlockIncrement(10);
		
		motionDetectionRateLabel.setText(String.valueOf(SystemManager.configuration.getMotionDetectionRate()));
		
		thresholdMethodChoiceBox.setItems(FXCollections.observableArrayList("Adaptive Gaussian","Adaptive Mean"));
		
		if(SystemManager.configuration.getThresholdMethod() == VideoAnalyser.THRESHOLD_TYPE_GUASSIAN){
			thresholdMethodChoiceBox.setValue("Adaptive Gaussian");
			
		}
		else if(SystemManager.configuration.getThresholdMethod() == VideoAnalyser.THRESHOLD_TYPE_MEAN){
			thresholdMethodChoiceBox.setValue("Adaptive Mean");
		}
		
		contourDetectionMethodChoiceBox.setItems(FXCollections.observableArrayList("Simple Chain Approximation","TC89 KCOS Chain Approximation"));
		
		if(SystemManager.configuration.getThresholdMethod() == VideoAnalyser.CONTOUR_DETECTION_TYPE_SIMPLE){
			contourDetectionMethodChoiceBox.setValue("Simple Chain Approximation");
			
		}
		else if(SystemManager.configuration.getThresholdMethod() == VideoAnalyser.CONTOUR_DETECTION_TYPE_TC89_KC0S){
			contourDetectionMethodChoiceBox.setValue("TC89 KCOS Chain Approximation");
		}
		
		adaptiveThresholdRectSizeXField.setText(String.valueOf(SystemManager.configuration.getAdaptiveThresholdRectSizeX()));
		adaptiveThresholdRectSizeYField.setText(String.valueOf(SystemManager.configuration.getAdaptiveThresholdRectSizeY()));
		highLightMovingObjectsChoiceBox.setSelected(SystemManager.configuration.isHighLightMovingObjects());
		movingObjectHighlightBoxWidthField.setText(String.valueOf(SystemManager.configuration.getMovingObjectHighlightBoxWidth()));
		
		emailToField.setText(SystemManager.configuration.getEmailTo());
		emailFromField.setText(SystemManager.configuration.getEmailfrom());
		hostNameField.setText(SystemManager.configuration.getHostName());
		portField.setText(SystemManager.configuration.getPort());
		userNameField.setText(SystemManager.configuration.getUsername());
		passwordField.setText(SystemManager.configuration.getPassword());
		
		toPhoneNumberField.setText(SystemManager.configuration.getToPhoneNumber());
		messageServiceCenterNumberField.setText(SystemManager.configuration.getMessageServiceCenterNumber());
		smsCustomAlertTextField.setText(SystemManager.configuration.getSmsCustomeAlertText());
		modemNameField.setText(SystemManager.configuration.getModemName());
		modemPortField.setText(SystemManager.configuration.getModemPort());
		modemPinField.setText(SystemManager.configuration.getModemPIN());
		modemBitrateField.setText(String.valueOf(SystemManager.configuration.getModemBitRate()));
		
		
		uploadIntervalMinsField.setText(String.valueOf(SystemManager.configuration.getUploadIntervalMins()));
		appKeyField.setText(SystemManager.configuration.getAppKey());
		appSecretField.setText(SystemManager.configuration.getAppSecret());
		accessTokenField.setText(SystemManager.configuration.getAccessToken());
		
		recordTimeContSecsField.setText(String.valueOf(SystemManager.configuration.getRecordTimeContSecs()));
		recordTimeDetectionSecsField.setText(String.valueOf(SystemManager.configuration.getRecordTimeDetectionSecs()));
		recordingWidthField.setText(String.valueOf(SystemManager.configuration.getRecordingWidth()));
		recordingHeightField.setText(String.valueOf(SystemManager.configuration.getRecordingHeight()));
		recordingFrameRateField.setText(String.valueOf(SystemManager.configuration.getRecordingFrameRate()));
		
	}
	
	@FXML public void handleSaveButtonAction(ActionEvent clickEvent){
		System.out.println("GUI: Button Action - Save");
		
		SystemManager.configuration.setVideoStorageLocation(videoStorageLocationField.getText());
		SystemManager.configuration.setImageStorageLocation(imageStorageLocationField.getText());
		SystemManager.configuration.setAlertEnabled(alertsEnabledCheckBox.isSelected());
		SystemManager.configuration.setUploadEnabled(uploadEnabledCheckBox.isSelected());
		SystemManager.configuration.setEmailEnabled(emailEnabledCheckBox.isSelected());
		SystemManager.configuration.setSmsEnabled(smsEnabledCheckBox.isSelected());
		SystemManager.configuration.setAlertIntervalMilliSecs(Integer.valueOf(alertIntervalField.getText()));
		SystemManager.configuration.setNextSmsAlertIntervalMins(Integer.valueOf(smsAlertIntervalField.getText()));
		
		SystemManager.configuration.setMotionDetectionRate((int) motionDetectionRateSlider.getValue());
//		TODO Change label when slider is moved
		SystemManager.configuration.setThresholdMethod(thresholdMethodChoiceBox.getSelectionModel().getSelectedIndex());
		SystemManager.configuration.setContourDetectionMethod(contourDetectionMethodChoiceBox.getSelectionModel().getSelectedIndex());
		SystemManager.configuration.setAdaptiveThresholdRectSizeX(Integer.valueOf(adaptiveThresholdRectSizeXField.getText()));
		SystemManager.configuration.setAdaptiveThresholdRectSizeY(Integer.valueOf(adaptiveThresholdRectSizeYField.getText()));
		SystemManager.configuration.setHighLightMovingObjects(highLightMovingObjectsChoiceBox.isSelected());
		SystemManager.configuration.setMovingObjectHighlightBoxWidth(Integer.valueOf(movingObjectHighlightBoxWidthField.getText()));
		
		SystemManager.configuration.setEmailTo(emailToField.getText());
		SystemManager.configuration.setEmailfrom(emailFromField.getText());
		SystemManager.configuration.setHostName(hostNameField.getText());
		SystemManager.configuration.setPort(portField.getText());
		SystemManager.configuration.setUsername(userNameField.getText());
		SystemManager.configuration.setPassword(passwordField.getText());
		
		SystemManager.configuration.setToPhoneNumber(toPhoneNumberField.getText());
		SystemManager.configuration.setMessageServiceCenterNumber(messageServiceCenterNumberField.getText());
		SystemManager.configuration.setSmsCustomAlertText(smsCustomAlertTextField.getText());//
		SystemManager.configuration.setModemName(modemNameField.getText());
		SystemManager.configuration.setModemPort(modemPortField.getText());
		SystemManager.configuration.setModemPIN(modemPinField.getText());
		SystemManager.configuration.setModemBitRate(Integer.valueOf(modemBitrateField.getText()));
		
		SystemManager.configuration.setUploadIntervalMins(Integer.valueOf(uploadIntervalMinsField.getText()));
		SystemManager.configuration.setAppKey(appKeyField.getText());
		SystemManager.configuration.setAppSecret(appSecretField.getText());
		SystemManager.configuration.setAccessToken(accessTokenField.getText());
		
		SystemManager.configuration.setRecordTimeContSecs(Integer.valueOf(recordTimeContSecsField.getText()));
		SystemManager.configuration.setRecordTimeDetectionSecs(Integer.valueOf(recordTimeDetectionSecsField.getText()));
		SystemManager.configuration.setRecordingWidth(Integer.valueOf(recordingWidthField.getText()));
		SystemManager.configuration.setRecordingHeight(Integer.valueOf(recordingHeightField.getText()));
		SystemManager.configuration.setRecordingFrameRate(Integer.valueOf(recordingFrameRateField.getText()));
		
		SystemManager.writeConfiguration();
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();

	}
	@FXML public void handleCancelButtonAction(ActionEvent clickEvent){
		System.out.println("GUI: Button Action - Cancel");
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();
	}
	@FXML public void handleMotionDetectionRateSliderMoveAction(){
		motionDetectionRateLabel.setText(String.valueOf(((int)motionDetectionRateSlider.getValue())));
	}
	
	
}
