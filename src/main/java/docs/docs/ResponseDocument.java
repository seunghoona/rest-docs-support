package docs.docs;

import docs.builder.Field;

public interface ResponseDocument {


    Document response(Field... fields);

    Document response();

}
