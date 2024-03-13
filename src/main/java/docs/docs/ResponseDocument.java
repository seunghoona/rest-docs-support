package docs.docs;

import docs.builder.Field;

public interface ResponseDocument {


    ResponseWithEndDocument response(Field... fields);

    ResponseWithEndDocument response();

}
