package docs.builder;

import docs.model.JsonDocumentFieldType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

class FieldDefaultTest {


    @Test
    void 기본_필드_생성() {
        Field 문자열_필드 = new FieldDefault("name", JsonDocumentFieldType.STRING);
        Assertions.assertEquals(문자열_필드, 문자열_필드);
    }


    @Test
    void 기본_필드_getter_값_조회() {
        Field 문자열_필드 = new FieldDefault("name", JsonDocumentFieldType.STRING);

        final var filed = 문자열_필드.toGetFiled();

        Assertions.assertEquals("name", filed.getFieldName());
        Assertions.assertEquals(JsonDocumentFieldType.STRING, filed.getJsonDocumentFieldType());
        Assertions.assertEquals("", filed.getDesc());
    }

}