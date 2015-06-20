package miniproject.gui.model;

import miniproject.video.VideoAnalyser;
import miniproject.video.VideoWriter;
import javafx.beans.property.SimpleStringProperty;

public class CameraTableItem {
	public static int count = 0;
	private int inputId;
	private boolean cameraOnline;
	private int cameraIdInt;
	private int cameraModeInt;
	private int inputType;
	private String inputFileName;
	private SimpleStringProperty cameraId;
	private SimpleStringProperty cameraName;
	private SimpleStringProperty cameraMode;
	private SimpleStringProperty cameraStatus;
	
	public CameraTableItem(String id,String name,int cameraMode,String status){
		this.cameraId = new SimpleStringProperty(id);
		this.cameraIdInt = Integer.valueOf(cameraId.get());
		this.cameraName = new SimpleStringProperty(name);
//		this.cameraMode = new SimpleStringProperty(mode);
		this.cameraModeInt = cameraMode;
		if(cameraMode == VideoWriter.RECORD_TYPE_DETECTION)
			this.cameraMode = new SimpleStringProperty("Detection Triggered");
		else if(cameraMode == VideoWriter.RECORD_TYPE_CONT)
			this.cameraMode = new SimpleStringProperty("Continuous");
		this.cameraStatus = new SimpleStringProperty(status);
		inputId = count;
		count++;
		cameraOnline = false;
		inputType = VideoAnalyser.INPUT_MODE_CAMERA;
		inputFileName = null;
	}
	public int getInputId(){
		return inputId;
	}
	public void setInputId(int inputId){
		this.inputId = inputId;
	}
	public boolean isCameraOnline(){
		return this.cameraOnline;
	}
	public void setCameraOnline(boolean cameraOnline){
		this.cameraOnline = cameraOnline;
	}
	public int getCameraIdInt(){
		return cameraIdInt;
	}
	public void setCameraIdInt(int cameraIdInt){
		this.cameraIdInt = cameraIdInt;
	}
	public int getInputType(){
		return this.inputType;
	}
	public void setInputType(int inputType){
		this.inputType = inputType;
	}
	public String getInputFileName() {
		return inputFileName;
	}
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	public int getCameraModeInt(){
		return cameraModeInt;
	}
	public void setCameraModeInt(int cameraModeInt){
		this.cameraModeInt = cameraModeInt;
	}
	public String getCameraId() {
		System.out.println(cameraId.get());
		return cameraId.get();
	}

	public void setCameraId(String cameraId) {
		this.cameraId.set(cameraId);
	}

	public String getCameraName() {
		return cameraName.get();
	}

	public void setCameraName(String cameraName) {
		this.cameraName.set(cameraName);
	}

	public String getCameraMode() {
		return cameraMode.get();
	}

	public void setCameraMode(int cameraMode) {
		if(cameraMode == 0)
			this.cameraMode.set("Continuous");
		else
			this.cameraMode.set("Detection Triggered");
		
		cameraModeInt = Integer.valueOf(cameraMode); 
		 
	}

	public String getCameraStatus() {
		return cameraStatus.get();
	}

	public void setCameraStatus(String cameraStatus) {
		this.cameraStatus.set(cameraStatus);
	}
	
	
	
	
	
}
