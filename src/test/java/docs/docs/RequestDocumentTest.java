package docs.docs;

import docs.builder.FieldDefaultBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;


class RequestDocumentTest {

    @Test
    void queryParam_생성() {

        // given
        final var name = new FieldDefaultBuilder("name", JsonFieldType.STRING);

        // when
        BaseDocument
            .document("요청_생성")
            .request(name, name, name)
            .response()
            .end();


    }

}