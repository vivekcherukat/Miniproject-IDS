package miniproject.video;



public class AnalyserInput {
	public int inputId = 0;
	public String inputName = "";
	public int inputType = VideoAnalyser.INPUT_MODE_CAMERA;
	public int cameraInputSource = 0;
	public String fileInputSource = "";
	public int recordingType = VideoWriter.RECORD_TYPE_DETECTION;
	public AnalyserInput(int inputIdA,String inputNameA,int inputTypeA,int cameraInputSourceA,String fileInputSourceA,int recordingTypeA){
		inputId = inputIdA;
		inputName = inputNameA;
		inputType = inputTypeA;
		cameraInputSource = cameraInputSourceA;
		fileInputSource = fileInputSourceA;
		recordingType = recordingTypeA;
	}
}

