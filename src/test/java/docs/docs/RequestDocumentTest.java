package docs.docs;

import static docs.BaseDocumentFields.list;
import static docs.BaseDocumentFields.string;
import static docs.docs.BaseDocument.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.BaseTest;
import docs.builder.FieldDefault;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;


class RequestDocumentTest extends BaseTest {

    @Test
    void queryParam_생성() {

        // given
        final var name = new FieldDefault("name", JsonFieldType.STRING);

        // when
        final var documentationFilter = document("요청_생성")
            .queryParam(string("name").desc("이름1"))
            .response(string("price").desc("가격"),
                      list("samples")
                          .desc("샘플")
                          .with(string("orderName").desc("주문이름")))
            .end();

        final var document = RestAssuredRestDocumentationWrapper.document("설명",

                                                                          request -> {
                                                                              RequestDocumentation.queryParameters(
                                                                                  parameterWithName(
                                                                                      "name").description(
                                                                                      "이름"));
                                                                          }, response -> {
                PayloadDocumentation.responseFields(fieldWithPath("data").description("데이터"),
                                                    fieldWithPath("data.price").description("가격"));

            });

        customGivenWithDocs(documentationFilter)
            .queryParam("name", "친구")
            .get("/sample");

    }


}