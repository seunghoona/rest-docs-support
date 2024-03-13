package docs.docs;

import docs.builder.DocumentBuilder;

public class BaseDocument {

    public static RequestDocument document(String document) {
        return DocumentBuilder.of(document);
    }

}
