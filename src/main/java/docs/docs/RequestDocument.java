package docs.docs;

import docs.builder.Field;

public interface RequestDocument {

    Document request(Field... fields);

    Document queryParam(Field... desc);

    Document pathParam(Field... desc);

    Document requestBody(Field... fields);

    Document multipart(Field... desc);

    ResponseDocument response(Field... desc);

    ResponseDocument response();

    int getSize();

}
