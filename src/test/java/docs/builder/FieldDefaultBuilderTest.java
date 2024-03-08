package docs.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

class FieldDefaultBuilderTest {


    @Test
    void 기본_필드_생성() {
        Field 문자열_필드 = new FieldDefaultBuilder("name", JsonFieldType.STRING);
        Assertions.assertEquals(문자열_필드, 문자열_필드);
    }

}