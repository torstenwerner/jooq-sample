package example.jooq;

import example.jooq.generated.tables.records.AuthorRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static example.jooq.generated.tables.Author.AUTHOR;

@SpringBootTest
public class JooqMapTests {
    @Test
    void shouldFindAllAuthors(@Autowired DSLContext create) {
        final Result<AuthorRecord> authors = create.selectFrom(AUTHOR).fetch();
        for (AuthorRecord author : authors) {
            final Integer id = author.getId();
            final String firstName = author.getFirstName();
            final String lastName = author.getLastName();
            System.out.printf("Class: %s, Id: %d, author: %s %s\n", getClass().getSimpleName(), id, firstName, lastName);
        }
    }
}
