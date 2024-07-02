package api.test;

import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.payload.User;
import api.endpoints.UserEndpoints;
import api.utilities.DatabaseUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Allergy_Test {
	
	public Logger logger;
	
	@Test(priority=2)
	public void testGetPatientIDFromDB() throws Exception {
		String query = "SELECT * FROM fhir_db.allergy limit 1;";
        List<Map<String, String>> data = DatabaseUtils.getDataFromDatabase(query);
        System.out.println(data);
        logger.info("******************** Read Patient ID from Allergy Resoiurces ********************* ");
        for (Map<String, String> row : data) {
            String patientname = row.get("Patient_name");
            //System.out.println(patientname);
            Response response = UserEndpoints.readUser(patientname);
    		response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);

//            // Further assertions based on the response and database data
        String responseBody = response.getBody().asString();
        System.out.println("Response for patient_id " + patientname + ": " + responseBody);
        logger.info("******************** Patient ID is dispalyed  ********************* ");
            // Add more assertions as needed to validate the response
	    }
	}

}
