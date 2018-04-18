package cz.neoris.qa.smokeapisuite;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginMethods implements Helper {


    private void AuthenticateUser(String username, String pwd) {

        String response =
                given()
                    .parameters(
                            "grant-type", "password",
                            "scope", "security",
                            "&username", MXUSER,
                            "&password", PWD,
                            "client_id", IBMCLIENTID)
                    .auth()
                    .preemptive()
                    .basic(MXUSER, PWD)
                .when()
                    .post(BASEURL+"/v2/secm/oam/oauth2/token").asString();

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        String jwtToken = jsonPath.getString("jwt");
        System.out.println("AUTH: " + accessToken + "JWT: " + jwtToken);

    }



    @Test
    public void LoginWithClass(){
        AuthenticateUser(MXUSER, PWD);

        when().get(BASEURL + "/v1/secm/applications/menu").then().log().ifError().statusCode(200);
    }

    @Test
    public void TestGoogle(){
        given().log().all().when().get("http://google.com")
                .then().statusCode(200);
    }

    @Test
    public void TestLoginService() {
        given().log().all().accept("text/plain, */*")
                .headers(
                        "App-Code", APPCODE,
                        "X-IBM-Client-Id", IBMCLIENTID
                )
                .and().given().contentType("application/x-www-form-urlencoded")
                .and().given().body("grant_type=password&scope=security&username="+MXUSER+"&password="+PWD+"&client_id="+IBMCLIENTID)
                .when().post(BASEURL+"/v2/secm/oam/oauth2/token")
                .then().log().ifError().assertThat().statusCode(200);
               /* .contentType(ContentType.JSON).extract().path("access_token").toString();*/


    }

}
