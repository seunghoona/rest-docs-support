package docs.docs;

import docs.builder.Field;
import docs.builder.RequestDocumentBuilder.FieldDocumentType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDocument {

    protected String document;
    protected final Map<FieldDocumentType, Collection<Field>> fields = new HashMap<>();

    public AbstractDocument(String document) {
        this.document = document;
    }

    public AbstractDocument() {
    }

    protected void processes(Field[] enterFields, FieldDocumentType type) {
        final var collection = toCollection(enterFields);

        if (isPutType(type)) {
            addFieldDocument(type, collection);
            return;
        }
        createNewFieldDocument(type, collection);
    }

    // 이미 등록된 FieldDocumentType 확인
    protected boolean isPutType(FieldDocumentType type) {
        return this.fields.containsKey(type);
    }

    // 새로운 FieldDocumentType 등록
    protected void createNewFieldDocument(FieldDocumentType type, Collection<Field> collection) {
        this.fields.put(type, collection);
    }

    // 이미 등록된 경우 FieldDocumentType 추가
    protected void addFieldDocument(FieldDocumentType type, Collection<Field> collection) {
        final var filed = this.fields.get(type);
        filed.addAll(collection);
    }


    protected Collection<Field> toCollection(Field[] fields) {
        return Arrays
            .stream(fields)
            .collect(Collectors.toList());
    }

}
