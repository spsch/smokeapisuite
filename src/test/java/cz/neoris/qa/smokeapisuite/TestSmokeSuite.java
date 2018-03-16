package cz.neoris.qa.smokeapisuite;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class TestSmokeSuite implements Helper {

    @Test
    public void TestGoogle(){
        given().when().get("http://google.com")
                .then().statusCode(200);
    }

    @Test()
    public void TestLoginService() {
        given().headers("X-IBM-Client-Id", IBMCLIENTID, "AppCode", APPCODE)
                .and()
                .given().contentType("application/x-www-form-urlencoded")
                .and().given().body("grant_type=password&scope=security&username="+MXUSER+"&password="+PWD+"&client_id="+IBMCLIENTID)
                .when().get(BASEURL+"/v2/secm/oam/oauth2/token")
                .then().assertThat().statusCode(200);

    }
}
