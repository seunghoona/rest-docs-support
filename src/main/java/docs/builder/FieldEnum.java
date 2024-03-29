package docs.builder;

import docs.model.JsonDocumentFieldType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FieldEnum implements Fields {

    private final Field field;
    private final Set<Field> fields = new HashSet<>();

    public FieldEnum(Field field) {
        this.field = field;
        this.fields.add(createField("key", "key"));
        this.fields.add(createField("comment", "comment"));
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
    public void changeName(String fieldName) {
        this.fields
            .forEach(field -> {
                final var code = field
                    .toGetFiled()
                    .getFieldName()
                    .split(this.field.toGetFiled().getFieldName());

                if(code.length == 2) {
                    field.changeName(String.format("%s%s", fieldName, code[1]));
                }
            });
        this.field.changeName(fieldName);
    }

    @Override
    public Fields with(Field... fields) {
        final var list = Arrays
            .stream(fields)
            .map(this::createField)
            .toList();

        this.fields.addAll(list);
        return this;
    }

    private FieldDefault createField(Field field) {
        final var rootField = this.field.toGetFiled();
        final var rootName = rootField.getFieldName();

        final var subField = field.toGetFiled();
        final var subFieldName = subField.getFieldName();

        return new FieldDefault(String.format("%s.%s", rootName, subFieldName),
                                subField.getJsonDocumentFieldType(),
                                subField.getDesc(),
                                subField.isOptional());
    }

    private FieldDefault createField(String subFieldName, String desc) {
        final var rootField = this.field.toGetFiled();
        final var rootName = rootField.getFieldName();

        return new FieldDefault(String.format("%s.%s", rootName, subFieldName),
                                JsonDocumentFieldType.STRING,
                                desc, false);
    }

    @Override
    public List<Field> getFields() {
        return this.fields.stream().toList();
    }

    @Override
    public Field getRootField() {
        return this.field;
    }
}
