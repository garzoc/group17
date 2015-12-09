package HotelsDSSV2;

import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java
public class Account{

	private final Review[] reviews=new Review[20];
	private final String userName;
	private final String password;
	
	private final String accountType;
	private final int userId;
	//email currently not in use
	private String email=null;
	
	//login as user
	public Account(String userName, String password, String accountType, int userId){
		this.userName = userName;
		this.password = password;
		this.accountType = accountType;
		this.userId = userId;
		
		
	}
	
	//creating a user
	public Account(String userName,String password) {

			this.userName = userName;
			this.password = password;
			this.userId = -1;
			this.accountType = null;
		
	}
	
	public String getUserType(){
		return this.accountType;
	}
	
	public int getUserId(){
		return this.userId;
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
