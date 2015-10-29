//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java

public class Hotel{
	
	
	//list of variables
	private String name="";
	private String address="";
	private double price=0;
	private boolean pool=false;
	private boolean gym=false;
	private boolean bar=false;
	private boolean pets=false;
	private boolean popularity=false;
	//-----------
	
	//the constructor will set all variable to their proper value when the object is created
	public Hotel(){
		
	}
	
	public String getHotelName(){
		return this.name;
	}
	
	public String getHotelAddress(){
		return this.address;
	}
	//create a getMethod for each of the variables
	
	

}
