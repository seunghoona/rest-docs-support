package docs;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public class BaseTest {

	protected RequestSpecification spec;

	@LocalServerPort
	private int port;

	private final boolean showLog = true;

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		RestAssured.port = port;
		this.spec = new RequestSpecBuilder()
			.addFilter(documentationConfiguration(restDocumentation))
			.build();
	}

	protected RequestSpecification customGivenWithDocs(Filter document) {
		final RequestSpecification customGiven = given(spec)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.filter(document);

		if (showLog) {
			return customGiven.log()
				.all();
		}
		return customGiven;
	}


}
