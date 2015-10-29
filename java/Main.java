
//javac Main.java Account.java HotelOwner.java Review.java Hotel.java Administrator.java SimpleIo.java
public class Main{
	
	public static void main(String[] args){
		SimpleIo console=new SimpleIo();
		System.out.println("welcome to group3 demo project");
		int selection=Integer.parseInt(console.readLine("1:Create account \n2:Login \n"));
		Account user;
		switch(selection){
			case 1:
				user=new Account(console.readLine("Username:"),console.readLine("Password:"));
				break;
			case 2:
				user=new Account(console.readLine("UserName:"));
				//user.exist();
				break;
			default:
				user=new Account("none");
				System.exit(0);
				break;
		}
		
		System.out.println("Hi "+user.getUserName()+" please enter your password");
		if(user.verifyPassword(console.readLine())){
			System.out.println("success");
			
		}else{
			System.out.println("Wrong username or password");
		}
		
		System.out.println("1:change password \n2:change userName"+user.getAdditionalOptions());
		switch(Integer.parseInt(console.readLine())){
			case 1:
				user.changeUserName(console.readLine("Enter Password"),console.readLine("Enter new name"));
				user=new Account(user.getUserName(),console.readLine("Enter you password to login"));
				break;
			case 2:
				user.changePassword(console.readLine("Old password"),console.readLine("new Password"));
				break;
			default:
				user.callAditionalOption(3);
		}
		
		
	}
}
