package miniproject.upload;
//Access Token pHKslOPetuAAAAAAAAAAE8lvEm1ozHAuJOe5JPD3ge6oB-3XXmdPYP-LEWfOWCNJ

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;


import miniproject.SystemManager;

//import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;

public class DropboxUploader implements Runnable {
	int uploadInterval;
	boolean exitStatus = false;
	
	final String APP_KEY = SystemManager.configuration.getAppKey();
    final String APP_SECRET = SystemManager.configuration.getAppSecret();
    String accessToken = new String(SystemManager.configuration.getAccessToken());
    
    static Queue<StoredItem> uploadQueue = new LinkedList<StoredItem>(); 
    
    public static void addToUploadQueue(String name,String filePath){
    	uploadQueue.add(new StoredItem(name,filePath));
    }
    
	public void terminateThread(){
		exitStatus = true;
	}
	public  DropboxUploader(int uploadInterval){
		this.uploadInterval = uploadInterval*1000*60;
	}
	public void run(){
		
		System.out.println("Uploader: Starting thread");
		DbxRequestConfig config = new DbxRequestConfig("MotionDetectionTest", Locale.getDefault().toString());
		System.out.println("Uploader: Authenticating using access token");
		DbxClient client = new DbxClient(config, accessToken);
        try {
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (DbxException e) {
			e.printStackTrace();
		}
        System.out.println("Uploader: Authentication success");
		
		while(exitStatus == false){
			
			System.out.println("Uploader: Checking for new files");
			if(uploadQueue.isEmpty() == false){
			
				System.out.println("Uploader: Uploading...");
				StoredItem uploadItem = uploadQueue.remove();
//				TODO Change to path
				File inputFile = new File(uploadItem.path,uploadItem.name);
		        FileInputStream inputStream = null;
				try {
					inputStream = new FileInputStream(inputFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		        try {
		            DbxEntry.File uploadedFile = client.uploadFile("/"+uploadItem.name,DbxWriteMode.add(), inputFile.length(), inputStream);
		            System.out.println("Uploader: Uploaded file " + uploadedFile.toString());
		        } catch (DbxException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} 
		        try {
					inputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		        System.out.println("Uploader: Upload Successful");   
			}
			else{
				System.out.println("Uploader: Nothing found, going to sleep");
				System.out.println("Uploader: Sleeping ("+uploadInterval+" milliseconds)");
				try {
					Thread.sleep(uploadInterval);
				} catch (InterruptedException e) {}

			}

	        
		}
		
		
	}
}
