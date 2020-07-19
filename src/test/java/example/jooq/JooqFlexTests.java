package example.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static example.jooq.generated.tables.Author.AUTHOR;

@SpringBootTest
public class JooqFlexTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final Result<Record> authors = create.select().from(AUTHOR).fetch();
        for (Record author : authors) {
            final Integer id = author.getValue(AUTHOR.ID);
            final String firstName = author.getValue(AUTHOR.FIRST_NAME);
            final String lastName = author.getValue(author.field("LAST_NAME"), String.class);
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
