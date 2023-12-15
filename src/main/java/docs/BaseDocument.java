package docs;


import docs.BaseRestDocsAdapter.RequestDocument;

public class BaseDocument {

	public static RequestDocument document(String document) {
		return BaseRestDocsAdapter.document(document);
	}

}
