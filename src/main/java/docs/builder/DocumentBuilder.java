package docs.builder;

import docs.docs.Document;
import docs.docs.RequestDocument;
import docs.docs.Snippets;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class DocumentBuilder extends AbstractDocument implements Document {

    private final String document;

    public enum FieldDocumentType {
        REQUEST, QUERY_PARAM, REQUEST_BODY, PATH_PARAM, MULTIPART, RESPONSE
    }

    public static RequestDocument of(String document) {
        return new DocumentBuilder(document);
    }

    private DocumentBuilder(String document) {
        this.document = document;
    }

    @Override
    public Document request(Field... fields) {
        processes(fields, FieldDocumentType.REQUEST);
        return this;
    }

    @Override
    public Document queryParam(Field... fields) {
        processes(fields, FieldDocumentType.QUERY_PARAM);
        return this;
    }

    @Override
    public Document pathParam(Field... fields) {
        processes(fields, FieldDocumentType.PATH_PARAM);
        return this;
    }

    @Override
    public Document requestBody(Field... fields) {
        processes(fields, FieldDocumentType.REQUEST_BODY);
        return this;
    }

    @Override
    public Document multipart(Field... fields) {
        processes(fields, FieldDocumentType.MULTIPART);
        return this;
    }

    @Override
    public Document response(Field... fields) {
        processes(fields, FieldDocumentType.RESPONSE);
        return this;
    }

    @Override
    public Document response() {
        processes(new Field[]{}, FieldDocumentType.RESPONSE);
        return this;
    }

    @Override
    public Snippets getSnippets() {
        return endDocumentService.convertToSnippet(super.fields);
    }

    @Override
    public RestDocumentationFilter end() {
        return endDocumentService.convertToRestDocumentationFilter(this.document, super.fields);
    }


    /**
     * 필드 문서 타입별 필드를 저장한다.
     * <p>
     * 이미 생성한 필드 타입인 경우 Append 처리, 그 외는 새로 신규로 등록
     */

    @Override
    public int getSize() {
        return this.fields.size();
    }

}
