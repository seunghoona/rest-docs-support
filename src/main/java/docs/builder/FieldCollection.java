package docs.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.restdocs.payload.JsonFieldType;

public class FieldCollection implements Fields {

    private final Field field;
    private final List<Field> fields = new ArrayList<>();

    public FieldCollection(Field field) {
        this.field = field;
    }

    @Override
    public Fields desc(String desc) {
        field.desc(desc);
        return this;
    }

    @Override
    public Fields optional() {
        field.optional();
        return this;
    }

    @Override
    public FieldGetter toGetFiled() {
        return field.toGetFiled();
    }

    @Override
    public Fields with(Field... fields) {
        final var list = Arrays
            .stream(fields)
            .map(field -> {
                final var rootField = this.field.toGetFiled();
                final var rootName = rootField.getFieldName();
                final var rootFieldType = rootField.getJsonFieldType();

                final var subField = field.toGetFiled();
                final var subFieldName = subField.getFieldName();

                if (JsonFieldType.OBJECT.equals(rootFieldType)) {
                    return new FieldDefault(String.format("%s.%s", rootName, subFieldName),
                                            subField.getJsonFieldType(),
                                            subField.getDesc(),
                                            subField.isOptional());
                }

                if (JsonFieldType.ARRAY.equals(rootFieldType)) {
                    return new FieldDefault(String.format("%s[].%s", rootName, subField),
                                            subField.getJsonFieldType(),
                                            subField.getDesc(),
                                            subField.isOptional());
                }

                throw new UnsupportedOperationException();
            })
            .toList();

        this.fields.addAll(list);
        return this;
    }

    @Override
    public List<Field> getFields() {
        return this.fields;
    }

    @Override
    public Field getRootField() {
        return this.field;
    }
}
