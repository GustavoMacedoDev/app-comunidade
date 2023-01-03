package br.com.macedo.sistemas.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class TipoLogradouroControllerTest {

    String jsonEntrada;

    @BeforeEach
    void setup() {
        jsonEntrada =
                "{\n" +
                    "  \"nome\": \"string\"\n" +
                "}";
    }

    @Test
    @DisplayName("Testa retorno 201 cadastra tipo de logradouro")
    void testaRetorno201CadastroTipoLogradouro() {
        given()
                .body(jsonEntrada)
                .contentType(ContentType.JSON)
                .when()
                .post("/tipologradouro")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }


}
