package docs;

import docs.docs.Document;
import docs.docs.RequestDocument;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class ApiTest {

    RequestSpecification requestSpecification;

    public ApiTest(RequestSpecification given) {
        this.requestSpecification = given;
    }

    public RequestSpecification api() {
        return  this.requestSpecification;
    }

    public ApiTest document(RestDocumentationFilter restDocumentationFilter) {
        this.requestSpecification.filter(restDocumentationFilter);
        return this;
    }


    public ApiTest get(String url) {
        this.requestSpecification.get(URI.create(url));
        return this;
    }

    public ApiTest post(String url) {
        this.requestSpecification.get(url);
        return this;
    }

    public ApiTest patch(String url) {
        this.requestSpecification.get(url);
        return this;
    }

    public ApiTest delete(String url) {
        this.requestSpecification.get(url);
        return this;
    }
}
