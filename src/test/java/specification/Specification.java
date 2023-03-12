package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {
    public static RequestSpecification requestSpecGet(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .build();

    }

    public static RequestSpecification requestSpecPost(String url, String body) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(url)
                .setBody(body)
                .build();
    }
}
