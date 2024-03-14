package docs.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

class JsonDocumentFieldTypeTest {


    @Test
    void 문서타입에_JSONFieldType_valueOf시_형변환() throws IllegalAccessException {

        final var type = JsonDocumentFieldType.valueOf(JsonFieldType.NULL);

        Assertions.assertEquals(JsonDocumentFieldType.NULL, type);

    }

}