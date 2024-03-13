package docs.builder;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.docs.AbstractDocument;
import docs.docs.EndDocument;
import java.util.function.Function;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.AbstractDescriptor;
import org.springframework.restdocs.snippet.Snippet;


public class EndDocumentBuilder extends AbstractDocument implements EndDocument {

    @Override
    public RestDocumentationFilter end() {

        final var snipets = this.fields
            .entrySet()
            .stream()
            .filter(fields -> !fields
                .getValue()
                .isEmpty())
            .flatMap(fields -> switch (fields.getKey()) {
                case QUERY_PARAM, PATH_PARAM -> fields
                    .getValue()
                    .stream()
                    .map(toFieldDescriptor());

                case REQUEST, RESPONSE -> fields
                    .getValue()
                    .stream()
                    .map(toParameterDescriptor());

                default -> throw new UnsupportedOperationException();
            })
            .toList();

        return RestAssuredRestDocumentationWrapper.document(this.document,
                                                            snipets.toArray(new Snippet[0]));
    }

    private static Function<Field, AbstractDescriptor<ParameterDescriptor>> toParameterDescriptor() {
        return field -> {
            final var getFiled = field.toGetFiled();
            ParameterDescriptor type = parameterWithName(getFiled.getFieldName()).description(
                getFiled.getDesc());
            return getFiled.isOptional()
                   ? type.optional()
                   : type;
        };
    }

    private static Function<Field, AbstractDescriptor<FieldDescriptor>> toFieldDescriptor() {
        return field -> {
            final var getFiled = field.toGetFiled();
            FieldDescriptor type = fieldWithPath(getFiled.getFieldName()).description(getFiled.getDesc());
            return getFiled.isOptional()
                   ? type.optional()
                   : type;
        };
    }


}
