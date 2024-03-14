package docs.builder;

import org.springframework.restdocs.payload.JsonFieldType;

public interface Field {

    Field desc(String desc);

    Field optional();

    FieldGetter toGetFiled();

}
