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

	}

	public interface FieldList extends Field {
		FieldList with(Field string);

		List<Field> getFields();

	}

	static class FieldBuilder implements Field {

		private String fieldName;
		private JsonFieldType jsonFieldType;
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
		public String toString() {
			return "FieldBuilder{" + "fieldName='" + fieldName + '\'' + ", jsonFieldType=" + jsonFieldType + ", desc='" + desc + '\'' + ", optional=" + optional + '}';
		}
	}

	static class FieldListBuilder implements FieldList {

		private String fieldName;
		private JsonFieldType jsonFieldType;
		private String desc;
		private boolean optional = false;
		private List<Field> fields = new ArrayList<>();


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
		public FieldList with(Field field) {
			var prefixList = String.format("%s[].%s", this.fieldName, field.getFieldName());
			fields.add(new FieldBuilder(prefixList, field.getJsonFieldType(), field.getDesc(), field.isOptional()));
			return this;
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
