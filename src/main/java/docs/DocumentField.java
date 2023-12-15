package docs;

import java.util.ArrayList;
import java.util.List;
import org.springframework.restdocs.payload.JsonFieldType;

public class DocumentField {

	public interface Field {
		Field desc(String desc);

		Field optional();

		String getFieldName();

		JsonFieldType getJsonFieldType();

		String getDesc();

		boolean isOptional();

		FieldCollection with(Field field);

	}

	public interface FieldCollection extends Field {

		FieldCollection with(Field field);

		List<Field> getFields();

	}

	static class FieldBuilder implements Field {

		private final String fieldName;
		private final JsonFieldType jsonFieldType;
		private String desc;
		private boolean optional = false;

		public FieldBuilder(String fieldName, JsonFieldType jsonFieldType) {
			this.fieldName = fieldName;
			this.jsonFieldType = jsonFieldType;
		}

		public FieldBuilder(String filedName, JsonFieldType jsonFieldType, String desc, boolean optional) {
			this.fieldName = filedName;
			this.jsonFieldType = jsonFieldType;
			this.desc = desc;
			this.optional = optional;
		}

		public FieldBuilder(String filedName, JsonFieldType jsonFieldType, String desc) {
			this(
				filedName,
				jsonFieldType,
				desc,
				false
			);
		}

		@Override
		public Field desc(String desc) {
			this.desc = desc;
			return this;
		}
		@Override
		public Field optional() {
			this.optional = true;
			return this;
		}

		@Override
		public String getFieldName() {
			return fieldName;
		}

		@Override
		public JsonFieldType getJsonFieldType() {
			return jsonFieldType;
		}

		@Override
		public String getDesc() {
			return desc;
		}

		@Override
		public boolean isOptional() {
			return optional;
		}

		@Override
		public FieldCollection with(Field field) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return "FieldBuilder{" + "fieldName='" + fieldName + '\'' + ", jsonFieldType=" + jsonFieldType + ", desc='" + desc + '\'' + ", optional=" + optional + '}';
		}
	}

	static class FieldListBuilder implements FieldCollection {

		private static final String OBJECT_FORMAT = "%s.%s";
		private static final String ARRAY_FORMAT = "%s[].%s";
		private final String fieldName;
		private final JsonFieldType jsonFieldType;
		private String desc;
		private boolean optional = false;
		private final List<Field> fields = new ArrayList<>();


		public FieldListBuilder(String fieldName, JsonFieldType jsonFieldType) {
			this.fieldName = fieldName;
			this.jsonFieldType = jsonFieldType;
		}

		public FieldListBuilder(String filedName, JsonFieldType jsonFieldType, String desc, boolean optional) {
			this.fieldName = filedName;
			this.jsonFieldType = jsonFieldType;
			this.desc = desc;
			this.optional = optional;
		}

		public FieldListBuilder(String filedName, JsonFieldType jsonFieldType, String desc) {
			this(
				filedName,
				jsonFieldType,
				desc,
				false
			);
		}

		@Override
		public Field desc(String desc) {
			this.desc = desc;
			return this;
		}
		@Override
		public Field optional() {
			this.optional = true;
			return this;
		}

		@Override
		public FieldCollection with(Field field) {

			if (JsonFieldType.OBJECT.equals(this.jsonFieldType)) {
				fields.add(new FieldBuilder(getString(field,
					OBJECT_FORMAT
				), field.getJsonFieldType(), field.getDesc(), field.isOptional()));
				return this;
			}

			if (JsonFieldType.ARRAY.equals(this.jsonFieldType)) {
				fields.add(new FieldBuilder(getString(field,
					ARRAY_FORMAT
				), field.getJsonFieldType(), field.getDesc(), field.isOptional()));
				return this;
			}

			throw new UnsupportedOperationException();
		}

		private String getString(Field field, String format) {
			return String.format(
				format,
				this.fieldName,
				field.getFieldName()
			);
		}

		@Override
		public List<Field> getFields() {
			return this.fields;
		}

		@Override
		public String getFieldName() {
			return fieldName;
		}

		@Override
		public JsonFieldType getJsonFieldType() {
			return jsonFieldType;
		}

		@Override
		public String getDesc() {
			return desc;
		}

		@Override
		public boolean isOptional() {
			return optional;
		}

		@Override
		public String toString() {
			return "FieldBuilder{" + "fieldName='" + fieldName + '\'' + ", jsonFieldType=" + jsonFieldType + ", desc='" + desc + '\'' + ", optional=" + optional + '}';
		}


	}

}
