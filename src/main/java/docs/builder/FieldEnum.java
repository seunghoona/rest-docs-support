package docs.builder;

import docs.model.JsonDocumentFieldType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private FieldDefault createField(String subField, String desc) {
        final var rootName = this.field
            .toGetFiled()
            .getFieldName();
        return new FieldDefault(String.format("%s.%s", rootName, subField),
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
