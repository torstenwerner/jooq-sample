package example.jooq;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static example.jooq.generated.tables.Author.AUTHOR;
import static java.lang.String.format;

@SpringBootTest
public class JooqRecordTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final List<String> messages = create.selectFrom(AUTHOR).fetch(author -> {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            return format("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        });
        messages.forEach(System.out::print);
    }
}
