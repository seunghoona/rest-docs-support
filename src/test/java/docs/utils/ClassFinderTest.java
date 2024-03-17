package docs.utils;

import static org.junit.jupiter.api.Assertions.*;

import docs.config.DocumentConfig;
import java.util.List;
import org.junit.jupiter.api.Test;

class ClassFinderTest {

    @Test
    void findClass() {

        final var docs = ClassFinder.findClassesImplementingInterface("docs", DocumentConfig.class);

        System.out.println(docs);
    }

}