package docs.builder;

public interface Field {

    void changeName(String fieldName);

    Field desc(String desc);

    Field optional();

    FieldGetter toGetFiled();
}
