package miniproject.gui.controller;
import miniproject.gui.SystemManagerGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
//import javafx.scene.paint.Stop;

public class MainWindowController {
	
	SystemManagerGUI mainClass;
	
	
	@FXML private BorderPane mainWindowBorderPane;
	@FXML public void handleCameraButtonAction(){
		System.out.println("GUI: Button Action - Cameras");
		SystemManagerGUI.getInstance().setMainWindowScene(SystemManagerGUI.SCENE_CAMERA);
	}
	@FXML public void handleImageButtonAction(){
		System.out.println("GUI: Button Action - Images");
		SystemManagerGUI.getInstance().setMainWindowScene(SystemManagerGUI.SCENE_IMAGES);
	}
	@FXML public void handleNewButtonAction(){
		System.out.println("GUI: Button Action - New");
		SystemManagerGUI.getInstance().openAddNewCameraWindow();
	}
	@FXML public void handleSettingsButtonAction(){
		System.out.println("GUI: Button Action - Settings");
		SystemManagerGUI.getInstance().openConfigurationWindow();
	}
	@FXML public void handleCloseMenuAction(){
		System.out.println("GUI: Menu Action - Close");
		Platform.exit();
	}
	@FXML public void handleSettingsMenuAction(){
		System.out.println("GUI: Menu Action - Settings");
		SystemManagerGUI.getInstance().openConfigurationWindow();
	}
	@FXML public void handleExitButtonAction(){
		System.out.println("GUI: Button Action - Exit");
		Platform.exit();
	}
	
	@FXML public void initialize(){
//		System.out.println("Initialized");
	}
}
