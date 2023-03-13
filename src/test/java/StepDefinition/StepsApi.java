package StepDefinition;

import io.cucumber.java.ru.И;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specification.Specification.requestSpecGet;
import static specification.Specification.requestSpecPost;
import static utils.PropConf.getProperty;

public class StepsApi {
    public static int lastEpisodeNumber;
    public static String speciesCharacter;
    public static String locationCharacter;
    public static int lastCharacterNumber;
    public static String speciesLastCharacter;
    public static String locationLastCharacter;

    @И("^Получить данные по персонажу: (.*)")
    public static void getInformationCharacter(String character) {
        Response getCharacter = given()
                .spec(requestSpecGet(getProperty("rick.url")))
                .param("name", character)
                .when()
                .get("/character/")
                .then()
                .extract()
                .response();
        String infoNameCharacter = new JSONObject(getCharacter.getBody().asString()).getJSONArray("results")
                .getJSONObject(0).get("name").toString();
        Assertions.assertEquals(character, infoNameCharacter, "Ошибка, найден персонаж: " + infoNameCharacter);
        int episode = (new JSONObject(getCharacter.getBody().asString()).getJSONArray("results")
                .getJSONObject(0).getJSONArray("episode")).length() - 1;
        speciesCharacter = new JSONObject(getCharacter.getBody().asString()).getJSONArray("results")
                .getJSONObject(0).get("species").toString();
        locationCharacter = new JSONObject(getCharacter.getBody().asString()).getJSONArray("results")
                .getJSONObject(0).getJSONObject("location").get("name").toString();
        lastEpisodeNumber = Integer.parseInt(new JSONObject(getCharacter.getBody().asString())
                .getJSONArray("results")
                .getJSONObject(0)
                .getJSONArray("episode")
                .get(episode).toString().replaceAll("[^0-9]", ""));
        Allure.addAttachment("Раса", speciesCharacter);
        Allure.addAttachment("Локация", locationCharacter);
    }

    @И("Получить из списка последнего эпизода, ID последнего персонажа")
    public static void getLastCharacter() {
        Response getLastCharacter = given()
                .spec(requestSpecGet(getProperty("rick.url")))
                .when()
                .get("/episode/" + lastEpisodeNumber)
                .then()
                .extract()
                .response();
        int character = (new JSONObject(getLastCharacter.getBody().asString()).getJSONArray("characters")).length() - 1;
        lastCharacterNumber = Integer.parseInt(new JSONObject(getLastCharacter.getBody().asString())
                .getJSONArray("characters")
                .get(character).toString().replaceAll("[^0-9]", ""));
        Allure.addAttachment("ID последнего персонажа", String.valueOf(lastCharacterNumber));
    }

    @И("^Получить данные последнего персонажа и сравнить с персонажем: (.*)")
    public static void getInformationLastCharacter(String character) {
        Response getCharacterInfo = given()
                .spec(requestSpecGet(getProperty("rick.url")))
                .when()
                .get("/character/" + lastCharacterNumber)
                .then()
                .extract()
                .response();
        String infoNameLastCharacter = new JSONObject(getCharacterInfo.getBody().asString()).get("name").toString();
        speciesLastCharacter = new JSONObject(getCharacterInfo.getBody().asString()).get("species").toString();
        locationLastCharacter = new JSONObject(getCharacterInfo.getBody().asString()).getJSONObject("location").get("name").toString();
        Allure.addAttachment("Имя последнего персонажа", String.valueOf(infoNameLastCharacter));
        Allure.addAttachment("Раса", speciesLastCharacter);
        Allure.addAttachment("Локация", locationLastCharacter);
        Assertions.assertAll("Проверка совпадения информации по персонажам: " + character + " и " + infoNameLastCharacter,
                () -> assertEquals(speciesCharacter, speciesLastCharacter, "Ошибка, не совпадает"),
                () -> assertEquals(locationCharacter, locationLastCharacter, "Ошибка, не совпадает")
        );
    }

    @И("Отправить запрос на reqres.in и проверить результаты")
    public static void createUserInReqres(Map<String, String> arg) throws IOException {
        JSONObject body = new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/json/Potato.json"))));
        arg.forEach(body::put);
        Response postCreateUser = given()
                .spec(requestSpecPost(getProperty("reqres.url"), body.toString()))
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();
        List<String> key = new ArrayList<>(arg.keySet());
        Assertions.assertEquals(body.get(key.get(0)), new JSONObject(postCreateUser.getBody().asString()).get(key.get(0)), "Ошибка, не совпадает");
        Assertions.assertEquals(body.get(key.get(1)), new JSONObject(postCreateUser.getBody().asString()).get(key.get(1)), "Ошибка, не совпадает");
    }
}
