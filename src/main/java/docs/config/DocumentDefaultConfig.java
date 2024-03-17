package docs.config;

import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import java.util.List;
import java.util.Map;


public class DocumentDefaultConfig {

    public DefaultResponse getResponseConfig() {

        final var headers = object("headers").desc("응답 헤더")
            .with(string("code", "응답 코드"))
            .with(string("message", "메시지"));

        return DefaultResponse
            .builder()
            .defaultField(headers)
            .wrapData(object("data").desc("데이터"))
            .build();
    }
}
