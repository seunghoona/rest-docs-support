package docs.builder;

import docs.BaseDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

class DocumentBuilderTest {


    @Test
    void 중복_체크() {

        // given
        final var requestDocumentBuilder = BaseDocument.document("문서");
        final var nameStr1 = new FieldDefaultBuilder("name", JsonFieldType.STRING);
        final var nameStr2 = new FieldDefaultBuilder("name", JsonFieldType.STRING);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            requestDocumentBuilder.request(nameStr1, nameStr2);
        });

    }

    @Test
    void 중복_체크_JSON_FIELD_TYPE_이_다른_경우_예외() {

        // given
        final var requestDocumentBuilder = BaseDocument.document("문서");

        final var nameStr = new FieldDefaultBuilder("name", JsonFieldType.STRING);
        final var object = new FieldDefaultBuilder("name", JsonFieldType.OBJECT);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            requestDocumentBuilder
                .pathParam(nameStr, object)
                .request(nameStr, object)
                .response(object);


        });

    }
}