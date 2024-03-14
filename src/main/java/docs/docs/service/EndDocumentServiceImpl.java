package docs.docs.service;

import static java.util.stream.Collectors.toMap;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import docs.builder.FieldDefault;
import docs.builder.Fields;
import docs.docs.Snippets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class EndDocumentServiceImpl implements EndDocumentService {

    @Override
    public Snippets convertToSnippet(Map<FieldDocumentType, List<Field>> fields) {

        final var collect = fields
            .entrySet()
            .stream()
            .collect(toMap(Entry::getKey,
                           entry -> entry
                               .getValue()
                               .stream()
                               .flatMap(subField -> {
                                   if (subField instanceof Fields castSubField) {
                                       return Stream.concat(castSubField
                                                                .getFields()
                                                                .stream(),
                                                            Stream.of(castSubField.getRootField()));
                                   } else {
                                       return Stream.of(subField);
                                   }
                               })
                               .collect(Collectors.toList())));

        final var snippets = collect
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
                    .map(responseField -> {

                        final var filed = responseField.toGetFiled();

                        if (filed
                                .getFieldName()
                                .contains("headers") || filed
                                .getFieldName()
                                .equals("data")) {
                            return responseField;
                        }

                        return new FieldDefault(String.format("data.%s", filed.getFieldName()),
                                                filed.getJsonFieldType(),
                                                filed.getDesc(),
                                                filed.isOptional());
                    })
                    .map(toFieldDescriptor())
                    .map(PayloadDocumentation::responseFields);

                default -> throw new UnsupportedOperationException();
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
