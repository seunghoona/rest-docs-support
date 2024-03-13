package docs.docs;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import java.util.Collection;
import java.util.Map;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public interface EndDocumentService {

    Snippets convertToSnippet(Map<FieldDocumentType, Collection<Field>> fields);

    RestDocumentationFilter convertToRestDocumentationFilter(String document,
        Map<FieldDocumentType, Collection<Field>> fields);
}
