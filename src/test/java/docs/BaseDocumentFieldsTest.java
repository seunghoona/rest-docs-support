package docs;

import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

class BaseDocumentFieldsTest {

	@Test
	void 객체_테스트() {
		BaseDocument.document("로그인")
			.request(
				string("email").desc("이메일"),
				string("password").desc("패스워드"),
				string("snsType").desc("SNS타입")
			)
			.response(

			)
			.toString();

	}
}