package HotelsDSSV2;

public class Hotel {

	// list of variables
	private String name = "";
	private String address = "";
	private double price; // Changed to string
	private int stars;
	private boolean pool = false;
	private boolean gym = false;
	private boolean bar = false;
	private boolean pets = false;
	private int hotelId;

	// ny
	private String imgPath = "";

	/* AUTHOR: Emanuel Mellblom */
	/* Contributors: Emanuel Mellblom */
	// the constructor will set all variable to their proper value when the
	// object is created
	public Hotel(int hotelId, String name, String address, double price, boolean pool, boolean gym, boolean bar,
			boolean pets, int stars, String imgPath) {
		this.hotelId = hotelId;
		this.name = name;
		this.address = address;
		this.price = price;
		this.pool = pool;
		this.gym = gym;
		this.bar = bar;
		this.pets = pets;
		this.stars = stars;
		this.imgPath = imgPath;

	}

	/* AUTHOR: John Sundling */
	/* Contributors: Emanuel Mellblom */
	public Hotel(String name, String address, double price, boolean pool, boolean gym, boolean bar, boolean pets,
			int stars, String imgPath) {
		this.name = name;
		this.address = address;
		this.price = price;
		this.pool = pool;
		this.gym = gym;
		this.bar = bar;
		this.pets = pets;
		this.stars = stars;
		// ny
		this.imgPath = imgPath;
	}

	/* AUTHOR: EmanuelMellblom */
	public int getHotelId() {
		return this.hotelId;
	}

	/* AUTHOR: John Sundling */
	public String getHotelName() {
		return this.name;
	}

	/* AUTHOR: John Sundling */
	public String getHotelAddress() {
		return this.address;
	}

	/* AUTHOR: John Sundling */
	public int getStars() {
		return this.stars;
	}

	/* AUTHOR: John Sundling */
	public double getPrice() {
		return this.price;
	}

	/* AUTHOR: John Sundling */
	public boolean getPool() {
		return this.pool;
	}

	/* AUTHOR: John Sundling */
	public boolean getGym() {
		return this.gym;
	}

	/* AUTHOR: John Sundling */
	public boolean getBar() {
		return this.bar;
	}

	/* AUTHOR: John Sundling */
	public boolean getPets() {
		return this.pets;
	}

	// ny
	/* AUTHOR: Emanuel Mellblom */
	public String getImgPath() {
		return this.imgPath;
	}

	/* AUTHOR: John Sundling */
	public int indexOfHotelName(String query, int length) {

		if (getHotelName().toLowerCase().indexOf(query.toLowerCase()) > -1) {
			return this.getHotelName().toLowerCase().indexOf(query.toLowerCase());
		}
		return -1;
	}

}
