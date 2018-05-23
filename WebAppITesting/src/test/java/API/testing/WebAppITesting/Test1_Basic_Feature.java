package API.testing.WebAppITesting;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
 





import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Test1_Basic_Feature extends TestCaseBase {

	String HAPIKEY="5e7ef2c1-29d4-4037-a703-023d4b21024d";
	String companyID="";
	String contactID="";

	//3As Principle

	//Create a Company
	@Test(priority=1)
	public void verifyCreateCompany() {
		//Arrange
		logger = extent.createTest("Verify Create Company");
		String URL="https://api.hubapi.com/companies/v2/companies?hapikey="+HAPIKEY;
		String requestBody="{\"properties\": [ { \"name\": \"name\", \"value\": \"IBM\" },  {  \"name\": \"description\",  \"value\": \"GDS Bangalore\"  },{  \"name\": \"domain\",  \"value\": \"ibm.com\"  } ]}";

		//Action
		String id=given().contentType("application/json").body(requestBody)
				.when().post(URL).
		//Assert
				then().statusCode(200)
				.extract().path("companyId").toString();

		System.out.println(id);
		companyID=id;

		if(id!=null) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Company Creation - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Company Creation - FAILED", ExtentColor.RED));
		}
		
	}
	@Test(priority=2)
	public void createContac(){
		logger = extent.createTest("Verify Create Contact");
		String URL="https://api.hubapi.com/contacts/v1/contact/?hapikey="+HAPIKEY;
		String requestBody="{ \"properties\": [ { \"property\": \"email\", \"value\": \"mp@ibm.com\" }, { \"property\": \"firstname\",   \"value\": \"Manoj\" },  { \"property\": \"lastname\", \"value\": \"Kumar\"  }]}";;
		
		contactID=given().contentType("application/json").body(requestBody).when().post(URL).
		then().statusCode(200).extract().path("vid").toString();
		if(contactID!=null) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Creation - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Creation - FAILED", ExtentColor.RED));
		}
		
	}
	
	@Test(priority=3)
	public void contentAssociatedWithCompany(){
		logger=extent.createTest("Contact Associate With Company");
		String URL="https://api.hubapi.com/companies/v2/companies/"+companyID+"/contacts?hapikey="+HAPIKEY;
		System.out.println(URL);
		String cID=given().contentType("application/json").when().get(URL).then().statusCode(200).extract().path("vidOffset").toString();
		System.out.println(cID+" cID");
		System.out.println(contactID+" contactID");
		Assert.assertEquals(cID, contactID);
		if(contactID!=null) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Association With company - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Associtaion with Company - FAILED", ExtentColor.RED));
		}
	}
	@Test(priority=5)
		public void deleteCompany(){
		logger=extent.createTest("Comany Delete");
		String URL="https://api.hubapi.com/companies/v2/companies/"+companyID+"?hapikey="+HAPIKEY;
		System.out.println(URL);
		
		Response res=expect().statusCode(200).given().contentType("application/json").when().delete(URL);;
		if(res.getStatusCode()==200) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Company Deletion - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Company Deletion - FAILED", ExtentColor.RED));
		}
		
		}
	@Test(priority=4)
	public void deleteContact(){
		logger=extent.createTest("Contact Delete");
		String URL="https://api.hubapi.com/contacts/v1/contact/vid/"+contactID+"?hapikey="+HAPIKEY;
		Response res=expect().statusCode(200).given().contentType("application/json").when().delete(URL);

		if(res.getStatusCode()==200) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Contact Deletion - PASSED", ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Contact Deletion - FAILED", ExtentColor.RED));
		}
	}
	}


