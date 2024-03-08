package docs;


import static docs.BaseDocumentFields.list;
import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import docs.DocumentField.FieldCollection;
import org.junit.jupiter.api.Test;


class BaseControllerTest extends BaseTest {

	@Test
	void 요청이_없는_응답조회() {
		var 문서 = BaseDocument.document("요청값이 없는 조회")
			.response(
				string("price").desc("가격"),
				list("samples").desc("샘플")
					.with("orderName", "주문이름"))
			.end();

		customGivenWithDocs(문서).when()
			.get("sample")
			.then()
			.statusCode(200);
	}

	@Test
	void 요청이_없고_응답이_없는조회() {
		var 문서 = BaseDocument.document("요청값이 없는 조회")
			.response()
			.end();

		customGivenWithDocs(문서).when()
			.get("sample/nodata")
			.then()
			.statusCode(200);
	}

	@Test
	void pathParameter_조회() {

		var 문서 = BaseDocument.document("pathParameter_조회")
			.pathParam(string("id").desc("아이디"))
			.response(
				string("price").desc("가격"),
				list("samples").desc("샘플")
					.with("orderName", "주문이름"))
			.end();

		customGivenWithDocs(문서).get(
			"sample/{id}",
			1L
		);
	}

	@Test
	void queryParam_조회() {
		var 문서 = BaseDocument.document("QueryParam 조회")
			.queryParam(
				string("name").desc("요청 이름"),
				string("sortType").desc("정렬")
			)
			.response(
				string("price").desc("가격"),
				list("samples").desc("샘플")
					.with("orderName", "주문이름"))
			.end();

		customGivenWithDocs(문서).queryParam(
				"name",
				"이름"
			)
			.queryParam(
				"sortType",
				"DESC"
			)
			.get("/sample");
	}

	@Test
	void 데이터_저장() {

		var 문서 = BaseDocument.document("데이터 저장")
			.requestBody(string("name").desc("요청 이름"))
			.response(
				string("price").desc("가격"),
				list("samples").desc("샘플")
					.with("orderName", "주문이름"))
			.end();

		customGivenWithDocs(문서).body("{\"name\" : \"하이\"}")
			.post("/sample");
	}

	@Test
	void 데이터_수정() {



		var 문서 = BaseDocument.document("데이터 수정")
			.pathParam(string("id").desc("아이디"))
			.requestBody(string("name").desc("요청 이름"))
			.response(
				string("price").desc("가격"),
				list("samples").desc("샘플")
					.with("orderName", "주문이름")

			)
			.end();

		customGivenWithDocs(문서).body("{\"name\" : \"하이\"}")
			.patch(
				"/sample/{id}",
				1L
			);
	}


}