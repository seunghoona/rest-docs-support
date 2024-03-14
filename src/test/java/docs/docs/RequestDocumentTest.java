package docs.docs;

import static docs.BaseDocumentFields.list;
import static docs.BaseDocumentFields.string;
import static docs.BaseDocumentFields.type;
import static docs.docs.BaseDocument.document;

import docs.BaseControllerTest;
import docs.BaseDocument;
import org.junit.jupiter.api.Test;


class RequestDocumentTest extends BaseControllerTest {

    @Test
    void queryParam_생성() {

        // when
        final var documentationFilter = document("요청_생성")
            .queryParam(string("name").desc("이름1"))
            .response(string("price").desc("가격"),
                      list("samples")
                          .desc("샘플")
                          .with(string("orderName").desc("주문이름")))
            .end();

        given()
            .document(documentationFilter)
            .api()
            .queryParam("name", "친구")
            .get("/sample");
    }

    @Test
    void path_생성() {

        // when
        final var documentationFilter = document("요청_path_param")
            .pathParam(string("path-name").desc("이름1"))
            .response(string("order").desc("주문이름"))
            .end();

        given()
            .document(documentationFilter)
            .api()
            .pathParam("path-name", "안녕")
            .get("/sample/path/{path-name}");

    }

    @Test
    void 응답값이_없는경우() {

        // when
        final var documentationFilter = BaseDocument
            .document("응답 값이 없는 경우")
            .pathParam()
            .response()
            .end();

        given()
            .document(documentationFilter)
            .api()
            .get("sample/no-data");

    }

    @Test
    void request_body() {

        // when
        final var documentationFilter = BaseDocument
            .document("응답 값이 없는 경우")
            .requestBody(string("name").desc("설명"))
            .response(type("contentType").with(string("key").desc("key"),
                                               string("comment").desc("comment")))
            .end();

        given()
            .document(documentationFilter)
            .api()
            .body("""
                          {"name": "tjfa"}
                      """)
            .post("sample/request-body");

    }


}