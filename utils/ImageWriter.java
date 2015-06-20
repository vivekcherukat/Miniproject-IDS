package miniproject.utils;


import java.util.concurrent.Semaphore;

import miniproject.SystemManager;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;;


public class ImageWriter {
	
	/*static String getCurrentTime(){
		String timestamp =  new java.text.SimpleDateFormat("dd_MM_yyyy h_mm_ss a").format(new Date());
		return timestamp;
	}*/
	public static String writeImage(Mat image,String analyserName){
		Semaphore semaphore = new Semaphore(1);
		try{
			semaphore.acquire();
		}catch(Exception e){}
		String capturedImageName = new String(TimeKeeper.getCurrentTime());
		capturedImageName = SystemManager.getImageStorageLocation()+"\\" + analyserName + " " + capturedImageName + ".jpg";
		//System.out.println(capturedImageName);
		//capturedImageName = new String("Hello.jpg");
		Highgui.imwrite(capturedImageName,image);
		semaphore.release();
		return capturedImageName;
	}
}
