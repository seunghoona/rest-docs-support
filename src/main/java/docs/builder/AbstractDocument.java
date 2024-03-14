package docs.builder;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.config.DocumentDefaultConfig;
import docs.docs.service.EndDocumentService;
import docs.docs.service.EndDocumentServiceImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDocument {

    protected String document;
    protected EndDocumentService endDocumentService = new EndDocumentServiceImpl();
    protected DocumentDefaultConfig config = new DocumentDefaultConfig();
    protected Map<FieldDocumentType, List<Field>> fields;

    public AbstractDocument(String document) {
        this.document = document;
        this.fields = new HashMap<>();

        this.fields.putAll(config.get());
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
    protected void createNewFieldDocument(FieldDocumentType type, List<Field> collection) {
        this.fields.put(type, collection);
    }

    // 이미 등록된 경우 FieldDocumentType 추가
    protected void addFieldDocument(FieldDocumentType type, List<Field> collection) {
        final var filed = this.fields.get(type);
        filed.addAll(collection);
    }

    protected List<Field> toCollection(Field[] fields) {
        return Arrays
            .stream(fields)
            .collect(Collectors.toList());
    }

}
