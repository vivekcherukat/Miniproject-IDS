package miniproject.gui;

//import java.io.IOException;

import java.io.IOException;

import miniproject.SystemManager;
import miniproject.gui.model.CameraTableItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SystemManagerGUI extends Application {
		
	public static final int SCENE_CAMERA = 0;
	public static final int SCENE_IMAGES = 1;
	public static final int SCENE_RECORDINGS = 2;
	public static final int SCENE_ALERTS = 3;
	
	private Stage primaryStage;
	public SystemManager systemManager = new SystemManager();

	
	private BorderPane mainWindowBorderPaneCamera;
	private BorderPane mainWindowBorderPaneImages;
	private BorderPane cameraPane;
	private BorderPane imagePane;
	
	private BorderPane addNewCameraPane;
	
	private Scene mainWindowSceneCamera;
	private Scene mainWindowSceneImages;
	private Scene addNewCameraScene;
	
	private static SystemManagerGUI instance;
	
	
	public ObservableList<CameraTableItem> cameraList = FXCollections.observableArrayList();
	private int cameraTableEditCandidateId;
	
	public SystemManagerGUI(){
		instance = this;
	}
	/*public CameraTableItem getCameraListItemByInputId(int inputId){
		for(int i = 0;i < cameraList.size();i++){
			if(cameraList.get(i).getInputId() == inputId){
				return cameraList.get(i);
			}
		}
		return null;
	}*/
	public static SystemManagerGUI getInstance(){
		return instance;
	}
	private void loadFXMLViews(){

		try {
			mainWindowBorderPaneCamera = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\MainWindow.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		try {
			mainWindowBorderPaneImages = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\MainWindow.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			cameraPane = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\CameraPane.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			imagePane = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\ImagePane.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			addNewCameraPane = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\AddNewCamera.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	private void setBorderPaneComponents(){
		mainWindowBorderPaneCamera.setCenter(cameraPane);
		mainWindowBorderPaneImages.setCenter(imagePane);
	}
	
	private void initializeScenes(){
		mainWindowSceneCamera = new Scene(mainWindowBorderPaneCamera);
		mainWindowSceneCamera.getStylesheets().add(SystemManagerGUI.class.getResource("view\\AllWindowStyle.css").toExternalForm());

		mainWindowSceneImages = new Scene(mainWindowBorderPaneImages);

		
		addNewCameraScene = new Scene(addNewCameraPane);
		addNewCameraScene.getStylesheets().add(SystemManagerGUI.class.getResource("view\\AllWindowStyle.css").toExternalForm());

		
	}
	
	
	
	
	public void setMainWindowScene(int sceneId){
		if(sceneId == SCENE_CAMERA){
			primaryStage.setScene(mainWindowSceneCamera);
			
		}
		else if(sceneId == SCENE_IMAGES){
			primaryStage.setScene(mainWindowSceneImages);

		}
		else if(sceneId == SCENE_RECORDINGS){
			primaryStage.setScene(mainWindowSceneCamera);

		}
		else if(sceneId == SCENE_ALERTS){
			primaryStage.setScene(mainWindowSceneCamera);

		}
	}
	public void updateIds(){
		CameraTableItem.count--;
		for(int i = 0;i < cameraList.size();i++){
			cameraList.get(i).setInputId(i);
			SystemManager.videoAnalyserList.get(i).setAnalyserId(i);
		}
	}
	
	public void start(Stage primaryStageA){
//		TODO Initialize system manager first, then load configuration
		
		loadFXMLViews();
		setBorderPaneComponents();
		initializeScenes();
		
		this.primaryStage = primaryStageA;
		primaryStage.setTitle("Batman");
		primaryStage.setScene(mainWindowSceneCamera);
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
		
		systemManager.initialize();
//		cameraList.add(new CameraTableItem("0", "Parking Lot", "Detection Triggered", "Online"));
	}
	public static void main(String[] args){
		launch(args);
	}
	
	public void openAddNewCameraWindow(){
		Stage addNewCameraStage = new Stage();
		addNewCameraStage.setTitle("Add New Camera");
		addNewCameraStage.setScene(addNewCameraScene);
		addNewCameraStage.setMaxWidth(400);
		addNewCameraStage.setMaxHeight(230);
		addNewCameraStage.initStyle(StageStyle.UNIFIED);
		addNewCameraStage.show();
		
	}
	public void openConfigureCameraWindow(){
		Scene configureCameraScene;
		BorderPane configureCameraPane = null;
		try {
			configureCameraPane = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\ConfigureCamera.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configureCameraScene =new Scene(configureCameraPane);
		Stage configureCameraStage = new Stage();
		configureCameraStage.setTitle("Edit Camera");
		configureCameraScene.getStylesheets().add(SystemManagerGUI.class.getResource("view\\AllWindowStyle.css").toExternalForm());

		configureCameraStage.setScene(configureCameraScene);
		configureCameraStage.setMaxWidth(380);//Added 30
		configureCameraStage.setMaxHeight(300);//Added 60
		configureCameraStage.initStyle(StageStyle.UNIFIED);

		configureCameraStage.show();
	}
	public void openConfigurationWindow(){
		Scene configurationScene;
		BorderPane configurationPane = null;
		try {
			configurationPane = (BorderPane)FXMLLoader.load(SystemManagerGUI.class.getResource("view\\ConfigurationWindow.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configurationScene =new Scene(configurationPane);
		configurationScene.getStylesheets().add(SystemManagerGUI.class.getResource("view\\AllWindowStyle.css").toExternalForm());

		Stage configurationStage = new Stage();
		configurationStage.setTitle("Settings");
		configurationStage.setScene(configurationScene);
		configurationStage.setMaxWidth(680);//Added 30
		configurationStage.setMaxHeight(500);//Added 60
		configurationStage.initStyle(StageStyle.UNIFIED);
		configurationStage.show();
	}
	public void setCameraTableEditCandidateId(int inputId){
		this.cameraTableEditCandidateId = inputId;
	}
	public int getCameraTableEditCandidateId(){
		return cameraTableEditCandidateId;
	}
}
