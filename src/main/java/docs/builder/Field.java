package docs.builder;

public interface Field {

    Field desc(String desc);

    Field optional();

    FieldGetter toGetFiled();

}
