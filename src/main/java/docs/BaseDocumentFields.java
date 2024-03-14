package docs;


import docs.builder.Field;
import docs.builder.FieldCollection;
import docs.builder.FieldDefault;
import docs.builder.FieldEnum;
import docs.builder.FieldObject;
import docs.builder.Fields;
import docs.model.JsonDocumentFieldType;

public interface BaseDocumentFields {

	static Field string(String filedName) {
		return new FieldDefault(filedName, JsonDocumentFieldType.STRING);
	}

	static Field string(String filedName, String desc) {
		return new FieldDefault(filedName, JsonDocumentFieldType.STRING, desc);
	}

	static Field string(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonDocumentFieldType.STRING, desc, isOptional);
	}

	static Field number(String filedName) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NUMBER);
	}

	static Field number(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NUMBER, desc, isOptional);
	}

	static Field number(String filedName, String desc) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NUMBER, desc);
	}

	static Fields object(String filedName) {
		return new FieldObject(new FieldDefault(filedName, JsonDocumentFieldType.OBJECT));
	}

	static Fields object(String filedName, String desc, boolean isOptional) {
		return new FieldObject(new FieldDefault(filedName, JsonDocumentFieldType.OBJECT, desc, isOptional));
	}

	static Fields object(String filedName, String desc) {
		return new FieldObject(new FieldDefault(filedName, JsonDocumentFieldType.OBJECT, desc));
	}

	static Fields list(String filedName) {
		return new FieldCollection(new FieldDefault(filedName, JsonDocumentFieldType.ARRAY));
	}

	static Fields list(String filedName, String desc) {
		return new FieldCollection(new FieldDefault(filedName, JsonDocumentFieldType.ARRAY, desc));
	}

	static Fields list(String filedName, String desc, boolean isOptional) {
		return new FieldCollection(new FieldDefault(filedName, JsonDocumentFieldType.ARRAY, desc, isOptional));
	}

	static Field bool(String filedName) {
		return new FieldDefault(filedName, JsonDocumentFieldType.BOOLEAN);
	}

	static Field bool(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonDocumentFieldType.BOOLEAN, desc, isOptional);
	}

	static Field bool(String filedName, String desc) {
		return new FieldDefault(filedName, JsonDocumentFieldType.BOOLEAN, desc);
	}

	static Field empty(String filedName) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NULL);
	}

	static Field empty(String filedName, String desc) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NULL, desc);
	}

	static Field empty(String filedName, String desc, boolean isOptional) {
		return new FieldDefault(filedName, JsonDocumentFieldType.NULL, desc, isOptional);
	}

	static Fields type(String filedName) {
		return new FieldEnum(new FieldDefault(filedName, JsonDocumentFieldType.ENUM));
	}

	static Fields type(String filedName, String desc) {
		return new FieldEnum(new FieldDefault(filedName, JsonDocumentFieldType.ENUM, desc));
	}

	static Fields type(String filedName, String desc, boolean isOptional) {
		return new FieldEnum(new FieldDefault(filedName, JsonDocumentFieldType.ENUM, desc, isOptional));
	}


}
