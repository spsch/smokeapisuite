package cz.neoris.qa.smokeapisuite;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;

public class TestSmokeSuite implements Helper {

 /*this service returns acces token*/
    public String LoginServiceToken(String token) {
        return token =
                given().log().all().accept("text/plain, */*")
                        .headers(
                                "App-Code", APPCODE,
                                "X-IBM-Client-Id", IBMCLIENTID
                        )
                        .and().given().contentType("application/x-www-form-urlencoded")
                        .and().given().body("grant_type=password&scope=security&username="+MXUSER+"&password="+PWD+"&client_id="+IBMCLIENTID)
                        .when().post(AZUREURL+"/v2/secm/oam/oauth2/token")
                        .then().log().ifError().assertThat().statusCode(200)
                        .extract().path("oauth2.access_token").toString();

    }
 /*this service returns jwt*/
    public String LoginServiceJwt(String jwt){
        return jwt =
                given().log().all().accept("text/plain, */*")
                        .headers(
                                "App-Code", APPCODE,
                                "X-IBM-Client-Id", IBMCLIENTID
                        )
                        .and().given().contentType("application/x-www-form-urlencoded")
                        .and().given().body("grant_type=password&scope=security&username="+MXUSER+"&password="+PWD+"&client_id="+IBMCLIENTID)
                        .when().post(AZUREURL+"/v2/secm/oam/oauth2/token")
                        .then().log().ifError().assertThat().statusCode(200)
                        .extract().path("jwt").toString();
    }

    @Test
    public void TestOrderHistory(){
        String token = null;
        String jwt = null;

        System.out.println("token " + LoginServiceToken(token));
        System.out.println("jwt " + LoginServiceToken(jwt));

        /*we can try to login with new tokens*/
        given().headers("App-Code", APPCODE,
                "Authorization", "Bearer " + LoginServiceToken(token),
                "jwt", LoginServiceJwt(jwt),
                "X-*IBM-Client-Id",IBMCLIENTID)
                .when().get(AZUREURL + "/v5/sm/orders?orderType=REQ,SLS,CREQ&include=orderItem&page=1&fetch=100&businessLineCode=RMX,AGG")
                .then().log().ifError().statusCode(200).assertThat().equals("count");

    }

}
