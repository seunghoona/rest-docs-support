package docs;


import docs.builder.RequestDocumentBuilder;
import docs.docs.RequestDocument;

public class BaseDocument {

    public static RequestDocument document(String document) {
        return RequestDocumentBuilder.of(document);
    }

}
