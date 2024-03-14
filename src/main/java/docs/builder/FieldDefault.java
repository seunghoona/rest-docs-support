package docs.builder;


import lombok.EqualsAndHashCode;
import org.springframework.restdocs.payload.JsonFieldType;

@EqualsAndHashCode(of = "fieldName")
public class FieldDefault implements Field, FieldGetter {

    private final String fieldName;

    private final JsonFieldType jsonFieldType;

    private String desc;

    private boolean optional;

    public FieldDefault(String fieldName, JsonFieldType jsonFieldType) {
        this(fieldName, jsonFieldType, "", false);
    }

    public FieldDefault(String fieldName, JsonFieldType jsonFieldType, String desc) {
        this(fieldName, jsonFieldType, desc, false);
    }

    public FieldDefault(String fieldName, JsonFieldType jsonFieldType, String desc, boolean optional) {
        this.fieldName = fieldName;
        this.jsonFieldType = jsonFieldType;
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
    public JsonFieldType getJsonFieldType() {
        return jsonFieldType;
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
