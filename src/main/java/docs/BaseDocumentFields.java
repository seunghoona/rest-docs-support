package docs;


import docs.builder.Field;
import docs.builder.FieldCollection;
import docs.builder.FieldDefault;
import docs.builder.Fields;
import org.springframework.restdocs.payload.JsonFieldType;

public interface BaseDocumentFields {

	static Field string(String filedName) {
		return new FieldDefault(filedName, JsonFieldType.STRING);
	}

	static Field string(String filedName, String desc) {
		return new FieldDefault(filedName, JsonFieldType.STRING, desc);
	}

	static Field string(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonFieldType.STRING, desc, isOptional);
	}

	static Field number(String filedName) {
		return new FieldDefault(filedName, JsonFieldType.NUMBER);
	}

	static Field number(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonFieldType.NUMBER, desc, isOptional);
	}

	static Field number(String filedName, String desc) {
		return new FieldDefault(filedName, JsonFieldType.NUMBER, desc);
	}

	static Fields object(String filedName) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT));
	}

	static Fields object(String filedName, String desc, boolean isOptional) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT, desc, isOptional));
	}

	static Fields object(String filedName, String desc) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT, desc));
	}

	static Fields list(String filedName) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT));
	}

	static Fields list(String filedName, String desc) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT, desc));
	}

	static Fields list(String filedName, String desc, boolean isOptional) {
		return new FieldCollection(new FieldDefault(filedName, JsonFieldType.OBJECT, desc, isOptional));
	}

	static Field bool(String filedName) {
		return new FieldDefault(filedName, JsonFieldType.BOOLEAN);
	}

	static Field bool(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonFieldType.BOOLEAN, desc, isOptional);
	}

	static Field bool(String filedName, String desc) {
		return new FieldDefault(filedName, JsonFieldType.BOOLEAN, desc);
	}

	static Field empty(String filedName) {
		return new FieldDefault(filedName, JsonFieldType.NULL);
	}

	static Field empty(String filedName, String desc) {
		return new FieldDefault(filedName, JsonFieldType.NULL, desc);
	}

	static Field empty(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonFieldType.NULL, desc, isOptional);
	}

/*	static Field enumType(String filedName, String desc) {
		return defaultEnum(filedName, desc);
	}*/

}
