package docs.builder;


import groovy.transform.EqualsAndHashCode;
import org.springframework.restdocs.payload.JsonFieldType;

@EqualsAndHashCode
public class FieldDefaultBuilder implements Field, FieldGetter {

    private final String fieldName;
    private final JsonFieldType jsonFieldType;
    private String desc;
    private boolean optional;

    protected FieldDefaultBuilder(String fieldName, JsonFieldType jsonFieldType) {
        this(fieldName, jsonFieldType, "");
    }

    protected FieldDefaultBuilder(String fieldName, JsonFieldType jsonFieldType, String desc) {
        this.fieldName = fieldName;
        this.jsonFieldType = jsonFieldType;
        this.desc = desc;
        this.optional = false;
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
