package docs.builder;

import docs.builder.RequestDocumentBuilder.FieldDocumentType;
import docs.docs.AbstractDocument;
import docs.docs.ResponseWithEndDocument;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class ResponseDocumentBuilder extends AbstractDocument implements ResponseWithEndDocument {

    @Override
    public ResponseWithEndDocument response(Field... fields) {
        processes(fields, FieldDocumentType.RESPONSE);
        return this;
    }

    @Override
    public ResponseWithEndDocument response() {
        return this;
    }

    @Override
    public RestDocumentationFilter end() {
        final var endBuilder = new EndDocumentBuilder();
        return endBuilder.end();
    }

}
