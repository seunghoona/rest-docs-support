package docs.builder;

import docs.model.JsonDocumentFieldType;
import org.springframework.restdocs.payload.JsonFieldType;

public interface FieldGetter {

    String getFieldName();

    JsonDocumentFieldType getJsonDocumentFieldType();

    JsonFieldType getJsonFieldType();

    String getDesc();

    boolean isOptional();

}
