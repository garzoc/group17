package HotelsDSS;

import java.sql.Connection;
//import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SearchItem {
	private int hits;
	private float accuracy;
	private String searchQuery;
	//boolean pH=false;
	private static int arrayLengths=30;
	private static SearchItem[] items=new SearchItem[arrayLengths];
	private static Hotel[][] hotels= new Hotel[arrayLengths][arrayLengths];
	private static Hotel[] sortedHotels= new Hotel[arrayLengths];
	Connection connection=null;
	ResultSet viewing=null;
	Statement statement=null;
	private SearchItem(String name,int index,int minQueryLength) throws SQLException{
		
		try{
			Class.forName("org.sqlite.JDBC");
			
			//connection=DriverManager.getConnection("jdbc:sqlite:/Users/john/Desktop/Test1.sqlite");
			
			connection=DriverManager.getConnection("jdbc:sqlite:/Users/System/Dropbox/1mac/Hotels.sqlite");
			
			//System.out.println(connection.getClientInfo());
			statement=connection.createStatement();
			
			//change the sql statements when changinh the database
			viewing=statement.executeQuery("Select * FROM Hotel");
			
			search(name,index,minQueryLength);
			//search(name,index,minQueryLength,statement.executeQuery("Select * FROM Type"));
			//System.out.println("connected");
		}catch(ClassNotFoundException error){
			//System.out.println(error);
		}catch(SQLException error){
			//System.out.println(error);
		}finally{
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException ignore){
					
				}
			}
		}
		
	}
	

	
	
	private void search(String name,int index,int minQueryLength) throws SQLException{
		
		int loopStop=0;
		for(int i=index+minQueryLength-1;i<name.length()+1;i++){
			
			
			//change the sql statements when changinh the database
			this.viewing=statement.executeQuery("Select * FROM Hotel where Name LIKE '%"+name.substring(index,i)+"%'");
		
			
			if(viewing.next()){
				//This for loop clears old reults when we find a new better matching result
				for(int n=0;n<hotels[index].length;n++){
					hotels[index][n]=null;
				}
				
				//hotels[index][hits]=new Hotel(this.viewing.getInt("HID"),this.viewing.getString("Name"),this.viewing.getString("Address"),this.viewing.getDouble("Price"),this.viewing.getBoolean("Pool"),this.viewing.getBoolean("Gym"),this.viewing.getBoolean("Bar"),this.viewing.getBoolean("Pets"),this.viewing.getInt("Stars"));
				//hotels[index][hits]=new Hotel(this.viewing.getInt("Id"),this.viewing.getString("query"));
				hotels[index][hits]=new Hotel(this.viewing.getInt("HID"),this.viewing.getString("Name"),this.viewing.getString("Address"),this.viewing.getDouble("Price"),this.viewing.getBoolean("Pool"),this.viewing.getBoolean("Gym"),this.viewing.getBoolean("Bar"),this.viewing.getBoolean("Pets"),this.viewing.getInt("Stars"), this.viewing.getString("Img"));

				//				System.out.println(hotels[index][hits].getHotelName()+" whith index "+index+"\n");
				this.hits++;
			
				while(viewing.next()){
					//hotels[index][hits]=new Hotel(this.viewing.getInt("HID"),this.viewing.getString("Name"),this.viewing.getString("Address"),this.viewing.getDouble("Price"),this.viewing.getBoolean("Pool"),this.viewing.getBoolean("Gym"),this.viewing.getBoolean("Bar"),this.viewing.getBoolean("Pets"),this.viewing.getInt("Stars"));
					//hotels[index][hits]=new Hotel(this.viewing.getInt("Id"),this.viewing.getString("query"));
//					System.out.println("wazzaaa");
					//System.out.println(hotels[index][hits].getHotelName()+" whith index "+index+"\n");
					hotels[index][hits]=new Hotel(this.viewing.getInt("HID"),this.viewing.getString("Name"),this.viewing.getString("Address"),this.viewing.getDouble("Price"),this.viewing.getBoolean("Pool"),this.viewing.getBoolean("Gym"),this.viewing.getBoolean("Bar"),this.viewing.getBoolean("Pets"),this.viewing.getInt("Stars"), this.viewing.getString("Img"));
					
					//hotels[index][hits].setAccuracy(name.substring(index+minQueryLength-1,i).length()/name.length());
					this.hits++;
					//System.out.println(hits);
					
					
				}
				//System.out.println("start "+index+" Stop "+i+" string "+name.substring(index,i));
				loopStop=i;
				//System.out.println("loop  "+loopStop);
				hits=0;
			}else{
				break;
			}
				
		}
		
		//System.out.println("test: "+statement.executeQuery("Select query FROM Type where query LIKE '%zuppa%'").next());
		if(loopStop>0){
			//System.out.println("strike "+index+" "+loopStop);
			this.searchQuery=name.substring(index,loopStop);
			this.accuracy=name.substring(index+minQueryLength-1,loopStop).length()/name.length();
//			try{
//			System.out.println("hotel   "+hotels[index][0].getHotelName());
//			}catch(NullPointerException e){}
//			try{
//			System.out.println("hotel   "+hotels[index][1].getHotelName());
//			}catch(NullPointerException e){}
//			try{
//			System.out.println("hotel   "+hotels[index][2].getHotelName());
//			}catch(NullPointerException e){}
//			try{
//			System.out.println("hotel   "+hotels[index][3].getHotelName());
//			}catch(NullPointerException e){}
			
			//System.out.println(this.searchQuery);	
		}else{
			
			this.searchQuery="";
			this.accuracy=0;
			this.hits=0;
		}
	}
	
	public String getQuery(){
		return this.searchQuery;
	}
	
	public float getAccuracy(){
		return this.accuracy;
	}
	
	public int getHits(){
		return this.hits;
	}
	
	
	static public Hotel[] locateItems(String name) throws SQLException{
		//SearchItem[] items=new SearchItem[100];
		
		for(int i=0;i<hotels.length;i++){
			for(int n=0;n<hotels[i].length;n++){
				hotels[i][n]=null;
			}
		}
		
		String query = name;

		
		//set the minimum query length of the in and output
		int minQueryLength=3;
		if(query.length()<3){
			minQueryLength=query.length();
		}
//		int minInputLength=3;
		//the variable above must always be greater than the one below
//		int minOutputLength=3;

		
		for(int i=0;i<query.length()-(minQueryLength)+1;i++){
		
			items[i]=new SearchItem(query,i,minQueryLength+1);
			//System.out.println("Changing index to  "+(i+1));
			
			
		}

//		
//		System.out.println("Finnishing up the search");
		
		for(int i=0;i<sortedHotels.length;i++){
			sortedHotels[i]=null;
		}
		
		int test=0; 
		for(int i=0;i<hotels.length;i++){
			for(int n=0;n<hotels[i].length;n++){
				if(hotels[i][n]!=null){
					//System.out.println("okay");
					for(int x=0;x<sortedHotels.length;x++){
						if(sortedHotels[x]!=null){
							if(hotels[i][n].getHotelId()==sortedHotels[x].getHotelId()){
								test++;
								break;
							}
							
							
						}else{
						
							sortedHotels[x]=hotels[i][n];
							test++;
							break;
						}
					}
				}
			}
		}
		
		
		
		Hotel [] tempSortedHotels=new Hotel[arrayLengths];
		
		
		//place names in alphabetic order
		for(int k=0;k<arrayLengths&&items[k]!=null;k++){
			for(int n=0;n<arrayLengths;n++){
				//The recomended max length of an the sorter may not work otherwise				
				int index=10000;
				//keeping track of which position to clear
				int loopPosition=0;
				//System.out.println("change gear");
				for(int i=0;sortedHotels[i]!=null;i++){
					if(sortedHotels[i].indexOfHotelName(items[k].searchQuery,minQueryLength)<index){	
						tempSortedHotels[n]=sortedHotels[i];
						loopPosition=i;
						index=sortedHotels[i].indexOfHotelName((items[k].searchQuery),minQueryLength);
						//System.out.println(sortedHotels[i].getHotelName()+ "index "+index +" posioin n "+n+" query "+items[k].searchQuery);
					}else{
						//System.out.println("Higher index");
					}
					
				}
				for(int x=loopPosition;x<30;x++){
					//System.out.println(x);
					if(sortedHotels[x]!=null&&sortedHotels[x+1]!=null){
						sortedHotels[x]=sortedHotels[x+1];
						//sortedHotels[x+1]=null;
					}else if(sortedHotels[x+1]==null&&sortedHotels[x]!=null){
						sortedHotels[x]=null;
						break;
					}else{
						break;
					}
				}
				if(tempSortedHotels[n]==null){
					break;
				}
			}
		}
		
		//Hotel [] tempSortedHotels=new Hotel[arrayLengths];
			
		for(int i=0;tempSortedHotels[i]!=null;i++){
			//System.out.println(tempSortedHotels[i].getHotelName()+" index "+i+" slutpunkt");
		}
		
		sortedHotels=tempSortedHotels;
		//System.out.println("done");
		return sortedHotels;
	}
}
