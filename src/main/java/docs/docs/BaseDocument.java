package docs.docs;

import docs.builder.RequestDocumentBuilder;

public class BaseDocument {

    public static RequestDocument document(String document) {
        return RequestDocumentBuilder.of(document);
    }

}
