import java.io.Console;
//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java
public class Account{
	//imaginary database
	private String databasePassword="1234";
	private String databaseName="Hans";
	//-----------------------
	private final Review[] reviews=new Review[20];
	private final String userName;
	private final String password;
	//email currently not in use
	private String email=null;
	
	//login as user
	public Account(String userName){
		//look for the username in the database
		//if username is found set
		if(userName.equals(this.databaseName)){
			this.userName=this.databaseName;
			this.password=this.databasePassword;
		}else{
			//else set
			this.userName=null;
			this.password=null;
		}
	}
	
	//creating a user
	public Account(String userName,String password){
		//Console console=System.console();
		this.userName=userName;
		//System.out.println("Password");
		this.password=password;
	}
	
	//check if the user was found it the database
	public boolean userExist(){
		if(this.userName==null){
			return false;
		}else{
			return true;
		}
	}
	
	//check the users password
	public boolean verifyPassword(String password){
		if(password.equals(password)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public void callAditionalOption(int i){
		
	}
	
	//list of options for the user
	public String getAdditionalOptions(){
		return "";
	}
	
	public void changeUserName(String password,String NewName){
		if(this.verifyPassword(password)){
			
		}else{
			
		}
	}
	
	public void changePassword(String oldPassword,String newPassword){
		if(this.verifyPassword(oldPassword)){
			
		}else{
			
		}
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public void removeReview(){
		
	}
	
	//get multiple reviews from database and store them in the array reviews
	public void getReviews(){
		
	}
	
	//return a specific review
	public Review getReview(){
		return reviews[0];
	}
	


	
}
