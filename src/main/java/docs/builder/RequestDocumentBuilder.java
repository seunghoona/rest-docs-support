package docs.builder;

import docs.docs.AbstractDocument;
import docs.docs.Document;
import docs.docs.RequestDocument;
import docs.docs.ResponseDocument;
import docs.docs.ResponseWithEndDocument;

public class RequestDocumentBuilder extends AbstractDocument implements Document, ResponseDocument {

    public static RequestDocument of(String document) {
        return new RequestDocumentBuilder(document);
    }

    protected RequestDocumentBuilder(String document) {
        super(document);
    }

    public RequestDocumentBuilder() {
        super("");
    }

    public enum FieldDocumentType {
        REQUEST, QUERY_PARAM, REQUEST_BODY, PATH_PARAM, MULTIPART, RESPONSE
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
    public ResponseWithEndDocument response(Field... fields) {
        processes(fields, FieldDocumentType.RESPONSE);
        return new ResponseDocumentBuilder();
    }

    @Override
    public ResponseWithEndDocument response() {
        return new ResponseDocumentBuilder();
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
