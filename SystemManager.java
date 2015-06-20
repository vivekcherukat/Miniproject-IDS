/*System Manager
 * Order of execution of tasks
 * 1.Check first run
 * 2.Load configuration/ write default configuration
 * 3.Add input sources
 * 4.Start video analysis threads
 * 5.Start alert threads
 * 6.Start upload threads*/

package miniproject;

import java.util.ArrayList;

import miniproject.alert.AlertSubsystem;
import miniproject.config.Configuration;
import miniproject.config.XMLWriter;
import miniproject.gui.SystemManagerGUI;
import miniproject.upload.DropboxUploader;
//import miniproject.video.AnalyserInput;
import miniproject.video.VideoAnalyser;

import java.util.concurrent.Semaphore;

public class SystemManager {

	static String configFilePath = "F:\\MiniProject\\Workspace\\MiniProjectGUIFX\\src\\miniproject\\config\\configuration.xml";
	// True if running for the first time
	static boolean isFirstRun = false;
	public static Semaphore alertCount = new Semaphore(0);
	public static Semaphore threadMutex = new Semaphore(1);
	public static Semaphore threadMutexVideoWriter = new Semaphore(1);

	private static String videoStorageLocation;
	private static String imageStorageLocation;
	private static int videoUploadInterval;

	private static boolean uploadEnabled;
	private static boolean alertEnabled;
	private static boolean loadConfigEnabled = true;

//	private static ArrayList<AnalyserInput> inputList = new ArrayList<AnalyserInput>();
	public static Configuration configuration;

	public static AlertSubsystem alertMonitor;
	public static DropboxUploader dropboxUploader;
	public static ArrayList<VideoAnalyser> videoAnalyserList = new ArrayList<VideoAnalyser>();
	public static String getVideoStorageLocation() {
		return videoStorageLocation;
	}

	public static String getImageStorageLocation() {
		return imageStorageLocation;
	}

	

	public static void writeDefaultConfiguration() {
		Configuration config = new Configuration();
		try {

			XMLWriter.write(config, configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Defaut configuration written to file");
	}
	public static void writeConfiguration(){
		try {
			XMLWriter.write(configuration, configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Configuration written to file");
	}
	public static void loadConfiguration() {
		try {

			configuration = XMLWriter.read(configFilePath);
			System.out.println("Configuration Written to file");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("Loaded configuration,"+config2.test);

	}

	public static void createAlertSubsystem() {

		if (alertEnabled == true) {
			alertMonitor = new AlertSubsystem();
			Thread alertMonitorThread = new Thread(alertMonitor);
			alertMonitorThread.start();
		} else {
			System.out.println("System Manager: Alerts disabled");
		}

	}

	public static void createDropboxUploader() {
		if (uploadEnabled == true) {
			dropboxUploader = new DropboxUploader(videoUploadInterval);
			Thread dropboxUploaderThread = new Thread(dropboxUploader);
			dropboxUploaderThread.start();
		} else {
			System.out.println("System Manager: Upload disabled");
		}
	}
	
	/*public static void addInputSource(int id, String sourceName, int inputType,
			int cameraInputSourceID, String filePath, int recordingType) {
		inputList.add(new AnalyserInput(id, sourceName, inputType,
				cameraInputSourceID, filePath, recordingType));
		videoAnalyserList.add(new VideoAnalyser(sourceName, inputType, cameraInputSourceID, filePath, recordingType));
		System.out.println("System Manager: New analyser added, Name "
				+ sourceName + " Input type " + inputType
				+ " Camera Input Source ID " + cameraInputSourceID
				+ " Recording Type "+recordingType);
	}*/
	
	public static int getAnalyserIdByInputId(int inputId){
		for(int i = 0;i < videoAnalyserList.size();i++){
			if(videoAnalyserList.get(i).getAnalyserId() == inputId){
				return i;
			}
		}
		return -1;
	}
	public static void startVideoAnalysisThread(int inputId) {
		/*for (int i = 0; i < inputList.size(); i++) {
			VideoAnalyser analyser = new VideoAnalyser(
					inputList.get(i).inputName, inputList.get(i).inputType,
					inputList.get(i).cameraInputSource,
					inputList.get(i).fileInputSource,
					inputList.get(i).recordingType);
			Thread analysisThread = new Thread(analyser);
			analysisThread.start();
		}*/
		
		String windowName = SystemManagerGUI.getInstance().cameraList.get(inputId).getCameraName();
		int inputType = SystemManagerGUI.getInstance().cameraList.get(inputId).getInputType();
		int cameraIdInt = SystemManagerGUI.getInstance().cameraList.get(inputId).getCameraIdInt();
		String inputFileName = SystemManagerGUI.getInstance().cameraList.get(inputId).getInputFileName();
		int recordingType = SystemManagerGUI.getInstance().cameraList.get(inputId).getCameraModeInt();
		
//		System.out.println("Video Analyser: Starting analysis thread for input ID:"+inputId);
		VideoAnalyser videoAnalyser = new VideoAnalyser(inputId,windowName, inputType, cameraIdInt, inputFileName, recordingType);
		Thread analysisThread = new Thread(videoAnalyser);
		videoAnalyserList.add(videoAnalyser);
		analysisThread.start();
		System.out.println("Video Analyser: Analysis thread started");
		
	}
	public static void stopVideoAnalysisThread(int inputId){
//		System.out.println("Video Analyser: Stoping analysis thread for camera ID:"+inputId);
		int analyserId = getAnalyserIdByInputId(inputId);
		if(analyserId != -1){
			videoAnalyserList.get(analyserId).terminateThread();
			videoAnalyserList.remove(analyserId);
		}
		
	
		
	}
	public static void showAnalyserWindow(int inputId){
		int analyserId = getAnalyserIdByInputId(inputId);
		videoAnalyserList.get(analyserId).showWindow();
	}
	public static void loadSystemManagerConfiguration() {
		videoStorageLocation = configuration.getVideoStorageLocation();
		imageStorageLocation = configuration.getImageStorageLocation();
		videoUploadInterval = configuration.getUploadIntervalMins();
		uploadEnabled = configuration.isUploadEnabled();
		alertEnabled = configuration.isAlertEnabled();
	}

	// Main
	public void initialize() {

		if (isFirstRun) {
			writeDefaultConfiguration();
		}

		if (loadConfigEnabled) {
			// writeConfiguration();
			loadConfiguration();
			// System.out.println(configuration.getEmailTo());
		}
		
		loadSystemManagerConfiguration();
		// TODO Call from view controller
		// TODO Load cameras from disk storage(file)
		// addInputSource(4,"Source4",VideoAnalyser.INPUT_MODE_FILE,0,"F:\\MiniProject\\Testing\\truck.avi");
//		 addInputSource(3,"Parking Lot",VideoAnalyser.INPUT_MODE_FILE,0,"F:\\MiniProject\\Testing\\parkinglotday.avi",VideoWriter.RECORD_TYPE_DETECTION);
		// addInputSource(5,"Source-Cam",VideoAnalyser.INPUT_MODE_CAMERA,0,"");

//		createVideoAnalysisThread();

		createAlertSubsystem();

		createDropboxUploader();

		System.out.println("System Manager: System Ready");

	}
}
