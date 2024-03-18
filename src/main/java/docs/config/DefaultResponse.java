package docs.config;

import docs.builder.DocumentBuilder.FieldDocumentType;
import docs.builder.Field;
import docs.builder.Fields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class DefaultResponse {

    private final Field wrapDataField;
    private final Field defaultField;
    private List<Field> fields;

    public DefaultResponse(Field wrapDataField, Field defaultField, List<Field> fields) {
        this.wrapDataField = wrapDataField;
        this.defaultField = defaultField;
        this.fields = fields;
    }

    public static DefaultResponseBuilder builder() {
        return new DefaultResponseBuilder();
    }

    static class DefaultResponseBuilder {

        private Field wrapDataField;
        private Field defaultField;
        private List<Field> fields = new ArrayList<>();

        public DefaultResponseBuilder wrapData(Field wrapDataField) {
            this.wrapDataField = wrapDataField;
            return this;
        }

        public DefaultResponseBuilder withField(Field defaultField) {
            this.defaultField = defaultField;
            return this;
        }

        public DefaultResponse build() {
            if (!(wrapDataField == null)) {
                this.fields.add(wrapDataField);
            }

            if (!(defaultField == null)) {
                this.fields.add(defaultField);
            }

            return new DefaultResponse(wrapDataField, defaultField, this.fields);
        }
    }

    public Map<FieldDocumentType, List<Field>> toMap() {
        Map<FieldDocumentType, List<Field>> map = new HashMap<>();
        map.put(FieldDocumentType.RESPONSE, fields);
        return map;
    }

    public List<Field> getIgnoredFields() {
        if (fields.isEmpty()) {
            return Collections.emptyList();
        }
        return fields.stream()
            .map(field -> ((Fields) field).getRootField())
            .toList();
    }


}
