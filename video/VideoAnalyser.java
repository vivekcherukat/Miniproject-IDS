package miniproject.video;

//Libraries for video conversion from Mat to BufferedImage
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Libraries for GUI
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import miniproject.SystemManager;
import miniproject.alert.AlertItem;
import miniproject.utils.ImageWriter;
import miniproject.utils.TimeKeeper;

//Libraries for motion detection
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

//import com.sun.javafx.webkit.ThemeClientImpl;

public class VideoAnalyser implements Runnable {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	Mat imag = null;

	public static final int INPUT_MODE_CAMERA = 0;
	public static final int INPUT_MODE_FILE = 1;
	public static final int THRESHOLD_TYPE_GUASSIAN = 0;
	public static final int THRESHOLD_TYPE_MEAN = 1;
	public static final int CONTOUR_DETECTION_TYPE_SIMPLE = 0;
	public static final int CONTOUR_DETECTION_TYPE_TC89_KC0S = 1;

	public ImageIcon writerBuffer = new ImageIcon();
	public static int semaphoreCount = 1;
	static int alertCount = 1;

	int analyserId;
	String analyserName = null;
	String inputFileName = null;
	int inputCameraId;
	int inputMode;
	int motionDetectionCount = 0;
	int recordingType;
	//Load these from configuration
	
	int motionDetectionRate = SystemManager.configuration.getMotionDetectionRate();
	boolean highlightMovingObjects = SystemManager.configuration.isHighLightMovingObjects();
	int adaptiveThresholdRectX = SystemManager.configuration.getAdaptiveThresholdRectSizeX();
	int adaptiveThresholdRectY = SystemManager.configuration.getAdaptiveThresholdRectSizeY();
	int thresholdType = SystemManager.configuration.getThresholdMethod();
	int contourDetectionType = SystemManager.configuration.getContourDetectionMethod();
	int movingObjectHighlightBoxWidth = SystemManager.configuration.getMovingObjectHighlightBoxWidth();
	
