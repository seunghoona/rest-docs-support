package docs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.DocumentField.Field;
import docs.DocumentField.FieldList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;


class BaseRestDocsAdapter {

	public static RequestDocument document(String document) {
		return new CustomRestDocsBuilder(document);
	}

	public interface DefaultDocument {

		RequestDocument document(String document);

	}

	public interface RequestDocument {

		ResponseDocument request(Field... fields);

	}


	public interface ResponseDocument {

		DocumentEnd response(Field... fields);
	}


	public interface DocumentEnd {

		RestDocumentationFilter end();

		String toString();
	}

	static class CustomRestDocsBuilder implements DefaultDocument, RequestDocument, ResponseDocument, DocumentEnd {

		private String document;
		private final List<Field> requestFields = new ArrayList<>();
		private final List<Field> responsesFields = new ArrayList<>();

		public CustomRestDocsBuilder(String document) {
			this.document = document;
		}

		@Override
		public RequestDocument document(String document) {
			this.document = document;
			return this;
		}

		@Override
		public ResponseDocument request(Field... fields) {
			var fieldMap = Arrays.stream(fields)
				.flatMap(field -> field instanceof FieldList fieldList ? add(fieldList) : Arrays.stream(new Field[]{field}))
				.toList();

			this.requestFields.addAll(fieldMap);
			return this;
		}

		private Stream<Field> add(FieldList fieldList) {
			var fields = fieldList.getFields();
			fields.add(0,fieldList);
			return fields.stream();
		}

		@Override
		public DocumentEnd response(Field... fields) {
			var fieldMap = Arrays.stream(fields)
				.flatMap(field -> field instanceof FieldList fieldList ? add(fieldList) : Arrays.stream(new Field[]{field}))
				.toList();
			this.responsesFields.addAll(fieldMap);
			return this;
		}

		@Override
		public RestDocumentationFilter end() {
			var request = this.requestFields.stream()
				.map(getFieldFieldDescriptorFunction())
				.toList();

			var response = this.responsesFields.stream()
				.map(getFieldFieldDescriptorFunction())
				.toList();

			return RestAssuredRestDocumentationWrapper.document(
				this.document,
				PayloadDocumentation.requestFields(request),
				PayloadDocumentation.responseFields(response)
			);
		}

		private Function<Field, FieldDescriptor> getFieldFieldDescriptorFunction() {
			return field -> {
				var type = fieldWithPath(field.getFieldName()).type(field.getJsonFieldType());
				return field.isOptional() ? type.optional() : type;
			};
		}

		@Override
		public String toString() {
			return "CustomRestDocsBuilder{" + "document='" + document + '\'' + ", requestFields=" + requestFields + ", responsesFields=" + responsesFields + '}';
		}
	}

}
