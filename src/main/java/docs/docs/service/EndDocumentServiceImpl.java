package docs.docs.service;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import docs.builder.FieldDefault;
import docs.builder.Fields;
import docs.config.DefaultResponse;
import docs.config.DocumentConfig;
import docs.docs.Snippets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;


public class EndDocumentServiceImpl implements EndDocumentService {

    private List<Field> ignoreFields;
    private Field warpField;
    
    @Override
    public void setUp(DocumentConfig defaultConfig) {
        final var config = defaultConfig.getResponseConfig();
        this.ignoreFields = config.getIgnoredFields();
        this.warpField = config.getWrapDataField();
    }

    @Override
    public Snippets convertToSnippet(Map<FieldDocumentType, List<Field>> fields) {

        // FieldCollection -> flatMap
        final var collect = fields
            .entrySet()
            .stream()
            .collect(() -> new HashMap<FieldDocumentType, List<Field>>(), (e, entry) -> {
                List<Field> list = new ArrayList<>();
                entry
                    .getValue()
                    .forEach(subField -> {
                        if (subField instanceof Fields castSubField) {
                            list.addAll(castSubField.getFields());
                            list.add(castSubField.getRootField());
                        } else {
                            list.add(subField);
                        }
                    });
                e.put(entry.getKey(), list);
            }, HashMap::putAll);

        // to Snippet
        final var snippets = collect
            .entrySet()
            .stream()
            .filter(docFields -> !docFields
                .getValue()
                .isEmpty())

            .flatMap(docFields -> switch (docFields.getKey()) {

                case QUERY_PARAM -> {
                    final var queryParams = docFields
                        .getValue()
                        .stream()
                        .map(toParameterDescriptor())
                        .toList();
                    yield Stream.of(RequestDocumentation.queryParameters(queryParams));
                }

                case PATH_PARAM -> {
                    final var pathParams = docFields
                        .getValue()
                        .stream()
                        .map(toParameterDescriptor())
                        .toList();
                    yield Stream.of(RequestDocumentation.pathParameters(pathParams));
                }

                case REQUEST, REQUEST_BODY -> {
                    final var requestFields = docFields
                        .getValue()
                        .stream()
                        .map(toFieldDescriptor())
                        .toList();
                    yield Stream.of(PayloadDocumentation.requestFields(requestFields));
                }

                case RESPONSE -> {
                    final var responseFields = docFields
                        .getValue()
                        .stream()
                        .map(responseField -> {

                            final var getField = responseField.toGetFiled();

                            if (ignoreFields
                                .stream()
                                .anyMatch(field -> field.equals(responseField) || getField
                                    .getFieldName()
                                    .startsWith(field
                                        .toGetFiled()
                                        .getFieldName()))) {

                                return responseField;
                            }

                            final var wrapField = this.warpField.toGetFiled();

                            return new FieldDefault(String.format("%s.%s",
                                wrapField.getFieldName(),
                                getField.getFieldName()),
                                getField.getJsonDocumentFieldType(),
                                getField.getDesc(),
                                getField.isOptional());
                        })
                        .map(toFieldDescriptor())
                        .toList();

                    yield Stream.of(PayloadDocumentation.responseFields(responseFields));
                }

                default -> throw new UnsupportedOperationException("Undefined document type " + docFields.getKey());
            })
            .toList();

        return Snippets.of(snippets);
    }


    @Override
    public RestDocumentationFilter convertToRestDocumentationFilter(String document,
        Map<FieldDocumentType, List<Field>> fields) {
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
