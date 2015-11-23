import java.sql.Connection;
//import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SearchItem {
	int hits;
	float accuracy;
	String searchQuery;
	//boolean pH=false;
	static SearchItem[] items=new SearchItem[100];
	static Hotel[][] hotels= new Hotel[350][350];
	static Hotel[] sortedHotels= new Hotel[350];
	Connection connection=null;
	ResultSet viewing=null;
	Statement statement=null;
	private SearchItem(String name,int index,int minQueryLength) throws SQLException{
		try{
			Class.forName("org.sqlite.JDBC");
			connection=DriverManager.getConnection("jdbc:sqlite:/Users/john/Desktop/Test1.sqlite");
			//System.out.println(connection.getClientInfo());
			statement=connection.createStatement();
			viewing=statement.executeQuery("Select * FROM Type");
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

			this.viewing=statement.executeQuery("Select * FROM Type where query LIKE '%"+name.substring(index,i)+"%'");
			//System.out.println(name.substring(index,i));
			if(viewing.next()){
				//hotels[index][hits]=new Hotel(viewing.getInt("HID"),viewing.getString("Name"),viewing.getString("Address"),viewing.getInt("Price"),viewing.getBoolean("Pool"),viewing.getBoolean("Gym"),viewing.getBoolean("Bar"),viewing.getBoolean("Pets"),viewing.getInt("Stars"));

				hotels[index][hits]=new Hotel(this.viewing.getInt("Id"),this.viewing.getString("query"));
//				System.out.println(hotels[index][hits].getHotelName()+" whith index "+index+"\n");
				this.hits++;
				//System.out.println(this.viewing.getString("query"));
				//System.out.println(hits+ " hits");
				while(viewing.next()){
				//	hotels[index][hits]=new Hotel(viewing.getInt("HID"),viewing.getString("Name"),viewing.getString("Address"),viewing.getInt("Price"),viewing.getBoolean("Pool"),viewing.getBoolean("Gym"),viewing.getBoolean("Bar"),viewing.getBoolean("Pets"),viewing.getInt("Stars"));
					hotels[index][hits]=new Hotel(this.viewing.getInt("Id"),this.viewing.getString("query"));
//					System.out.println("wazzaaa");
					//System.out.println(hotels[index][hits].getHotelName()+" whith index "+index+"\n");
					
					//hotels[index][hits].setAccuracy(name.substring(index+minQueryLength-1,i).length()/name.length());
					this.hits++;
					//System.out.println(hits);
					
					
				}
				System.out.println("start "+index+" Stop "+i+" string "+name.substring(index,i));
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
	
	
	static public Hotel[] locateItems() throws SQLException{
		//SearchItem[] items=new SearchItem[100];

		
		Scanner sc = new Scanner(System.in);
		System.out.println("tjena");
		String query=sc.next();
		sc.close();
		
		//set the minimum query length of the in and output
		int minQueryLength=3;

		
		for(int i=0;i<query.length()-(minQueryLength)+1;i++){
		
			items[i]=new SearchItem(query,i,minQueryLength+1);
			//System.out.println("Changing index to  "+(i+1));
			
			
		}
//		System.out.println(hotels[0][0].getHotelName());
//		System.out.println(hotels[0][1].getHotelName());
//		System.out.println(hotels[0][2].getHotelName());
//		System.out.println(hotels[2][0].getHotelName());
//		System.out.println(hotels[1][1].getHotelName());
//		System.out.println(hotels[1][2].getHotelName());
//		System.out.println(hotels[1][3].getHotelName());
//		try{
//		System.out.println("hotel   "+hotels[2][0].getHotelName());
//		}catch(NullPointerException e){}
//		try{
//		System.out.println("hotel   "+hotels[0][1].getHotelName());
//		}catch(NullPointerException e){}
//		try{
//		System.out.println("hotel   "+hotels[0][2].getHotelName());
//		}catch(NullPointerException e){}
//		try{
//		System.out.println("hotel   "+hotels[0][3].getHotelName());
//		}catch(NullPointerException e){}
//		
		System.out.println("Finnishing up the search");
	
		
		for(int i=0;i<hotels.length;i++){
			for(int n=0;n<hotels[i].length;n++){
				if(hotels[i][n]!=null){
					//System.out.println("okay");
					for(int x=0;x<sortedHotels.length;x++){
						if(sortedHotels[x]!=null){
							if(hotels[i][n].getHotelId()==sortedHotels[x].getHotelId()){
								break;
							}
							
							
						}else{
						
							sortedHotels[x]=hotels[i][n];
							break;
						}
					}
				}
			}
		}
		
		//System.out.println(sortedHotels[2].getHotelName());
		
		for(int i=0;sortedHotels[i]!=null;i++){
			System.out.println(sortedHotels[i].getHotelName());
		}
		System.out.println("done");
		return sortedHotels;
	}
}
