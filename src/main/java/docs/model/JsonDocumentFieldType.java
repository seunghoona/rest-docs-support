package docs.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;
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

    private final JsonFieldType jsonFieldType;

    JsonDocumentFieldType(JsonFieldType jsonFieldType) {
        this.jsonFieldType = jsonFieldType;
    }

    private String key;
    private String comment;

    public static JsonDocumentFieldType valueOf(JsonFieldType type) throws IllegalAccessException {

        return Arrays
            .stream(JsonDocumentFieldType.values())
            .filter(fi -> fi
                .getJsonFieldType()
                .equals(type))
            .findFirst()
            .orElseThrow(IllegalAccessException::new);
    }
    @JsonValue
    public String getKey() {
        return key;
    }

    @JsonValue
    public String getComment() {
        return comment;
    }

}
