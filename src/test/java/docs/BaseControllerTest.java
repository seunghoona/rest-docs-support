package docs;


import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;


class BaseControllerTest extends BaseTest{

	@Test
	void 요청값이_없는_조회() {
		var 문서 = RestAssuredRestDocumentationWrapper.document(
			"요청값이_없는_조회",
			responseFields(fieldWithPath("name").type(JsonFieldType.STRING)
				.description("응답 name"))
		);

		customGivenWithDocs(문서)
			.when()
			.get("sample")
			.then()
			.statusCode(200);
	}

	@Test
	void pathParamter요청_있는_다건_조회() {
		var 문서 = RestAssuredRestDocumentationWrapper.document(
			"요청값이_있는_다건_조회",
			pathParameters(parameterWithName("id").description("아이디")),
			responseFields(fieldWithPath("name").type(JsonFieldType.STRING)
				.description("응답 name"))
		);

		customGivenWithDocs(문서).get(
			"sample/{id}",
			1L
		);
	}

	@Test
	void queryParam_요청() {
		var 문서 = RestAssuredRestDocumentationWrapper.document(
			"queryParam_요청",
			queryParameters(parameterWithName("sortType").description("오호"),
				parameterWithName("name").description("오호")),
			responseFields(fieldWithPath("name").type(JsonFieldType.STRING)
				.description("응답 name"))
		);

		customGivenWithDocs(문서)
			.queryParam("name", "이름")
			.queryParam("sortType", "DESC")
			.get("/sample");
	}

	@Test
	void 데이터_저장() {
		var 문서 = RestAssuredRestDocumentationWrapper.document(
			"데이터_저장",
			requestFields(fieldWithPath("name").type(JsonFieldType.STRING).description("요청 이름")),
			responseFields(fieldWithPath("name").type(JsonFieldType.STRING)
				.description("응답 name"))
		);

		customGivenWithDocs(문서)
			.body("{\"name\" : \"하이\"}")
			.post("/sample");
	}

	@Test
	void 데이터_수정() {
		var 문서 = RestAssuredRestDocumentationWrapper.document(
			"데이터_수정",
			pathParameters(parameterWithName("id").description("아이디")),
			requestFields(fieldWithPath("name").type(JsonFieldType.STRING).description("요청 이름")),
			responseFields(fieldWithPath("name").type(JsonFieldType.STRING)
				.description("응답 name"))
		);


		customGivenWithDocs(문서)
			.body("{\"name\" : \"하이\"}")
			.patch("/sample/{id}", 1L);
	}
	
	
}