package docs.docs;

import java.util.List;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.snippet.TemplatedSnippet;


public class Snippets {

    private List<TemplatedSnippet> snippets;

    public static Snippets of(List<TemplatedSnippet> snippets) {
        return new Snippets(snippets);
    }

    public Snippets(List<TemplatedSnippet> snippets) {
        this.snippets = snippets;
    }

    public Snippet[] toRestDocumentationFilter() {
        return snippets.toArray(new Snippet[0]);
    }

    public int size() {
        return snippets.size();
    }

}
