package docs.builder;

import org.springframework.restdocs.payload.JsonFieldType;

public interface FieldGetter {

    String getFieldName();

    JsonFieldType getJsonFieldType();

    String getDesc();

    boolean isOptional();

}
