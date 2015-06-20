package miniproject.gui.controller;

import miniproject.gui.SystemManagerGUI;
import miniproject.gui.model.CameraTableItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ConfigureCameraController {
	@FXML TextField cameraNameField;
	@FXML TextField cameraIdField;
	@FXML ChoiceBox<String> cameraModeChoiceBox;
	@FXML Button deleteButton;
	int cameraTableEditCandidateId;
	
	@FXML public void initialize(){
		cameraTableEditCandidateId = SystemManagerGUI.getInstance().getCameraTableEditCandidateId();
		
		CameraTableItem cameraListItem = SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId);
		
		cameraNameField.setText(cameraListItem.getCameraName());
		
		cameraIdField.setText(cameraListItem.getCameraId());
		
		cameraModeChoiceBox.setItems(FXCollections.observableArrayList("Continuous","Detection Triggered"));
		
		cameraModeChoiceBox.setValue(cameraListItem.getCameraMode());
		
		if(SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).isCameraOnline())
			deleteButton.setDisable(true);
//		cameraNameField.setText(SystemManagerGUI.getInstance().);
	}
	
	@FXML public void handleSaveButtonAction(ActionEvent clickEvent){
		cameraTableEditCandidateId = SystemManagerGUI.getInstance().getCameraTableEditCandidateId();
		String cameraName = cameraNameField.getText();
		cameraNameField.clear();
		String cameraId = cameraIdField.getText();
		cameraIdField.clear();
		int cameraMode = cameraModeChoiceBox.getSelectionModel().getSelectedIndex();
		SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).setCameraId(cameraId);
		SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).setCameraIdInt(Integer.valueOf(cameraId));
		SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).setCameraName(cameraName);
		SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).setCameraMode(cameraMode);
//		SystemManagerGUI.getInstance().cameraList.get(cameraTableEditCandidateId).setCameraModeInt(cameraMode);
		
		
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();

		
	}
	@FXML public void handleCancelButtonAction(ActionEvent clickEvent){
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();
		
	}
	@FXML public void handleDeleteButtonAction(ActionEvent clickEvent){
//		If camera is not online, then enable DELETE button
		
		SystemManagerGUI.getInstance().cameraList.remove(cameraTableEditCandidateId);
//		Recompute IDs to match changed indexes
		SystemManagerGUI.getInstance().updateIds();
		deleteButton.setDisable(true);
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();

	}
	
	
}