	boolean exitStatus = false;
	public static Queue<AlertItem> alertQueue = new LinkedList<AlertItem>();
	VideoCapture camera = new VideoCapture();
	VideoWriter videoWriter;
	JFrame jframe;
	public VideoAnalyser(){
	
	}
	public VideoAnalyser(int analyserId,String analyserName, int inputType, int cameraInputId,
			String inputFileName,int recordingType) {
		this.analyserName = new String(analyserName);
		this.inputMode = inputType;
		this.inputCameraId = cameraInputId;
		this.inputFileName = inputFileName;
		this.analyserId = analyserId;
		this.recordingType = recordingType;
		System.out.println("New VideoAnalyser\nAnalyserID:" + this.analyserId
				+ "\nAnalyserName:" + this.analyserName + "\n Input Mode:"
				+ this.inputMode + "\n Input Camera Id:"+this.inputCameraId+"\n Input File Source:"+this.inputFileName+"\n Recording Type:"+this.recordingType);
	}
	public int getAnalyserId(){
		return analyserId;
	}
	public void setAnalyserId(int analyserId){
		this.analyserId = analyserId;
	}
	public int getCameraId(){
		return this.inputCameraId;
	}
	/*
	 * public ImageIcon getCurrentFrame(){ return writerBuffer; }
	 */
	public void showWindow(){
		jframe.setVisible(true);
	}
	public void terminateThread(){
		videoWriter.terminateThread();
		exitStatus = true;
		camera.release();
		jframe.setVisible(false);
	}
	public void run() {
		System.out.println("Video Analyser " + analyserName + ": Starting...");
		// Initialize GUI
		// JFrame.setDefaultLookAndFeelDecorated(true);
		jframe = new JFrame(analyserName);
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(640, 480);
		jframe.setVisible(true);

		// Initialize matrices and storage structures
		Mat frame = new Mat();
		Mat outerBox = new Mat();
		Mat diff_frame = null;
		Mat tempon_frame = null;
		ArrayList<Rect> array = new ArrayList<Rect>();

		videoWriter = new VideoWriter(
				recordingType, analyserName);

		String capturedImagePath = new String();
		String currentTime = new String();

		
		// Video input source, 0 for camera, const string <path to file> for
		// input from video file(.avi only)

		if (inputMode == INPUT_MODE_CAMERA) {
			camera = new VideoCapture(inputCameraId);
		} else {
			if (inputFileName == "") {
				System.out.println("Video Analyser " + analyserName
						+ ": Input file not specified!");
			}
			camera = new VideoCapture(inputFileName);
		}

		if (!camera.isOpened()) {
			System.out.println("Video Analyser " + analyserName
					+ ": Video Input Error");
		} else {
			System.out.println("Video Analyser " + analyserName
					+ ": Video Input Ready");
		}
		Size sz = new Size(640, 480);
		int i = 0;

		// Start video recorder
		Thread videoWriterThread = new Thread(videoWriter);
		videoWriterThread.start();

		while (exitStatus == false) {
			if (camera.read(frame)) {
				Imgproc.resize(frame, frame, sz);
				imag = frame.clone();
				outerBox = new Mat(frame.size(), CvType.CV_8UC1);
				Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
				// Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);

				if (i == 0) {
					jframe.setSize(frame.width(), frame.height());
					diff_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
					tempon_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
					diff_frame = outerBox.clone();
				}

				if (i == 1) {
					Core.subtract(outerBox, tempon_frame, diff_frame);
					if (thresholdType == THRESHOLD_TYPE_MEAN) {
						Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
								Imgproc.ADAPTIVE_THRESH_MEAN_C,
								Imgproc.THRESH_BINARY_INV, 5, 2);
					} else {
						Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
								Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
								Imgproc.THRESH_BINARY_INV,
								adaptiveThresholdRectX, adaptiveThresholdRectY);
					}
					array = detection_contours(diff_frame);

					// ############## Detection ###############
					if (array.size() > 0) {
						motionDetectionCount++;
						if (motionDetectionCount >= motionDetectionRate) {
							motionDetectionCount = 0;

							System.out.println("Video Analyser " + analyserName
									+ ": Motion Detected!");

							capturedImagePath = ImageWriter.writeImage(frame,
									analyserName);
							currentTime = TimeKeeper.getCurrentTime();
							videoWriter.threadPermitVideoWriter.release();

							// Provide permit for video writer
							try {
								SystemManager.threadMutexVideoWriter.acquire();
							} catch (InterruptedException e) {
							}
							videoWriter.incSemCount();
							SystemManager.threadMutexVideoWriter.release();

							// Provide permits for alert subsystem
							SystemManager.alertCount.release();
							try {
								SystemManager.threadMutex.acquire();
							} catch (InterruptedException e) {
							}

							alertQueue.add(new AlertItem(alertCount,
									analyserName, capturedImagePath,
									currentTime));
							alertCount++;
							System.out.println("Video Analyser " + analyserName
									+ ": Item added to alert queue");
							semaphoreCount++;

							SystemManager.threadMutex.release();

						}

						Iterator<Rect> it2 = array.iterator();
						while (it2.hasNext()) {
							Rect obj = it2.next();
							if(highlightMovingObjects){
								
								Core.rectangle(imag, obj.br(), obj.tl(),
										new Scalar(0, 0, 255), movingObjectHighlightBoxWidth);
							}
						}

					}

				}

				i = 1;

				BufferedImage buffer = Mat2bufferedImage(imag);
				videoWriter.setNewFrame(buffer);
				ImageIcon image = new ImageIcon(buffer);
				vidpanel.setIcon(image);
				vidpanel.repaint();
				tempon_frame = outerBox.clone();

			}
		}

	}

	public BufferedImage Mat2bufferedImage(Mat image) {
		MatOfByte bytemat = new MatOfByte();
		Highgui.imencode(".jpg", image, bytemat);
		byte[] bytes = bytemat.toArray();
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {

			// e.printStackTrace();
		}
		return img;
	}

	public ArrayList<Rect> detection_contours(Mat outmat) {
		Mat v = new Mat();
		Mat vv = outmat.clone();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		if (contourDetectionType == CONTOUR_DETECTION_TYPE_SIMPLE) {
			Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST,
					Imgproc.CHAIN_APPROX_SIMPLE);
		} else if (contourDetectionType == CONTOUR_DETECTION_TYPE_TC89_KC0S) {
			Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST,
					Imgproc.CHAIN_APPROX_TC89_KCOS);

		}

		double maxArea = 100;
		int maxAreaIdx = -1;
		Rect r = null;
		ArrayList<Rect> rect_array = new ArrayList<Rect>();

		for (int idx = 0; idx < contours.size(); idx++) {
			Mat contour = contours.get(idx);
			double contourarea = Imgproc.contourArea(contour);
			if (contourarea > maxArea) {
				// maxArea = contourarea;
				maxAreaIdx = idx;
				r = Imgproc.boundingRect(contours.get(maxAreaIdx));
				rect_array.add(r);
				Imgproc.drawContours(imag, contours, maxAreaIdx, new Scalar(0,
						0, 255));
			}

		}

		v.release();

		return rect_array;

	}
}
