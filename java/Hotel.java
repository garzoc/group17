//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java

public class Hotel{
	
	
	//list of variables
	private String name="";
	private String address="";
	private int price=0;
	private int stars=0;
	private boolean pool=false;
	private boolean gym=false;
	private boolean bar=false;
	private boolean pets=false;
	//private boolean popularity=false;
	//-----------
	
	//the constructor will set all variable to their proper value when the object is created
	public Hotel(String name, String address,int price,boolean pool,boolean gym,boolean bar, boolean pets,int stars){
		this.name=name;
		this.address=address;
		this.price=price;
		this.pool=pool;
		this.gym=gym;
		this.bar=bar;
		this.pets=pets;
		this.stars=stars;
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
	public int getPrice(){
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
	
	//create a getMethod for each of the variables
	
	

}
