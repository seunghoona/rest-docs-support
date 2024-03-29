package docs.builder;

import java.util.List;

public class FieldUnrWrap implements Fields {

    private final Fields field;

    public FieldUnrWrap(Fields field) {
        this.field = field;
    }

    @Override
    public Fields desc(String desc) {
        return field.desc(desc);
    }

    @Override
    public Fields optional() {
        return field.optional();
    }

    @Override
    public FieldGetter toGetFiled() {
        return field.toGetFiled();
    }

    @Override
    public Fields with(Field... fields) {
        return field.with(fields);
    }

    @Override
    public List<Field> getFields() {
        return field.getFields();
    }

    @Override
    public Field getRootField() {
        return field.getRootField();
    }
}
