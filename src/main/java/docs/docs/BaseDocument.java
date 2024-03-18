package docs.docs;

import docs.builder.DocumentBuilder;

public interface BaseDocument {

    static RequestDocument document(String document) {
        return DocumentBuilder.of(document);
    }

}
