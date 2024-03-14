package docs.config;

import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DocumentDefaultConfig {

    public Map<FieldDocumentType, List<Field>> get() {

        final var headers = object("headers")
            .desc("성장")
            .with(string("code", "응답 코드"))
            .with(string("message", "메시지"));

        final var list = new ArrayList();

        list.add(headers);
        list.add(object("data").desc("데이터"));

        return Map.of(FieldDocumentType.RESPONSE, list);

    }
}
