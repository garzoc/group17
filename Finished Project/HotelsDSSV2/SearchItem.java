package HotelsDSSV2;

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
	// boolean pH=false;
	private static int arrayLengths = 1000;
	private static SearchItem[] items = new SearchItem[arrayLengths];
	private static Hotel[][] hotels = new Hotel[arrayLengths][arrayLengths];
	private static Hotel[] sortedHotels = new Hotel[arrayLengths];
	Connection connection = null;
	ResultSet viewing = null;
	Statement statement = null;

	/* AUTHOR: John Sundling */
	private SearchItem(String name, int index, int minQueryLength) throws SQLException {

		try {
			Class.forName("org.sqlite.JDBC");


			connection = DriverManager.getConnection(Main.dbAddress);

			statement = connection.createStatement();

			// change the sql statements when changinh the database
			viewing = statement.executeQuery("Select * FROM Hotel");

			search(name, index, minQueryLength);
		} catch (ClassNotFoundException error) {
		} catch (SQLException error) {
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ignore) {

				}
			}
		}

	}

	private void search(String name, int index, int minQueryLength) throws SQLException {

		int loopStop = 0;
		for (int i = index + minQueryLength - 1; i < name.length() + 1; i++) {

			// change the sql statements when changinh the database
			this.viewing = statement
					.executeQuery("Select * FROM Hotel where Name LIKE '%" + name.substring(index, i) + "%'");

			if (viewing.next()) {
				// This for loop clears old reults when we find a new better
				// matching result
				for (int n = 0; n < hotels[index].length; n++) {
					hotels[index][n] = null;
				}

				// Hotel(this.viewing.getInt("HID"),this.viewing.getString("Name"),this.viewing.getString("Address"),this.viewing.getDouble("Price"),this.viewing.getBoolean("Pool"),this.viewing.getBoolean("Gym"),this.viewing.getBoolean("Bar"),this.viewing.getBoolean("Pets"),this.viewing.getInt("Stars"));
				hotels[index][hits] = new Hotel(this.viewing.getInt("HID"), this.viewing.getString("Name"),
						this.viewing.getString("Address"), this.viewing.getDouble("Price"),
						this.viewing.getBoolean("Pool"), this.viewing.getBoolean("Gym"), this.viewing.getBoolean("Bar"),
						this.viewing.getBoolean("Pets"), this.viewing.getInt("Stars"), this.viewing.getString("Img"));

				this.hits++;

				while (viewing.next()) {
					
					hotels[index][hits] = new Hotel(this.viewing.getInt("HID"), this.viewing.getString("Name"),
							this.viewing.getString("Address"), this.viewing.getDouble("Price"),
							this.viewing.getBoolean("Pool"), this.viewing.getBoolean("Gym"),
							this.viewing.getBoolean("Bar"), this.viewing.getBoolean("Pets"),
							this.viewing.getInt("Stars"), this.viewing.getString("Img"));

					this.hits++;

				}
				loopStop = i;
				hits = 0;
			} else {
				break;
			}

		}

		if (loopStop > 0) {
			this.searchQuery = name.substring(index, loopStop);
			this.accuracy = name.substring(index + minQueryLength - 1, loopStop).length() / name.length();
		} else {
			this.searchQuery = "";
			this.accuracy = 0;
			this.hits = 0;
		}
	}

	/*AUTHOR: John Sundling*/
	public String getQuery() {
		return this.searchQuery;
	}

	/*AUTHOR: John Sundling*/
	public float getAccuracy() {
		return this.accuracy;
	}

	/*AUTHOR: John Sundling*/
	public int getHits() {
		return this.hits;
	}

	
	/*AUTHOR: John Sundling*/
	static public Hotel[] locateItems(String name) throws SQLException {
		for (int i = 0; i < hotels.length; i++) {
			for (int n = 0; n < hotels[i].length; n++) {
				hotels[i][n] = null;
			}
		}

		String query = name;

		// set the minimum query length of the in and output
		int minQueryLength = 3;
		if (query.length() < 3) {
			minQueryLength = query.length();
		}
		// the variable above must always be greater than the one below
		// int minOutputLength=3;

		for (int i = 0; i < query.length() - (minQueryLength) + 1; i++) {
			items[i] = new SearchItem(query, i, minQueryLength + 1);
		}


		for (int i = 0; i < sortedHotels.length; i++) {
			sortedHotels[i] = null;
		}

		int test = 0;
		for (int i = 0; i < hotels.length; i++) {
			for (int n = 0; n < hotels[i].length; n++) {
				if (hotels[i][n] != null) {
					for (int x = 0; x < sortedHotels.length; x++) {
						if (sortedHotels[x] != null) {
							if (hotels[i][n].getHotelId() == sortedHotels[x].getHotelId()) {
								test++;
								break;
							}

						} else {

							sortedHotels[x] = hotels[i][n];
							test++;
							break;
						}
					}
				}
			}
		}
		
		for(int i=0;sortedHotels[i]!=null;i++){
			System.out.println(sortedHotels[i].getHotelName());
		}

		Hotel[] tempSortedHotels = new Hotel[arrayLengths];

		// place names in alphabetic order
		for (int k = 0; k < arrayLengths && items[k] != null; k++) {
			System.out.println(items[k].searchQuery);
			for (int n = 0; n < arrayLengths; n++) {
				// The recomended max length of an the sorter may not work
				// otherwise
				int index = 10000;
				// keeping track of which position to clear
				int loopPosition = 0;
				for (int i = 0; sortedHotels[i] != null; i++) {
					if (sortedHotels[i].indexOfHotelName(items[k].searchQuery, minQueryLength) < index) {
						tempSortedHotels[n] = sortedHotels[i];
						loopPosition = i;
						index = sortedHotels[i].indexOfHotelName((items[k].searchQuery), minQueryLength);
					} else {
					}

				}
				for (int x = loopPosition; x < arrayLengths; x++) {
					if (sortedHotels[x] != null && sortedHotels[x + 1] != null) {
						sortedHotels[x] = sortedHotels[x + 1];
					} else if (sortedHotels[x + 1] == null && sortedHotels[x] != null) {
						sortedHotels[x] = null;
						break;
					} else {
						break;
					}
				}
				if (tempSortedHotels[n] == null) {
					break;
				}
			}
		}

		sortedHotels = tempSortedHotels;
		return sortedHotels;
	}
}
