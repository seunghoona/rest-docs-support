package docs.docs;

import static docs.BaseDocumentFields.list;
import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;
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
    //    final var documentationFilter = BaseDocument
    //        .document("리스트")
    //        .requestBody(
    //            list("list").desc("리스트")
    //        )
    //        .response(type("contentType")
    //            .desc("컨텐츠")
    //            .with(string("key").desc("key"))
    //            .with(string("comment").desc("comment"),
    //                object("headers")
    //                    .with(string("code").desc("key"))
    //                    .with(string("message").desc("comment"))))
    //        .end();

    @Test
    void nestingList() {

        // when
        final var documentationFilter = BaseDocument
            .document("리스트")
            .requestBody(
                list("[][]").desc("리스트").with()
            )
            .response(object("headers")
                .with(string("code").desc("code"))
                .with(string("message").desc("comment")))
            .end();

        given()
            .document(documentationFilter)
            .api()
            .body(""" 
                         
                         [
                             ["안녕"],
                             ["안녕"]
                         ]
                         
                """)
            .post("sample/list");
    }


}