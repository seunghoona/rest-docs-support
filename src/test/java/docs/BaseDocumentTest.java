package docs;

import static docs.BaseDocumentFields.bool;
import static docs.BaseDocumentFields.list;
import static docs.BaseDocumentFields.string;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

class BaseDocumentTest {
	
	
	@Test
	
	void 문서만들기() {

		String s = BaseDocument.document("기본")
			.request(
				string("이름"),
				list(
					"사람",
					"이름을 하나씩 넣겠습니다."
				).with(string("id").desc("아이디"))
					.with(string("age").desc("나이"))
					.with(string("name").desc("이름")),
				string("주소")
			)
			.response(bool("deleteYn").desc("삭제여부"))
			.toString();

		System.out.println("s = " + s);
	}

}