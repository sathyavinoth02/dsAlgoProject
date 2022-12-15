package stepDefinition;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import utilities.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.SigninPage;
import utilities.Loggerload;

public class Signin_SD {

	SigninPage sign=new SigninPage();
	static String username;
	static String password;
	static String message;
	
	//User is on Sigin page
	@Given("The user is on signin page")
	public void the_user_is_on_signin_page() {
		
	    Loggerload.info("User is on Sigin Page");
		sign.login_page();
	}

	//invalid data from scenario outline
		@When("The user enter invalid {string} and {string}")
		public void the_user_enter_invalid_and(String username, String password) {
			Loggerload.info("User Enter username as \" "+username+" \" and Password as \" "+password+"\" ");
			sign.doLogin(username,password); 
			
		}
		
		@Then("click login button to verify")
		public void click_login_button_to_verify()  {

			Loggerload.info("User clicks on login button");
			sign.login_button();
		}
		
	//user read both invalid and valid data from excel 
	@When("The user enter sheet {string} and {int}")
	public void the_user_enter_sheet_and(String sheetname, Integer rownumber) throws InvalidFormatException, IOException {
	    ExcelReader reader=new ExcelReader();
		List <Map<String,String>> testdata=
				reader.getData("C:\\Users\\sathy\\work\\SathyaWorkspace\\dsAlgoProject\\src\\test\\resources\\Exceldata\\Login.xlsx",sheetname);
		
		username=testdata.get(rownumber).get("username");
		password=testdata.get(rownumber).get("password");
		message=testdata.get(rownumber).get("expectedmessage");
		
		Loggerload.info("User Enter username as \" "+username+" \"and Password as \" " +password+"\" ");
		if(username!=null || password!=null)
		sign.doLogin(username,password); 
		
	}

	//user get an alert message when login with invalid data and login successfully for valid data
	@Then("click login button")
	public void click_login_button() {
	   
		Loggerload.info("User clicks on login button");
		Loggerload.info("Expected Message - Excel Sheet :  "+message);
		String msg=sign.click_login();
		Loggerload.info("Actual Message :  "+msg);
		assertEquals(msg, message);
		
		
	}
	
	//register link
	@When("The user clicks on register link on signin page")
	public void the_user_clicks_on_register_link_on_signin_page() {
		Loggerload.info("User clicks on Register ");
	    sign.register_link();
	}

	@Then("The user redirected to Registration page from signin page")
	public void the_user_redirected_to_registration_page_from_signin_page() {
		Loggerload.info("User redirected to Registraion page ");
		String Title=sign.register_page();
		Loggerload.info("Title of the Page : \" " +Title +"\" ");
		assertEquals(Title, "Registration", "pass");
	}


}