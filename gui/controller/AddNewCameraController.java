package miniproject.gui.controller;

import miniproject.gui.SystemManagerGUI;
import miniproject.gui.model.CameraTableItem;
import miniproject.video.VideoAnalyser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class AddNewCameraController {
	@FXML TextField cameraNameField;
	@FXML TextField cameraIdField;
	@FXML ChoiceBox<String> cameraModeChoiceBox;
	
	
	@FXML public void handleAddCameraButtonAction(ActionEvent clickEvent){
		System.out.println("GUI: Button action Add");

		String cameraName = cameraNameField.getText();
		cameraNameField.clear();
		String cameraId = cameraIdField.getText();
		cameraIdField.clear();
		int cameraMode = cameraModeChoiceBox.getSelectionModel().getSelectedIndex();
		
		String cameraStatus = new String("Offline");
		
		
		System.out.println("Adding new camera, Name "+cameraName+" ID "+cameraId+" Mode "+cameraMode);
		if(cameraId.equals("101")){
			SystemManagerGUI.getInstance().cameraList.add(new CameraTableItem(cameraId, cameraName, cameraMode, cameraStatus));
			CameraTableItem item = SystemManagerGUI.getInstance().cameraList.get(SystemManagerGUI.getInstance().cameraList.size()-1);
			item.setInputType(VideoAnalyser.INPUT_MODE_FILE);
			item.setInputFileName("F:\\MiniProject\\Testing\\thief.avi");
		}
		else{
			
			SystemManagerGUI.getInstance().cameraList.add(new CameraTableItem(cameraId, cameraName, cameraMode, cameraStatus));
		}
//		TODO Add to alert item list if necessary
//		SystemManager.addInputSource(0, cameraName, VideoAnalyser.INPUT_MODE_CAMERA,Integer.valueOf(cameraId),"",cameraMode);
		
		((Node)(clickEvent.getSource())).getScene().getWindow().hide();
		
	}
	@FXML public void handleCancelButtonAction(ActionEvent clickEvent){
		System.out.println("GUI: Button action Cancel");

		((Node)(clickEvent.getSource())).getScene().getWindow().hide();
	}
	@FXML public void initialize(){
		cameraModeChoiceBox.setItems(FXCollections.observableArrayList("Continuous","Detection Triggered"));
		cameraModeChoiceBox.setValue("Continuous");
		
	}
}
