package docs;

import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import docs.DocumentField.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseDocumentConfig {

	public static final List<Field> RESPONSE_DEFAULT_HEADER = defaultHeader();


	private static List<Field> defaultHeader() {

		List<Field> list = new ArrayList<>();
		list.add(object("headers").desc("헤더"));
		list.add(string("headers.code").desc("코드"));
		list.add(string("headers.message").desc("메시지"));

		return list;
	}

	public static Field defaultEnum(String type, String desc) {

		assert Objects.equals(type, "") : "type값은 필수입니다.";

		return object(type)
			.desc(desc)
			.with(BaseDocumentFields
					  .string("key")
					  .desc("key"))
			.with(BaseDocumentFields
					  .string("value")
					  .desc("value"));
	}
}
