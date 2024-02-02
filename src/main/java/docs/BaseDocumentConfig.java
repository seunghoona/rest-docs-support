package docs;

import static docs.BaseDocumentFields.object;
import static docs.BaseDocumentFields.string;

import docs.DocumentField.Field;
import java.util.ArrayList;
import java.util.List;

public class BaseDocumentConfig {

	public static final List<Field> RESPONSE_DEFAULT_HEADER = getDefault();

	private static List<Field> getDefault() {

		List<Field> list = new ArrayList<>();
		list.add(object("headers").desc("헤더"));
		list.add(string("headers.code").desc("코드"));
		list.add(string("headers.message").desc("메시지"));

		return list;
	}
}
