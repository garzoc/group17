package HotelsDSS;

import java.sql.SQLException;

public class HotelSearch {
	static private Hotel[] processedHotel=new Hotel[100];
	//static public Hotel[] searchHotelsBy(String query,int hid,String name,String address,double price,int stars,boolean pool,boolean gym,boolean bar,boolean pets,String img) throws SQLException{

	static public Hotel[] searchHotelsBy(double priceFrom, double priceTo, int stars, boolean bar, boolean pool, boolean gym, boolean pets, String name) throws SQLException{
		Hotel[] hotels=new Hotel[100];
		//System.out.println(stars);
		hotels=SearchItem.locateItems(name);
		for(int i=0;i<hotels.length;i++){
			//System.out.println(hotels[i] + "hejsan");
			if(hotels[i] != null){
			if(priceFrom <= hotels[i].getPrice() && priceTo >= hotels[i].getPrice() && hotels[i].getStars() == stars && hotels[i].getBar() == bar && hotels[i].getPool() == pool && hotels[i].getGym() == gym && hotels[i].getPets() == pets){
				for(int n=0;n<processedHotel.length;n++){
					if(processedHotel[n]==null){
						processedHotel[n]=hotels[i];
						break;
					}
				}
				
			}
		}else{
			break;
		}
		}
		//upplikasaii
		return processedHotel;
	}

	
}
