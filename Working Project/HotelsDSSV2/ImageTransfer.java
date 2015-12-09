package HotelsDSSV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;

public class ImageTransfer {
	static public String imagePath;
	
	public void copyFiles(File sourceFile,File destinationFile) throws IOException{
		if(!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())){
			if(!destinationFile.exists()){
				destinationFile.createNewFile();
			}
	
			FileChannel source=null;
			FileChannel destination=null;
			try{
				source=new FileInputStream(sourceFile).getChannel();
				destination=new FileOutputStream(destinationFile).getChannel();
			
				long count = 0;
				long size = source.size();
				//destination.force(false);
				//source.close();
				while ((count += destination.transferFrom(source, count, size - count)) < size);
                
			}catch(NonWritableChannelException e){
				System.out.println("hej");
			}finally{
				if (source != null) {
					source.close();
	            	}
	            	if (destination != null) {
	            		destination.close();
	            	}
	            
//	            	destination.close();
//	            	source.close();
			}
		}
		
	}
	
	public ImageTransfer(String source) throws IOException{
		File sourceFile=new File(source);
		String localImagePath="";
		
		for(int i=0;i<source.length();i++){
			if(source.indexOf("/",i)>-1){
				i=source.indexOf("/",i);
			}else if(source.indexOf("/",i)==-1){
				localImagePath=source.substring(i,source.length());
				break;
			}
		}
		imagePath=Properties.getProperty("imgPath")+localImagePath;
		File destinationFile=new File(Properties.getProperty("imgPath")+localImagePath);
		System.out.println(source);
		this.copyFiles(sourceFile, destinationFile);
		
	}
	
	public ImageTransfer(String source,String destination) throws IOException{
		File sourceFile=new File(source);
		
		String localImageName="";
		if(source.lastIndexOf("/")>-1){
			localImageName=source.substring(source.lastIndexOf("/")+1,source.length());
		}else{
			localImageName=source;
		}
		File destinationFile=new File(destination+localImageName);
		System.out.println(sourceFile.toString()+" this is the source folder");
		System.out.println(destinationFile.toString()+" this is the destination file");
		this.copyFiles(sourceFile, destinationFile);
		
	}
	
//	static public String getImagePath(){
//		return imagePath;
//	}
	
	static public boolean createNewImageTransfer(String source){
		try {
			new ImageTransfer(source);
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
}
