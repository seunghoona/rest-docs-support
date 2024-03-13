package docs.docs;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

public interface Document extends RequestDocument, ResponseDocument {

    Snippets getSnippets();

    RestDocumentationFilter end();
}
