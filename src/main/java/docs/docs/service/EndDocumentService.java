package docs.docs.service;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import docs.docs.Snippets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public interface EndDocumentService {

    Snippets convertToSnippet(Map<FieldDocumentType, List<Field>> fields);

    RestDocumentationFilter convertToRestDocumentationFilter(String document,
        Map<FieldDocumentType, List<Field>> fields);
}
