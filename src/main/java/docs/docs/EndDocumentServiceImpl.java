package docs.docs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class EndDocumentServiceImpl implements EndDocumentService {

    @Override
    public Snippets convertToSnippet(Map<FieldDocumentType, Collection<Field>> fields) {
        final var snippets = fields
            .entrySet()
            .stream()
            .filter(docFields -> !docFields
                .getValue()
                .isEmpty())
            .flatMap(docFields -> switch (docFields.getKey()) {
                case QUERY_PARAM -> docFields
                    .getValue()
                    .stream()
                    .map(toParameterDescriptor())
                    .map(RequestDocumentation::queryParameters);

                case PATH_PARAM -> docFields
                    .getValue()
                    .stream()
                    .map(toParameterDescriptor())
                    .map(RequestDocumentation::pathParameters);

                case REQUEST -> docFields
                    .getValue()
                    .stream()
                    .map(toFieldDescriptor())
                    .map(PayloadDocumentation::requestFields);

                case RESPONSE -> docFields
                    .getValue()
                    .stream()
                    .map(toFieldDescriptor())
                    .map(PayloadDocumentation::responseFields);

                default -> throw new UnsupportedOperationException();
            })
            .toList();





        snippets.toArray();

        return Snippets.of(snippets);
    }

    @Override
    public RestDocumentationFilter convertToRestDocumentationFilter(String document,
        Map<FieldDocumentType, Collection<Field>> fields) {

        return RestAssuredRestDocumentationWrapper.document(document,
                                                            convertToSnippet(fields).toRestDocumentationFilter());
    }

    private static Function<Field, ParameterDescriptor> toParameterDescriptor() {
        return field -> {
            final var getFiled = field.toGetFiled();
            ParameterDescriptor type = parameterWithName(getFiled.getFieldName()).description(
                getFiled.getDesc());
            return getFiled.isOptional()
                   ? type.optional()
                   : type;
        };
    }

    private static Function<Field, FieldDescriptor> toFieldDescriptor() {
        return field -> {
            final var getFiled = field.toGetFiled();
            FieldDescriptor type = fieldWithPath(getFiled.getFieldName()).description(getFiled.getDesc());
            return getFiled.isOptional()
                   ? type.optional()
                   : type;
        };
    }


}
