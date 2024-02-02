package docs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import docs.DocumentField.Field;
import docs.DocumentField.FieldCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.restdocs.snippet.Snippet;


class BaseRestDocs {

	public static RequestResponseDocument document(String document) {
		return new BaseRestDocsBuilder(document);
	}

	public interface RequestDocument {

		@Deprecated
		ResponseDocument request(Field... fields);
		RequestResponseDocument queryParam(Field... desc);
		RequestResponseDocument pathParam(Field... desc);

		ResponseDocument requestBody(Field... fields);

		RequestResponseDocument multipart(Field... desc);
	}


	public interface ResponseDocument {

		DocumentEnd response(Field... fields);
	}

	public interface RequestResponseDocument extends RequestDocument, ResponseDocument {}


	public interface DocumentEnd {
		RestDocumentationFilter end();
	}

	static class BaseRestDocsBuilder implements RequestResponseDocument, DocumentEnd {

		private String document;
		private final List<Field> requestFields = new ArrayList<>();
		private final List<Field> responsesHeader = BaseDocumentConfig.RESPONSE_DEFAULT_HEADER;
		private final List<Field> responsesFields = new ArrayList<>();
		private final List<Field> queryParam = new ArrayList<>();
		private final List<Field> pathParam = new ArrayList<>();

		public BaseRestDocsBuilder(String document) {
			this.document = document;
		}

		@Override
		public ResponseDocument request(Field... fields) {
			List<Field> fieldMap = Arrays.stream(fields)
				.flatMap(getFieldStreamFunction())
				.collect(Collectors.toList());

			this.requestFields.addAll(fieldMap);
			return this;
		}

		@Override
		public RequestResponseDocument pathParam(Field... fields) {
			List<Field> fieldMap = Arrays.stream(fields)
				.flatMap(getFieldStreamFunction())
				.collect(Collectors.toList());
			this.pathParam.addAll(fieldMap);
			return this;
		}

		@Override
		public ResponseDocument requestBody(Field... fields) {
			return request(fields);
		}

		@Override
		public RequestResponseDocument queryParam(Field... fields) {
			List<Field> fieldMap = Arrays.stream(fields)
				.flatMap(getFieldStreamFunction())
				.collect(Collectors.toList());
			this.queryParam.addAll(fieldMap);
			return this;
		}

		@Override
		public DocumentEnd response(Field... fields) {
			List<Field> fieldMap = Arrays.stream(fields)
				.flatMap(getFieldStreamFunction())
				.collect(Collectors.toList());

			this.responsesFields.addAll(this.responsesHeader);
			this.responsesFields.addAll(fieldMap);
			return this;
		}

		private Function<Field, Stream<? extends Field>> getFieldStreamFunction() {
			return field -> {
				if (field instanceof FieldCollection) {
					return add((FieldCollection) field);
				}
				return Arrays.stream(new Field[]{field});
			};
		}

		private Stream<Field> add(FieldCollection fieldList) {
			List<Field> fields = fieldList.getFields();
			fields.add(0,fieldList);
			return fields.stream();
		}

		@Override
		public RequestResponseDocument multipart(Field... desc) {
			throw new UnsupportedOperationException();
		}


		@Override
		public RestDocumentationFilter end() {

			List<FieldDescriptor> request = this.requestFields.stream()
				.map(getFieldWithPathFunc())
				.collect(Collectors.toList());

			List<FieldDescriptor> response = this.responsesFields.stream()
				.map(getFieldWithPathResponseFunc())
				.collect(Collectors.toList());

			List<ParameterDescriptor> pathParam = this.pathParam.stream()
				.map(getPathQueryFunc())
				.collect(Collectors.toList());

			List<ParameterDescriptor> queryParam = this.queryParam.stream()
				.map(getPathQueryFunc())
				.collect(Collectors.toList());

			Map<Integer, Snippet> snippetMap = new HashMap<>();

			snippetMap.put(request.size(), PayloadDocumentation.requestFields(request));
			snippetMap.put(response.size(), PayloadDocumentation.responseFields(response));
			snippetMap.put(pathParam.size(), RequestDocumentation.pathParameters(pathParam));
			snippetMap.put(queryParam.size(), RequestDocumentation.queryParameters(queryParam));

			List<Snippet> snippets = snippetMap
				.entrySet()
				.stream()
				.filter(s -> s.getKey() != 0)
				.map(Entry::getValue)
				.collect(Collectors.toList());


			this.requestFields.clear();
			this.responsesFields.clear();
			this.queryParam.clear();
			this.pathParam.clear();

			return RestAssuredRestDocumentationWrapper.document(
				this.document,
				snippets.toArray(new Snippet[0])
			);
		}

		private Function<Field, ParameterDescriptor> getPathQueryFunc() {
			return field -> {
				ParameterDescriptor type = parameterWithName(field.getFieldName()).description(field.getDesc());
				return field.isOptional() ? type.optional() : type;
			};
		}

		private Function<Field, FieldDescriptor> getFieldWithPathFunc() {
			return field -> {
				FieldDescriptor type = fieldWithPath(field.getFieldName())
					.type(field.getJsonFieldType())
					.description(field.getDesc());
				return field.isOptional() ? type.optional() : type;
			};
		}

		private Function<Field, FieldDescriptor> getFieldWithPathResponseFunc() {
			return field -> {
				// TODO : 반드시 분리 생각 하기

				if (field.getFieldName().startsWith("headers")) {
					FieldDescriptor type = fieldWithPath(field.getFieldName())
					.type(field.getJsonFieldType())
					.description(field.getDesc());
					return field.isOptional() ? type.optional() : type;
				}

				FieldDescriptor type = fieldWithPath("data." + field.getFieldName())
					.type(field.getJsonFieldType())
					.description(field.getDesc());
				return field.isOptional() ? type.optional() : type;
			};
		}

		@Override
		public String toString() {
			return "BaseRestDocsBuilder{" + "document='" + document + '\'' + ", requestFields=" + requestFields + ", responsesFields=" + responsesFields + '}';
		}
	}

}
