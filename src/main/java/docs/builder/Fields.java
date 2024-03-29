package docs.builder;

import java.util.List;

public interface Fields extends Field {

    Fields desc(String desc);

    Fields optional();

    FieldGetter toGetFiled();

    Fields with(Field... fields);

    List<Field> getFields();

    Field getRootField();

    default void changeName(Field field,  String fieldName) {
        field.changeName(fieldName);
    }

}
