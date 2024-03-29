package docs.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FieldObject implements Fields {

    private final Field field;
    private final Set<Field> fields = new HashSet<>();

    @Override
    public Fields desc(String desc) {
        this.field.desc(desc);
        return this;
    }

    @Override
    public Fields optional() {
        this.field.optional();
        return this;
    }

    @Override
    public FieldGetter toGetFiled() {
        return this.field.toGetFiled();
    }

    @Override
    public Fields with(Field... fields) {
        final var list = Arrays
            .stream(fields)
            .peek(field -> {
                final var rootField = this.field.toGetFiled();
                final var rootName = rootField.getFieldName();

                final var subField = field.toGetFiled();
                final var subFieldName = subField.getFieldName();

                changeName(field, String.format("%s.%s", rootName, subFieldName));
            })
            .toList();

        this.fields.addAll(list);
        return this;
    }

    @Override
    public List<Field> getFields() {
        return this.fields
            .stream()
            .toList();
    }

    @Override
    public Field getRootField() {
        return this.field;
    }
}
