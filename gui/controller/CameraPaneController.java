package miniproject.gui.controller;

import miniproject.SystemManager;
import miniproject.gui.SystemManagerGUI;
import miniproject.gui.model.CameraTableItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class CameraPaneController {
	
//	TODO Change button label when row is selected
	@FXML Button startStopButton;
	@FXML Button viewButton;
	
	@FXML TableView<CameraTableItem> cameraTable = new TableView<CameraTableItem>();
	@FXML TableColumn<CameraTableItem,String> cameraTableColId = new TableColumn<CameraTableItem,String>();
	@FXML TableColumn<CameraTableItem,String> cameraTableColName = new TableColumn<CameraTableItem, String>();
	@FXML TableColumn<CameraTableItem,String> cameraTableColMode = new TableColumn<CameraTableItem, String>();
	@FXML TableColumn<CameraTableItem,String> cameraTableColStatus = new TableColumn<CameraTableItem, String>();
	
	@FXML public void initialize(){
		cameraTableColId.setCellValueFactory(new PropertyValueFactory<CameraTableItem,String>("cameraId"));
		cameraTableColName.setCellValueFactory(new PropertyValueFactory<CameraTableItem,String>("cameraName"));
		cameraTableColMode.setCellValueFactory(new PropertyValueFactory<CameraTableItem,String>("cameraMode"));
		cameraTableColStatus.setCellValueFactory(new PropertyValueFactory<CameraTableItem,String>("cameraStatus"));
		cameraTable.setItems(SystemManagerGUI.getInstance().cameraList);
		
		
		cameraTableColId.prefWidthProperty().bind(cameraTable.widthProperty().divide(8));
		cameraTableColName.prefWidthProperty().bind(cameraTable.widthProperty().divide(4));
		cameraTableColMode.prefWidthProperty().bind(cameraTable.widthProperty().divide(3));
		cameraTableColStatus.prefWidthProperty().bind(cameraTable.widthProperty().divide(3.5));
		viewButton.setDisable(true);
	}
	@FXML public void handleStartStopButtonAction(){
		System.out.println("GUI: Button action -  Start Stop");
		CameraTableItem selectedItem = cameraTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null){
			
			if(!selectedItem.isCameraOnline()){
				startStopButton.setText("Stop");
				SystemManager.startVideoAnalysisThread(selectedItem.getInputId());
				selectedItem.setCameraOnline(true);
				SystemManagerGUI.getInstance().cameraList.get(selectedItem.getInputId()).setCameraStatus("Online");;
//				Refresh Table
				cameraTable.getColumns().get(cameraTable.getSelectionModel().getSelectedIndex()).setVisible(false);
				cameraTable.getColumns().get(cameraTable.getSelectionModel().getSelectedIndex()).setVisible(true);

			}
			else{
				startStopButton.setText("Start");
				SystemManager.stopVideoAnalysisThread(selectedItem.getInputId());
				selectedItem.setCameraOnline(false);
				SystemManagerGUI.getInstance().cameraList.get(selectedItem.getInputId()).setCameraStatus("Offline");;
				cameraTable.getColumns().get(cameraTable.getSelectionModel().getSelectedIndex()).setVisible(false);
				cameraTable.getColumns().get(cameraTable.getSelectionModel().getSelectedIndex()).setVisible(true);			}
		}
	}
	@FXML public void handleViewButtonAction(){
		System.out.println("GUI: Button action -  View");
//		System.out.println("View button pressed");
		
		CameraTableItem selectedItem = cameraTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null){
			if(selectedItem.isCameraOnline())
				SystemManager.showAnalyserWindow(selectedItem.getInputId());
			else
				System.out.println("GUI: Action Failed - View (Selected item not running)");
		}
	}
	@FXML public void handleTableRowSelectAction(){
		System.out.println("GUI: Action - Table Selected");
		CameraTableItem selectedItem = cameraTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null){
			if(selectedItem.isCameraOnline()){
				startStopButton.setText("Stop");
				if(viewButton.isDisabled())
					viewButton.setDisable(false);
			}
			else{
				startStopButton.setText("Start");
				if(!viewButton.isDisabled()){
					
					viewButton.setDisable(true);
				}
			}
				
		}
		
	}
	@FXML public void handleConfigButtonAction(){
		System.out.println("GUI: Button Action - Config");
		CameraTableItem selectedItem = cameraTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null){
			SystemManagerGUI.getInstance().setCameraTableEditCandidateId(selectedItem.getInputId());
			SystemManagerGUI.getInstance().openConfigureCameraWindow();

		}
		else{
			System.out.println("GUI: Button Action Failed - Config (No row selected)");
		}
	}
	
	



}
