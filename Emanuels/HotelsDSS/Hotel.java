package HotelsDSS;

public class Hotel {
	
	//list of variables
	private String name="";
	private String address="";
	private double price; //Changed to string
	private int stars;
	private boolean pool=false;
	private boolean gym=false;
	private boolean bar=false;
	private boolean pets=false;
	private int hotelId;
	
	//ny
	private String imgPath ="";
	//private boolean popularity=false;
	
	//the constructor will set all variable to their proper value when the object is created
	public Hotel(int hotelId, String name, String address,double price,boolean pool,boolean gym,boolean bar, boolean pets,int stars,String imgPath){
		this.hotelId = hotelId;
		this.name=name;
		this.address=address;
		this.price=price;
		this.pool=pool;
		this.gym=gym;
		this.bar=bar;
		this.pets=pets;
		this.stars=stars;
		//ny
		this.imgPath = imgPath;
		
	}
	
	public Hotel(String name, String address,double price,boolean pool,boolean gym,boolean bar, boolean pets,int stars,String imgPath){
		this.name=name;
		this.address=address;
		this.price=price;
		this.pool=pool;
		this.gym=gym;
		this.bar=bar;
		this.pets=pets;
		this.stars=stars;
		//ny
		this.imgPath = imgPath;
	}
	public int getHotelId(){
		return this.hotelId;
	}
	
	public String getHotelName(){
		return this.name;
	}
	public String getHotelAddress(){
		return this.address;
	}
	public int getStars(){
		return this.stars;
	}
	public double getPrice(){
		return this.price;
	}
	public boolean getPool(){
		return this.pool;
	}
	public boolean getGym(){
		return this.gym;
	}
	public boolean getBar(){
		return this.bar;
	}
	public boolean getPets(){
		return this.pets;
	}
	//ny
	public String getImgPath(){
		return this.imgPath;
	}
	public int indexOfHotelName(String query, int length){
		for(int i = 0; i<this.getHotelName().length()-length;i++){
			if(this.getHotelName().substring(i, i+length).toLowerCase().equals(query.toLowerCase())){
				return i;
			}
		}
		return -1;
	}

}
