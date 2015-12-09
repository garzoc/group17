package HotelsDSSV2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class Properties {

	static private String filename="config.properties";
	static public final Charset charset=Charset.forName("UTF-8");
	
	static private String readProperties(String source){
		BufferedReader reader=null;
		String fileContent="";
		
		try {
			reader = Files.newBufferedReader(Paths.get(source), charset);
			String line=null;
		    while ((line = reader.readLine()) != null) {
		    	fileContent=fileContent+line+"\n";
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}
		
		return fileContent;
	}
	
	static private void writeProperties(String source,String listedProperties){
		BufferedWriter writer=null;

		try {
			writer = Files.newBufferedWriter(Paths.get(source), charset);
		    writer.write(listedProperties,0,listedProperties.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	static public void addProperty(String property,String value){
		
		
		if(Files.notExists(Paths.get(filename))){
			System.out.println("NOTICE: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! No existing config file creating new default config.properties");
			try {
				Files.createFile(Paths.get(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		if(!propertyExists(property)){
			String propertyList=readProperties(filename);
			propertyList=propertyList+property+"="+value;
			writeProperties(filename,propertyList);
		}else{
			System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! property ['"+property+"'] already exist");
		}
			
				
//		Charset charset = Charset.forName("UTF-8");
//		String s ="hej";
//		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), charset)) {
//		    writer.write(s, 0, s.length());
//		    writer.newLine();
//		    s="never";
//		    writer.write(s, 0, s.length());
//		    writer.close();
//		} catch (IOException x) {
//		    System.err.format("IOException: %s%n", x);
//		}
		
			
//		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
//		    String line = null;
//		    while ((line = reader.readLine()) != null) {
//		        System.out.println(line);
//		    }
//		} catch (IOException x) {
//		    System.err.format("IOException: %s%n", x);
//		}
	}
	
	static private String toStringList(String[] stringArray){
		String arrayToString="";
		for(int i=0;i<stringArray.length;i++){
			if(!stringArray[i].equals("")){
			arrayToString=arrayToString+stringArray[i]+"\n";
			}
		}
		return arrayToString;
	}
	
	static private int findRowNumberOfProperty(String property){
		String[] properties=readProperties(filename).split("\n");
		for(int i=0;i<properties.length;i++){
			if(properties[i].indexOf(property)>-1){
				return i;
			
			}
		}
		return -1;	
	}
	
	static public String getProperty(String property){
		if(configExists()){
			String[] properties=readProperties(filename).split("\n");
			if(propertyExists(property)){
				try{
				return properties[findRowNumberOfProperty(property)].split("=")[1];
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! property "+property+" doesn't contain a value");
				}
				return "";
			}else {
				System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! property "+property+" do not exist");
				return "";
			}
		}else{
			System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! file "+filename+" do not exist");
			return "";
		}
	}
	
	
	
	static public void deleteProperty(String property){
		if(configExists()){
			if(propertyExists(property)){
				String[] properties=readProperties(filename).split("\n");
				properties[findRowNumberOfProperty(property)]="";
				writeProperties(filename,toStringList(properties));
			}else{
				System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! property "+property+" do not exist");
			}
		}else{
			System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! file "+filename+" do not exist");
		}
	}
	
	static public void changeProperty(String property,String newValue){	
		if(configExists()){
			if(propertyExists(property)){			
				String[] properties=readProperties(filename).split("\n");
			
				properties[findRowNumberOfProperty(property)]=property+"="+newValue;
				writeProperties(filename,toStringList(properties));
			
			}else{
				System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! could not write to property "+property);
			}
		}else{
			System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! file "+filename+" do not exist");
		}
	}
	
	static public boolean propertyExists(String property){
		if(configExists()){
			if(findRowNumberOfProperty(property)>=0){
				return true;
			}else{
				return false;
			}
		}else{
			System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! file "+filename+" do not exist");
			return false;
		}
	}

	

static public void createCustomConfigFile(Path directory,String fileName){
	
	if(Files.notExists(Paths.get(directory+"/"+fileName))){
		String pathBuilder="";
		//System.out.println(directory.toString().split("/").length);
		for(int i=0;i<directory.toString().split("/").length;i++){
			pathBuilder=pathBuilder+directory.toString().split("/")[i].toString()+"/";
			if(Files.notExists(Paths.get(pathBuilder))){
				new File(pathBuilder).mkdir();
			}
		}
		
		try {
			if(0<directory.toString().length()){
				Files.createFile(Paths.get(directory+"/"+fileName));
				filename=directory+"/"+fileName;
			}else{
				Files.createFile(Paths.get(fileName));
				filename=fileName;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}else{
		System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! file '"+fileName+"' already exist in directory '"+directory+"'");

	}
}
	
static public void loadCutomConfigFile(String pathName){
	if(Files.exists(Paths.get(pathName))){
		filename=pathName;
	}else{
		System.out.println("ERROR: [Properties('"+new Object(){}.getClass().getEnclosingMethod().getName()+ "')]! could not find "+pathName);
	}
}	
	
static public boolean configExists(){
		if(Files.exists(Paths.get(filename))){
			return true;
		}else{
			return false;
		}
		
	}
}
