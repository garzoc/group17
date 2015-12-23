package HotelsDSSV2;

import java.sql.SQLException;

public class HotelSearch {
	static private Hotel[] processedHotel = new Hotel[100];
	
	/*AUTHOR: John Sundling*/
	/*Contributors: Emanuel Mellblom*/
	static public Hotel[] searchHotelsBy(double priceFrom, double priceTo, int stars, boolean bar, boolean pool,
			boolean gym, boolean pets, String name) throws SQLException {
		Hotel[] hotels = new Hotel[100];
		System.out.println(pets);
		System.out.println(priceTo);
		System.out.println(stars);
		for (int i = 0; i < 100; i++) {
			processedHotel[i] = null;
		}
		hotels = SearchItem.locateItems(name);

		for (int i = 0; i < hotels.length; i++) {
			if (hotels[i] != null) {
				if (priceFrom <= hotels[i].getPrice() && priceTo >= hotels[i].getPrice()
						&& hotels[i].getStars() == stars && hotels[i].getBar() == bar && hotels[i].getPool() == pool
						&& hotels[i].getGym() == gym && hotels[i].getPets() == pets) {

					for (int n = 0; n < processedHotel.length; n++) {
						if (processedHotel[n] == null) {
							processedHotel[n] = hotels[i];
							break;
						}
					}

				}
			} else {
				break;
			}
		}
		return processedHotel;
	}

}
