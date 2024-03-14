package docs.model;

import java.util.Arrays;
import lombok.Getter;
import org.springframework.restdocs.payload.JsonFieldType;

@Getter
public enum JsonDocumentFieldType {

    ARRAY(JsonFieldType.ARRAY),
    BOOLEAN(JsonFieldType.BOOLEAN),
    OBJECT(JsonFieldType.OBJECT),
    NUMBER(JsonFieldType.NUMBER),
    NULL(JsonFieldType.NULL),
    STRING(JsonFieldType.STRING),
    VARIES(JsonFieldType.VARIES),
    ENUM(JsonFieldType.OBJECT);

    JsonDocumentFieldType(JsonFieldType jsonFieldType) {
        this.jsonFieldType = jsonFieldType;
    }

    private final JsonFieldType jsonFieldType;

    public static JsonDocumentFieldType valueOf(JsonFieldType type) throws IllegalAccessException {

        return Arrays
            .stream(JsonDocumentFieldType.values())
            .filter(fi -> fi
                .getJsonFieldType()
                .equals(type))
            .findFirst()
            .orElseThrow(IllegalAccessException::new);
    }

}
