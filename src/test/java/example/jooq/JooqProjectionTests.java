package example.jooq;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;
import static java.lang.String.format;
import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.val;

@SpringBootTest
public class JooqProjectionTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final Field<String> field = concat(
                val(format("Class: %s, Id: ", getClass().getSimpleName())),
                AUTHOR.ID,
                val(", author: "),
                AUTHOR.FIRST_NAME,
                val(" "),
                AUTHOR.LAST_NAME,
                val("\n"));
        final List<String> messages = create.select(field).from(AUTHOR).fetch(field);
        messages.forEach(System.out::print);
    }
}
