package docs;


import docs.builder.DocumentBuilder;
import docs.docs.RequestDocument;

public class BaseDocument {

    public static RequestDocument document(String document) {
        return DocumentBuilder.of(document);
    }

}
