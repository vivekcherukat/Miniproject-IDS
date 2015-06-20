package miniproject.video;

import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import miniproject.SystemManager;
import miniproject.upload.DropboxUploader;
import miniproject.utils.TimeKeeper;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class VideoWriter implements Runnable{
	
	public static int RECORD_TYPE_CONT = 0;
	public static int RECORD_TYPE_DETECTION = 1;
	
	public Semaphore threadPermitVideoWriter = new Semaphore(0);
	int recordingType;
	final int recordTimeCont = SystemManager.configuration.getRecordTimeContSecs();
	final int recordTimeDetection = SystemManager.configuration.getRecordTimeDetectionSecs();
	final int streamWidth = SystemManager.configuration.getRecordingWidth();
	final int streamHeight = SystemManager.configuration.getRecordingHeight();
	int frameRate = SystemManager.configuration.getRecordingFrameRate();
	int recordDuration;
	int permitCount = 1;
	String sourceName = new String();
	
	boolean isFrameReady = false;
	boolean stillInMotion = false;
	boolean exitStatus = false;
	
	BufferedImage frame =  null;
	IMediaWriter writer = null;

	
	public void incSemCount(){
		permitCount++;
		stillInMotion = true;
	}
	public void setNewFrame(BufferedImage frame){
		isFrameReady = true;
		this.frame = frame;
	}
	
	
	
	
	public VideoWriter(int recordingType,String callerSourceName){
		this.recordingType = recordingType;
		this.sourceName = callerSourceName;
	}
	
	public void terminateThread(){
		exitStatus = true;
		recordDuration = 0;
		
	}
	
	public void run(){
		System.out.println("VideoWriter "+sourceName+": Starting...");
		int i;
		String outputFilePath = SystemManager.getVideoStorageLocation();
		String outputFilename = null;
		while(exitStatus == false){
			
			if(recordingType == RECORD_TYPE_CONT ){
				System.out.println("VideoWriter "+sourceName+": Running in continuous recording mode");
				recordDuration = recordTimeCont;
			}
			else if(recordingType == RECORD_TYPE_DETECTION){
				recordDuration = recordTimeDetection;
				System.out.println("VideoWriter "+sourceName+": Running in detection triggered recording mode");
				try {
					System.out.println("VideoWriter "+sourceName+": Waiting for token");
					threadPermitVideoWriter.acquire(permitCount);
					permitCount = 1;
				} catch (InterruptedException e) {}
				System.out.println("VideoWriter "+sourceName+": Token received, starting recording...");
				
				
			}
			System.out.println("Video Writer: Record Duration "+recordDuration);

			outputFilename = new String(sourceName);
			outputFilename = outputFilename + "-" + TimeKeeper.getCurrentTime() + ".mp4";
			long startTime = System.nanoTime();
			writer = ToolFactory.makeWriter(outputFilePath+"\\"+outputFilename);
			writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,streamWidth, streamHeight);
			
			for(i = 0;i < recordDuration*frameRate;i++){
				
//				while(isFrameReady == false);
				
				/*if(exitStatus){
					if(writer != null)
						writer.close();
					return;
				}*/
				
				isFrameReady = false;
				
				if(frame != null){
				
					BufferedImage wrFrame = convertToType(frame,BufferedImage.TYPE_3BYTE_BGR);
					writer.encodeVideo(0, wrFrame, System.nanoTime() - startTime,TimeUnit.NANOSECONDS);
					try {
		                Thread.sleep((long) (1000 / frameRate));
		            } 
		            catch (InterruptedException e) {
		                // ignore
		            }
					if(stillInMotion == true){
						i = 0;
						try {
							SystemManager.threadMutexVideoWriter.acquire();
						} catch (InterruptedException e) {}
						stillInMotion = false;
						SystemManager.threadMutexVideoWriter.release();
					}
				}
				else{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
				
			}
			writer.close();
			DropboxUploader.addToUploadQueue(outputFilename, outputFilePath);
			System.out.println("VideoWriter "+sourceName+": Sleeping...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			
		}
			
	}
	public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        
        BufferedImage image;

        
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        
        else {
            image = new BufferedImage(sourceImage.getWidth(), 
                 sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;
        
    }

}
