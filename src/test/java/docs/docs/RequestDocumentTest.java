package docs.docs;

import static docs.BaseDocumentFields.*;
import static docs.docs.BaseDocument.document;

import docs.builder.FieldDefaultBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;


class RequestDocumentTest {

    @Test
    void queryParam_생성() {

        // given
        final var name = new FieldDefaultBuilder("name", JsonFieldType.STRING);

        // when
            document("요청_생성")
            .request(string("name1").desc("이름1"))
            .request(string("name2").desc("이름2"))
            .response(string("name").desc("응답"))
            .end();
    }

}