package br.com.macedo.sistemas.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthenticationControllerTest {

    private String token;

    private String body;

    @BeforeEach
    void geraBodyCorreto() {
        body =  "{\n" +
                "  \"email\": \"g@gmail.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";
    }

    @Test
    public void testaRetorno200() {
        given()
                .body(body)
                .when().post("/auth")
                .then()
                .statusCode(200);

    }
}
