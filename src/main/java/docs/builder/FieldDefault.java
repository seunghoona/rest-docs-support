package docs.builder;


import docs.model.JsonDocumentFieldType;
import lombok.EqualsAndHashCode;
import org.springframework.restdocs.payload.JsonFieldType;

@EqualsAndHashCode(of = "fieldName")
public class FieldDefault implements Field, FieldGetter {

    private final String fieldName;

    private final JsonDocumentFieldType jsonDocumentFieldType;

    private String desc;

    private boolean optional;

    public FieldDefault(String fieldName, JsonDocumentFieldType JsonDocumentFieldType) {
        this(fieldName, JsonDocumentFieldType, "", false);
    }

    public FieldDefault(String fieldName, JsonDocumentFieldType JsonDocumentFieldType, String desc) {
        this(fieldName, JsonDocumentFieldType, desc, false);
    }

    public FieldDefault(String fieldName, JsonDocumentFieldType JsonDocumentFieldType, String desc, boolean optional) {
        this.fieldName = fieldName;
        this.jsonDocumentFieldType = JsonDocumentFieldType;
        this.desc = desc;
        this.optional = optional;
    }

    @Override
    public FieldGetter toGetFiled() {
        return this;
    }

    @Override
    public Field desc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public Field optional() {
        this.optional = true;
        return this;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public JsonDocumentFieldType getJsonDocumentFieldType() {
        return jsonDocumentFieldType;
    }

    @Override
    public JsonFieldType getJsonFieldType() {
        return jsonDocumentFieldType.getJsonFieldType();
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }
}
